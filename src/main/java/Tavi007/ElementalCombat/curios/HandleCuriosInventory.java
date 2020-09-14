package Tavi007.ElementalCombat.curios;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class HandleCuriosInventory {
	public static DefenseData getDefenseData(LivingEntity entity) {
		DefenseData defData = new DefenseData();

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
					defData.sum(defDataItem);
				}
			}
		}
		return defData;
	}
}
