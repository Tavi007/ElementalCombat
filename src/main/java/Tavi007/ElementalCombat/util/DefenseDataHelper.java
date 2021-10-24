package Tavi007.ElementalCombat.util;

import java.util.HashMap;

import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseDataCapability;
import Tavi007.ElementalCombat.config.ServerConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class DefenseDataHelper {

	public static DefenseData get(LivingEntity entity) {
		DefenseData defenseData = (DefenseData) entity.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
		if(!defenseData.isInitialized()) {
			defenseData.initialize(entity);
		}
		return defenseData;
	}
	
	public static void sumMaps(HashMap<String, Integer> base, HashMap<String, Integer> additional) {
		additional.forEach((key, value) -> {
			if(base.containsKey(key)) {
				base.put(key, value + base.get(key));
			} else {
				base.put(key, value);
			}
		});
	}
	
	public static DefenseData get(ItemStack stack) {
		if(stack.isEmpty()) {
			return new DefenseData();
		}
		else {
			DefenseData defenseData = (DefenseData) stack.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
			if(!defenseData.isInitialized()) {
				defenseData.initialize(stack);
			}
			if (!defenseData.areEnchantmentChangesApplied()) {
				defenseData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
			}
			return defenseData;
		}
	}

	public static float getScaling(HashMap<String, Integer> map, String key) {
		Integer factor = map.getOrDefault(key, 0);
		return 1.0f - getPercentage(factor);
	}

	public static float getPercentage(Integer factor) {
		return ((float) factor)/ServerConfig.getMaxFactor();
	}
}
