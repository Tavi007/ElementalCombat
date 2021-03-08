package Tavi007.ElementalCombat.interaction;

import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

public class HandleCuriosInventory {
	@SubscribeEvent
	public static void onCurioChange(CurioChangeEvent event) {
		// get data
		DefenseData defDataItemFrom = DefenseDataAPI.get(event.getFrom());
		DefenseData defDataItemTo = DefenseDataAPI.get(event.getTo());

		// compute change
		DefenseData newData = new DefenseData();
		newData.substract(defDataItemFrom);
		newData.add(defDataItemTo);

		// apply change
		DefenseDataAPI.add(event.getEntityLiving(), newData);
	}
}
