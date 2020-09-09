package Tavi007.ElementalCombat.curios;

import java.util.ArrayList;
import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class HandleCuriosInventory {
	public static DefenseData getDefenseData(LivingEntity entity) {
		DefenseData defData = new DefenseData();
		HashMap<String, Integer> styleFactor = defData.getStyleFactor();
		HashMap<String, Integer> elementFactor = defData.getElementFactor();
		
		// get the item list
		//LazyOptional<IItemHandlerModifiable> itemHandler = CuriosApi.getCuriosHelper().getEquippedCurios(entity);
		Iterable<ItemStack> iterator = new ArrayList<ItemStack>(); // for now. Later get this from curios API
		iterator.forEach(item -> {
			DefenseData defDataItem = ElementalCombatAPI.getDefenseData(item);
			DefenseDataHelper.sumMaps(styleFactor, defDataItem.getStyleFactor());
			DefenseDataHelper.sumMaps(elementFactor, defDataItem.getElementFactor());
		});
		return defData;
	}
}
