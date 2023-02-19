package Tavi007.ElementalCombat.interaction;

import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

public class HandleCuriosInventory {

    @SubscribeEvent
    public static void onCurioChange(CurioChangeEvent event) {
        LivingEntity entity = event.getEntityLiving();
        DefenseData data = DefenseDataHelper.get(entity);
        ResourceLocation rl = new ResourceLocation("curios", "armor");
        DefenseLayer layer = data.getLayer(rl);
        if (layer == null) {
            layer = new DefenseLayer();
        }

        // get data
        DefenseData defDataItemFrom = DefenseDataHelper.get(event.getFrom());
        DefenseData defDataItemTo = DefenseDataHelper.get(event.getTo());

        // compute new layer by applying the change
        layer.subtractLayer(defDataItemFrom.toLayer());
        layer.addLayer(defDataItemTo.toLayer());
        DefenseDataAPI.putLayer(entity, layer, rl);
    }
}
