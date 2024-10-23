package Tavi007.ElementalCombat.server;

import java.util.function.Supplier;

public class ServerConfig {

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
        ServerConfig.maxFactor = maxFactor;
        ServerConfig.enchantmentScalingElement = enchantmentScalingElement;
        ServerConfig.enchantmentScalingStyle = enchantmentScalingStyle;
        ServerConfig.potionScaling = potionScaling;
        ServerConfig.essenceSpawnWeight = essenceSpawnWeight;
    }

    public static int getMaxFactor() {
        return ServerConfig.maxFactor.get();
    }

    public static int getEnchantmentScalingElement() {
        return ServerConfig.enchantmentScalingElement.get();
    }

    public static int getEnchantmentScalingStyle() {
        return ServerConfig.enchantmentScalingStyle.get();
    }

    public static int getPotionScaling() {
        return ServerConfig.potionScaling.get();
    }

    public static double getEssenceSpawnWeight() {
        return ServerConfig.essenceSpawnWeight.get();
    }
}
