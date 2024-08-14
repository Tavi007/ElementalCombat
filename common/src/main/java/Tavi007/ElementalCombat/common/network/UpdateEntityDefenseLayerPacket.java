package Tavi007.ElementalCombat.common.network;

import Tavi007.ElementalCombat.common.ElementalCombat;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.util.DamageCalculationHelper;
import Tavi007.ElementalCombat.common.util.PacketBufferHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.Optional;

public class UpdateEntityDefenseLayerPacket extends AbstractPacket {

    private final Integer id;
    private final String location;
    private final DefenseLayer defenseLayer;

    public UpdateEntityDefenseLayerPacket(DefenseLayer defenseLayer, ResourceLocation location, Integer id) {
        this.defenseLayer = defenseLayer;
        this.location = location.toString();
        this.id = id;
    }

    public UpdateEntityDefenseLayerPacket(FriendlyByteBuf buf) {
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
        if (entity instanceof LivingEntity livingEntity) {
            DamageCalculationHelper.get(livingEntity).putLayer(new ResourceLocation(location), defenseLayer);
        }
    }

    private boolean isValid() {
        return id != null && location != null && defenseLayer != null && defenseLayer.getStyleFactor() != null && defenseLayer.getElementFactor() != null;
    }
}
