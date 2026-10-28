package Tavi007.ElementalCombat.common.network.packages;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackLayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record UpdateEntityAttackLayerPacket(
        int id,
        ResourceLocation location,
        AttackLayer layer
) implements IPacket {

    public static UpdateEntityAttackLayerPacket read(FriendlyByteBuf buf) {
        return new UpdateEntityAttackLayerPacket(
                buf.readInt(),
                buf.readResourceLocation(),
                AttackLayer.read(buf)
        );
    }

    public static void write(FriendlyByteBuf buf, UpdateEntityAttackLayerPacket packet) {
        buf.writeInt(packet.id);
        buf.writeResourceLocation(packet.location);
        AttackLayer.write(buf, packet.layer);
    }

    public void encode(FriendlyByteBuf buf) {
        write(buf, this);
    }

    @Override
    public boolean isValid() {
        return location != null
                && layer != null
                && layer.isValid();
    }

    @Override
    public void processPacket(Optional<Level> level) {
        if (level.isEmpty()) {
            Constants.LOG.warn("Sender without level encountered. Skip UpdateEntityAttackLayerPacket.");
            return;
        }
        Entity entity = level.get().getEntity(id);
        if (entity instanceof LivingEntity livingEntity) {
            CapabilitiesAccessors.getAttackConfig(livingEntity).put(location, layer);
        }
    }
}
