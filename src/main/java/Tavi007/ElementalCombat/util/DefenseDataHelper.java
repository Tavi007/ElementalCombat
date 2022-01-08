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
        if (!defenseData.isInitialized()) {
            defenseData.initialize(entity);
        }
        return defenseData;
    }

    public static void updateItemLayer(LivingEntity entity) {

    }

    public static DefenseData get(ItemStack stack) {
        if (stack.isEmpty()) {
            return new DefenseData();
        } else {
            DefenseData defenseData = (DefenseData) stack.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
            if (!defenseData.isInitialized()) {
                defenseData.initialize(stack);
            }
            if (!defenseData.areEnchantmentChangesApplied()) {
                defenseData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
            }
            return defenseData;
        }
    }

    public static float getScaling(HashMap<String, Integer> map, String key, boolean isStyle) {
        Integer factor = map.getOrDefault(key, 0);
        return 1.0f - getPercentage(factor, isStyle);
    }

    public static float getPercentage(Integer factor, boolean isStyle) {
        float percentage = ((float) factor) / ServerConfig.getMaxFactor();
        if (isStyle) {
            return Math.min(1.0f, percentage);
        }
        return percentage;
    }
}
