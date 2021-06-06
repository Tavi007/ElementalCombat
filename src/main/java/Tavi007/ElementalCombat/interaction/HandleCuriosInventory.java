package Tavi007.ElementalCombat.interaction;

import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.api.defense.DefenseLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.Curios;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

public class HandleCuriosInventory {
	@SubscribeEvent
	public static void onCurioChange(CurioChangeEvent event) {
		// get data
		DefenseData defDataItemFrom = DefenseDataAPI.get(event.getFrom());
		DefenseData defDataItemTo = DefenseDataAPI.get(event.getTo());

		// compute change
		DefenseLayer layer = new DefenseLayer();
		layer.subtractLayer(defDataItemFrom.toLayer());
		layer.addLayer(defDataItemTo.toLayer());

		// apply change
		DefenseDataAPI.get(event.getEntityLiving()).addLayer(layer, new ResourceLocation(Curios.MODID, "armor"));
	}
}
