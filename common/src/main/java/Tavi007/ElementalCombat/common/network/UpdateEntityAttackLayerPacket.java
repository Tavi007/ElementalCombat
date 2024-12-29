package Tavi007.ElementalCombat.common.network;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class UpdateEntityAttackLayerPacket extends AbstractPacket {

    private final Integer id;
    private final ResourceLocation resourceLocation;
    private final AttackLayer attackLayer;

    public UpdateEntityAttackLayerPacket(AttackLayer attackLayer, ResourceLocation resourceLocation, Integer id) {
        this.attackLayer = attackLayer;
        this.resourceLocation = resourceLocation;
        this.id = id;
    }

    public UpdateEntityAttackLayerPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.resourceLocation = buf.readResourceLocation();
        // rest of the combat properties
        this.attackLayer = new AttackLayer(buf.readUtf(), buf.readUtf());
    }

    public void encode(FriendlyByteBuf buf) {
        // get entity through id
        buf.writeInt(id);
        buf.writeResourceLocation(resourceLocation);
        // write rest of the combat properties
        buf.writeUtf(attackLayer.getStyle());
        buf.writeUtf(attackLayer.getElement());
    }

    @Override
    public boolean isValid() {
        return id != null && resourceLocation != null && attackLayer != null && attackLayer.getStyle() != null && attackLayer.getElement() != null;
    }

    @Override
    public void processPacket(Optional<Level> level) {
        if (level.isEmpty()) {
            Constants.LOG.warn("Sender without level encountered. Skip UpdateEntityAttackLayerPacket.");
            return;
        }
        Entity entity = level.get().getEntity(id);
        if (entity instanceof LivingEntity livingEntity) {
            CapabilitiesAccessors.getAttackData(livingEntity).putLayer(resourceLocation, attackLayer);
        }
    }
}
