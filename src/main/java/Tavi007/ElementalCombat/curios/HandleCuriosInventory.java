package Tavi007.ElementalCombat.curios;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class HandleCuriosInventory {
	@SubscribeEvent
	public static void onCurioChange(CurioChangeEvent event) {
		// get Data
		DefenseData defDataItemFrom = ElementalCombatAPI.getDefenseData(event.getFrom());
		DefenseData defDataItemTo = ElementalCombatAPI.getDefenseData(event.getTo());

		// compute Change
		DefenseData newData = new DefenseData();
		newData.substract(defDataItemFrom);
		newData.add(defDataItemTo);

		// apply change
		ElementalCombatAPI.addDefenseData(event.getEntityLiving(), newData);
	}
}
