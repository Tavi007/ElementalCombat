package Tavi007.ElementalCombat.common.network;

import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.util.PacketBufferHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class UpdateEntityDefenseLayerPacket extends AbstractPacket {

    private final Integer id;
    private final ResourceLocation resourceLocation;
    private final DefenseLayer defenseLayer;

    public UpdateEntityDefenseLayerPacket(DefenseLayer defenseLayer, ResourceLocation resourceLocation, Integer id) {
        this.defenseLayer = defenseLayer;
        this.resourceLocation = resourceLocation;
        this.id = id;
    }

    public UpdateEntityDefenseLayerPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.resourceLocation = buf.readResourceLocation();
        this.defenseLayer = new DefenseLayer(PacketBufferHelper.readMap(buf), PacketBufferHelper.readMap(buf));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeResourceLocation(resourceLocation);
        PacketBufferHelper.writeMap(buf, defenseLayer.getStyles());
        PacketBufferHelper.writeMap(buf, defenseLayer.getElements());
    }

    public Integer getId() {
        return id;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    @Override
    public void processPacket(Level level) {
        Entity entity = level.getEntity(id);
        if (entity instanceof LivingEntity livingEntity) {
            CapabilitiesAccessors.getDefenseData(livingEntity).putLayer(resourceLocation, defenseLayer);
        }
    }

    @Override
    public boolean isValid() {
        return id != null && resourceLocation != null && defenseLayer != null && defenseLayer.getStyles() != null && defenseLayer.getElements() != null;
    }
}
