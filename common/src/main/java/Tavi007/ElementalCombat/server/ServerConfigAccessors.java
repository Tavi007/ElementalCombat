package Tavi007.ElementalCombat.server;

import java.util.function.Supplier;

public class ServerConfigAccessors {

    private static Supplier<Integer> maxFactor;
    private static Supplier<Integer> enchantmentScalingElement;
    private static Supplier<Integer> enchantmentScalingStyle;
    private static Supplier<Integer> potionScaling;
    private static Supplier<Double> essenceSpawnWeight;

    public static void init(Supplier<Integer> maxFactor,
                            Supplier<Integer> enchantmentScalingElement,
                            Supplier<Integer> enchantmentScalingStyle,
                            Supplier<Integer> potionScaling,
                            Supplier<Double> essenceSpawnWeight) {
        ServerConfigAccessors.maxFactor = maxFactor;
        ServerConfigAccessors.enchantmentScalingElement = enchantmentScalingElement;
        ServerConfigAccessors.enchantmentScalingStyle = enchantmentScalingStyle;
        ServerConfigAccessors.potionScaling = potionScaling;
        ServerConfigAccessors.essenceSpawnWeight = essenceSpawnWeight;
    }

    public static int getMaxFactor() {
        return ServerConfigAccessors.maxFactor.get();
    }

    public static int getEnchantmentScalingElement() {
        return ServerConfigAccessors.enchantmentScalingElement.get();
    }

    public static int getEnchantmentScalingStyle() {
        return ServerConfigAccessors.enchantmentScalingStyle.get();
    }

    public static int getPotionScaling() {
        return ServerConfigAccessors.potionScaling.get();
    }

    public static double getEssenceSpawnWeight() {
        return ServerConfigAccessors.essenceSpawnWeight.get();
    }
}
