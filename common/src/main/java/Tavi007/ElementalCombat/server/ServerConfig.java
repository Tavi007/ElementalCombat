package Tavi007.ElementalCombat.server;

public class ServerConfig {

    private static int maxFactor;
    private static int enchantmentScalingElement;
    private static int enchantmentScalingStyle;
    private static int potionScaling;
    private static double essenceSpawnWeight;

    public static void init(int maxFactor, int enchantmentScalingElement, int enchantmentScalingStyle, int potionScaling, double essenceSpawnWeight) {
        ServerConfig.maxFactor = maxFactor;
        ServerConfig.enchantmentScalingElement = enchantmentScalingElement;
        ServerConfig.enchantmentScalingStyle = enchantmentScalingStyle;
        ServerConfig.potionScaling = potionScaling;
        ServerConfig.essenceSpawnWeight = essenceSpawnWeight;
    }

    public static int getMaxFactor() {
        return ServerConfig.maxFactor;
    }

    public static int getEnchantmentScalingElement() {
        return ServerConfig.enchantmentScalingElement;
    }

    public static int getEnchantmentScalingStyle() {
        return ServerConfig.enchantmentScalingStyle;
    }

    public static int getPotionScaling() {
        return ServerConfig.potionScaling;
    }

    public static double getEssenceSpawnWeight() {
        return ServerConfig.essenceSpawnWeight;
    }
}
