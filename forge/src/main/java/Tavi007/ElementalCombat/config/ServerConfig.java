package Tavi007.ElementalCombat.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ServerConfig {

    public static final ForgeConfigSpec CONFIG_SPEC;
    private static final ServerConfig SERVER;

    private final IntValue maxFactor;

    private final IntValue enchantmentScalingElement;
    private final IntValue enchantmentScalingStyle;
    private final IntValue potionScaling;

    private final DoubleValue essenceSpawnWeight;

    static {
        Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);

        CONFIG_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    ServerConfig(ForgeConfigSpec.Builder builder) {
        maxFactor = builder
            .comment("The maximal combat factor. See vanilla Enchantment Protection Factor.")
            .defineInRange("maxFactor", 100, 1, 200);
        enchantmentScalingElement = builder
            .comment("Scaling for (element) protection enchantments. See vanilla Enchantment Protection Factor.")
            .defineInRange("enchantmentScaling", 5, 1, 15);
        enchantmentScalingStyle = builder
            .comment("Scaling for (style) protection enchantments. See vanilla Enchantment Protection Factor.")
            .defineInRange("enchantmentScaling", 5, 1, 15);
        potionScaling = builder
            .comment("Scaling for potion effects. Resistance (or Weakness) gained through potion effects will be scaled with this factor")
            .defineInRange("potionScaling", 50, 1, 100);
        essenceSpawnWeight = builder
            .comment(
                "Weight for the essence item to spawn depending on the attack element of the mob. 0 disables this features. 1 is normal spawning rate.")
            .defineInRange("essenceSpawnWeight", 0.5, 0, 1);
    }

    public static int getMaxFactor() {
        return SERVER.maxFactor.get();
    }

    public static int getEnchantmentScalingElement() {
        return SERVER.enchantmentScalingElement.get();
    }

    public static int getEnchantmentScalingStyle() {
        return SERVER.enchantmentScalingStyle.get();
    }

    public static int getPotionScaling() {
        return SERVER.potionScaling.get();
    }

    public static double getEssenceSpawnWeight() {
        return SERVER.essenceSpawnWeight.get();
    }
}
