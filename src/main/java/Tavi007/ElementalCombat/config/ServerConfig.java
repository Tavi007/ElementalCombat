package Tavi007.ElementalCombat.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ServerConfig {

    public static final ForgeConfigSpec CONFIG_SPEC;
    private static final ServerConfig SERVER;

    private final ConfigValue<String> defaultElement;
    private final ConfigValue<String> defaultStyle;
    private final IntValue maxFactor;
    private final BooleanValue overrideVanillaEnchantment;
    private final IntValue enchantmentScalingElement;
    private final IntValue enchantmentScalingStyle;

    static {
        Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);

        CONFIG_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    ServerConfig(ForgeConfigSpec.Builder builder) {
        defaultElement = builder
            .comment("The default element.")
            .define("defaultElement", "normal");
        defaultStyle = builder
            .comment("The default style.")
            .define("defaultStyle", "hit");
        maxFactor = builder
            .comment("The maximal combat factor. See vanilla Enchantment Protection Factor.")
            .defineInRange("maxFactor", 100, 1, 200);
        enchantmentScalingElement = builder
            .comment("Scaling for (element) protection enchantments. See vanilla Enchantment Protection Factor.")
            .defineInRange("enchantmentScaling", 5, 1, 15);
        enchantmentScalingStyle = builder
            .comment("Scaling for (style) protection enchantments. See vanilla Enchantment Protection Factor.")
            .defineInRange("enchantmentScaling", 5, 1, 15);
        overrideVanillaEnchantment = builder
            .comment("When true, vanilla protection enchantments will be overriden so they will use the elemental combat protection system.")
            .define("overrideVanillaEnchantment", true);
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

    public static String getDefaultStyle() {
        return SERVER.defaultStyle.get();
    }

    public static String getDefaultElement() {
        return SERVER.defaultElement.get();
    }

    public static boolean shouldVanillaEnchantmentsBeOverriden() {
        return SERVER.overrideVanillaEnchantment.get();
    }
}
