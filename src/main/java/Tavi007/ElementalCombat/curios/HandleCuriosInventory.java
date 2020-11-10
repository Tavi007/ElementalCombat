package Tavi007.ElementalCombat.curios;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

public class HandleCuriosInventory {
	@SubscribeEvent
	public static void onCurioChange(CurioChangeEvent event) {
		// get data
		DefenseData defDataItemFrom = ElementalCombatAPI.getDefenseData(event.getFrom());
		DefenseData defDataItemTo = ElementalCombatAPI.getDefenseData(event.getTo());

		// compute change
		DefenseData newData = new DefenseData();
		newData.substract(defDataItemFrom);
		newData.add(defDataItemTo);

		// apply change
		ElementalCombatAPI.addDefenseData(event.getEntityLiving(), newData);
	}
}
