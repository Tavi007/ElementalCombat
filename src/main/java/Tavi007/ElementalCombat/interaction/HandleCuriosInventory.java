package Tavi007.ElementalCombat.interaction;

import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.api.defense.DefenseLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

public class HandleCuriosInventory {
	@SubscribeEvent
	public static void onCurioChange(CurioChangeEvent event) {
		LivingEntity entity = event.getEntityLiving();
		DefenseData data = DefenseDataAPI.get(entity);
		ResourceLocation rl = new ResourceLocation("curios", "armor");
		DefenseLayer layer = data.getLayer(rl);
		if(layer == null) {
			layer = new DefenseLayer();
		}
		
		// get data
		DefenseData defDataItemFrom = DefenseDataAPI.get(event.getFrom());
		DefenseData defDataItemTo = DefenseDataAPI.get(event.getTo());

		// compute change
		layer.subtractLayer(defDataItemFrom.toLayer());
		layer.addLayer(defDataItemTo.toLayer());

		// apply change
		DefenseDataAPI.putLayer(entity, layer, rl);
	}
}
