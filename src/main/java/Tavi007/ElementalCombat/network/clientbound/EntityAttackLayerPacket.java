package Tavi007.ElementalCombat.network.clientbound;

import java.util.Optional;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.network.Packet;
import Tavi007.ElementalCombat.util.AttackDataHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

public class EntityAttackLayerPacket extends Packet {

    private Integer id;
    private String location;
    private AttackLayer attackLayer;

    public EntityAttackLayerPacket(AttackLayer attackLayer, ResourceLocation location, Integer id) {
        this.attackLayer = attackLayer;
        this.location = location.toString();
        this.id = id;
    }

    public EntityAttackLayerPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.location = buf.readUtf();
        // rest of the combat properties
        this.attackLayer = new AttackLayer(buf.readUtf(), buf.readUtf());
    }

    public void encode(FriendlyByteBuf buf) {
        // get entity through id
        buf.writeInt(id);
        buf.writeUtf(location);
        // write rest of the combat properties
        buf.writeUtf(attackLayer.getStyle());
        buf.writeUtf(attackLayer.getElement());
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

    private boolean isValid() {
        return id != null && location != null && attackLayer != null && attackLayer.getStyle() != null && attackLayer.getElement() != null;
    }

    private void processMessage(Level level) {
        Entity entity = level.getEntity(id);
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            AttackDataHelper.get(livingEntity).putLayer(new ResourceLocation(location), attackLayer);
        }
    }
}
