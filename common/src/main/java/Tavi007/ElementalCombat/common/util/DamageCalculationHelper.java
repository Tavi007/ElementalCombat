package Tavi007.ElementalCombat.common.util;

import Tavi007.ElementalCombat.common.api.DefenseDataAPI;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

public class DamageCalculationHelper {

    private static int maxFactor = 100;

    // TODO: use the function
    public static void setMaxFactor(int maxFactor) {
        DamageCalculationHelper.maxFactor = maxFactor;
    }

    public static float getScaling(Map<String, Integer> map, String key, boolean isStyle) {
        Integer factor = map.getOrDefault(key, 0);
        return 1.0f - getPercentage(factor, isStyle);
    }

    public static float getPercentage(Integer factor, boolean isStyle) {
        float percentage = ((float) factor) / maxFactor;
        if (isStyle) {
            return Math.min(1.0f, percentage);
        }
        return percentage;
    }

    public static float getNewDamageValue(float oldValue, DamageSource damageSource, LivingEntity target) {

        // no modification. Entity should take normal damage and die eventually.
        if (damageSource == null || damageSource.is(DamageTypes.GENERIC)) {
            return oldValue;
        }

        AttackLayer attack = null;
        DefenseLayer defense = DefenseDataAPI.getFullDataAsLayer(target);

        float defenseStyleScaling = getScaling(defense.getStyles(), attack.getStyle(), true);
        float defenseElementScaling = getScaling(defense.getElements(), attack.getElement(), false);

        return oldValue * defenseStyleScaling * defenseElementScaling;
    }
}
