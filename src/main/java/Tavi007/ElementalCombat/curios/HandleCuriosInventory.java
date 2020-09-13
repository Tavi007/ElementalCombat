package Tavi007.ElementalCombat.curios;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class HandleCuriosInventory {
	public static DefenseData getDefenseData(LivingEntity entity) {
		DefenseData defData = new DefenseData();
		HashMap<String, Integer> styleFactor = defData.getStyleFactor();
		HashMap<String, Integer> elementFactor = defData.getElementFactor();

		IItemHandlerModifiable itemHandler = CuriosApi.getCuriosHelper().getEquippedCurios(entity).orElse(null);
		if (itemHandler != null) {
			//loop over every curio slot.
			int noSlots = itemHandler.getSlots();
			for (int i = 0; i < noSlots; i++) {
				ItemStack item = itemHandler.getStackInSlot(i);
				if (!item.isEmpty())
				{
					// get defense data and add them up
					DefenseData defDataItem = ElementalCombatAPI.getDefenseDataWithEnchantment(item);
					DefenseDataHelper.sumMaps(styleFactor, defDataItem.getStyleFactor());
					DefenseDataHelper.sumMaps(elementFactor, defDataItem.getElementFactor());
				}
			}
		}
		return defData;
	}
}
