package Tavi007.ElementalCombat.common.network.packages;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record DisableDamageRenderPacket(Integer id) implements IPacket {

    public static DisableDamageRenderPacket read(FriendlyByteBuf buf) {
        return new DisableDamageRenderPacket(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.id);
    }

    @Override
    public boolean isValid() {
        return id != null;
    }

    @Override
    public void processPacket(Optional<Level> level) {
        if (level.isEmpty()) {
            Constants.LOG.warn("Sender without level encountered. Skip DisableDamageRenderPacket.");
            return;
        }
        Entity entity = level.get().getEntity(id);
        if (entity instanceof LivingEntity livingEntity) {
            ImmersionData data = CapabilitiesAccessors.getImmersionData(livingEntity);
            data.disableFlag = true;
        }
    }

}
