package Tavi007.ElementalCombat.common.util;

import java.util.HashMap;

public class DefenseDataHelper {

    private static int maxFactor = 1;

    public static void setMaxFactor(int maxFactor) {
        DefenseDataHelper.maxFactor = maxFactor;
    }

    public static float getScaling(HashMap<String, Integer> map, String key, boolean isStyle) {
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
}
