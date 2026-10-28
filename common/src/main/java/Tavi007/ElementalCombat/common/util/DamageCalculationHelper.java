package Tavi007.ElementalCombat.common.util;

import Tavi007.ElementalCombat.common.api.AttackAPI;
import Tavi007.ElementalCombat.common.api.DefenseAPI;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseMap;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseProperty;
import Tavi007.ElementalCombat.server.ServerConfigAccessors;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

public class DamageCalculationHelper {

    public static float getScaling(DefenseMap map, String key, boolean isStyle) {
        return getScaling(map.map(), key, isStyle);
    }

    public static float getScaling(Map<String, Integer> map, String key, boolean isStyle) {
        Integer factor = map.getOrDefault(key, 0);
        return 1.0f - getPercentage(factor, isStyle);
    }

    public static float getPercentage(Integer factor, boolean isStyle) {
        float percentage = ((float) factor) / ServerConfigAccessors.getMaxFactor();
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

        AttackProperty attack = AttackAPI.getSummedProperty(damageSource);
        DefenseProperty defense = DefenseAPI.getSummedProperty(target);

        float defenseStyleScaling = getScaling(defense.style(), attack.style(), true);
        float defenseElementScaling = getScaling(defense.element(), attack.element(), false);

        return oldValue * defenseStyleScaling * defenseElementScaling;
    }
}
