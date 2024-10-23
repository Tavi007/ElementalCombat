package Tavi007.ElementalCombat.common.compatibility.curios;

import Tavi007.ElementalCombat.common.api.DefenseDataAPI;
import Tavi007.ElementalCombat.common.api.GainDefenseFromEquipmentEvent;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class HandleCuriosInventory {

    @SubscribeEvent
    public static void onCurioChange(CurioChangeEvent event) {
        LivingEntity entity = event.getEntity();
        DefenseData data = CapabilitiesAccessors.getDefenseData(entity);
        ResourceLocation rl = new ResourceLocation("curios", "armor");
        DefenseLayer layer = data.getLayer(rl);
        if (layer == null) {
            layer = new DefenseLayer();
        }

        // get data
        DefenseData defDataItemFrom = CapabilitiesAccessors.getDefenseData(event.getFrom());
        DefenseData defDataItemTo = CapabilitiesAccessors.getDefenseData(event.getTo());

        // compute new layer by applying the change
        layer.subtractLayer(defDataItemFrom.toLayer());
        layer.addLayer(defDataItemTo.toLayer());
        DefenseDataAPI.putLayer(entity, layer, rl);
    }

    @SubscribeEvent
    public static void gainDefenseFromEquipmentEvent(GainDefenseFromEquipmentEvent event) {
        if (EquipmentSlot.Type.HAND.equals(event.getEquipmentSlot().getType())) {
            event.setCanceled(event.getItemStack().getItem() instanceof ICurioItem);
        }
    }
}
