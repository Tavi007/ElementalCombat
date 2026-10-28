package Tavi007.ElementalCombat.common.network.packages;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseLayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record UpdateEntityDefenseLayerPacket(
        Integer id,
        ResourceLocation resourceLocation,
        DefenseLayer layer
) implements IPacket {

    public static UpdateEntityDefenseLayerPacket read(FriendlyByteBuf buf) {
        return new UpdateEntityDefenseLayerPacket(
                buf.readInt(),
                buf.readResourceLocation(),
                DefenseLayer.read(buf));
    }

    public static void write(FriendlyByteBuf buf, UpdateEntityDefenseLayerPacket packet) {
        buf.writeInt(packet.id);
        buf.writeResourceLocation(packet.resourceLocation);
        DefenseLayer.write(buf, packet.layer);
    }

    public void encode(FriendlyByteBuf buf) {
        write(buf, this);
    }

    @Override
    public void processPacket(Optional<Level> level) {
        if (level.isEmpty()) {
            Constants.LOG.warn("Sender without level encountered. Skip UpdateEntityDefenseLayerPacket.");
            return;
        }
        Entity entity = level.get().getEntity(id);
        if (entity instanceof LivingEntity livingEntity) {
            CapabilitiesAccessors.getDefenseConfig(livingEntity).put(resourceLocation, layer);
        }
    }

    @Override
    public boolean isValid() {
        return id != null
                && resourceLocation != null
                && layer != null
                && layer.isValid();
    }
}
