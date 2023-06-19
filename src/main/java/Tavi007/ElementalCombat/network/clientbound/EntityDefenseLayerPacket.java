package Tavi007.ElementalCombat.network.clientbound;

import java.util.Optional;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.network.Packet;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import Tavi007.ElementalCombat.util.PacketBufferHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

public class EntityDefenseLayerPacket extends Packet {

    private Integer id;
    private String location;
    private DefenseLayer defenseLayer;

    public EntityDefenseLayerPacket(DefenseLayer defenseLayer, ResourceLocation location, Integer id) {
        this.defenseLayer = defenseLayer;
        this.location = location.toString();
        this.id = id;
    }

    public EntityDefenseLayerPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.location = buf.readUtf();
        this.defenseLayer = new DefenseLayer(PacketBufferHelper.readHashMap(buf), PacketBufferHelper.readHashMap(buf));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeUtf(location);
        PacketBufferHelper.writeHashMap(buf, defenseLayer.getStyleFactor());
        PacketBufferHelper.writeHashMap(buf, defenseLayer.getElementFactor());
    }

    @Override
    public void handle(Context context) {
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.enqueueWork(() -> {
            if (!isValid()) {
                return;
            }

            Optional<Level> level = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
            if (!level.isPresent()) {
                ElementalCombat.LOGGER.warn(" context could not provide a ClientLevel.");
                return;
            }

            processMessage(level.get());
            context.setPacketHandled(true);
        });
    }

    private void processMessage(Level level) {
        Entity entity = level.getEntity(id);
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            DefenseDataHelper.get(livingEntity).putLayer(new ResourceLocation(location), defenseLayer);
        }
    }

    private boolean isValid() {
        return id != null && location != null && defenseLayer != null && defenseLayer.getStyleFactor() != null && defenseLayer.getElementFactor() != null;
    }
}
