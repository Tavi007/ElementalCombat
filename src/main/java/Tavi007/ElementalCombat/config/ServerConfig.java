package Tavi007.ElementalCombat.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ServerConfig {
	public static final ForgeConfigSpec CONFIG_SPEC;
	private static final ServerConfig SERVER;

	private final ConfigValue<String> defaultElement;
	private final ConfigValue<String> defaultStyle;
	private final IntValue maxFactor;
	private final IntValue enchantmentScaling;
	
	static
	{
		Pair<ServerConfig,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);

		CONFIG_SPEC = specPair.getRight();
		SERVER = specPair.getLeft();
	}

	ServerConfig(ForgeConfigSpec.Builder builder)
	{
		defaultElement = builder
				.comment("The default element.")
				.define("defaultElement", "natural");
		defaultStyle = builder
				.comment("The default style.")
				.define("defaultStyle", "basic");
		maxFactor = builder
				.comment("The maximal combat factor. See vanilla Enchantment Protection Factor.")
				.defineInRange("maxFactor", 100, 1, 200);
		enchantmentScaling = builder
				.comment("Scaling for protection enchantments. See vanilla Enchantment Protection Factor.")
				.defineInRange("enchantmentScaling", 5, 1, 15);
	}

	public static int getMaxFactor()
	{
		return SERVER.maxFactor.get();
	}

	public static int getEnchantmentScaling()
	{
		return SERVER.enchantmentScaling.get();
	}

	public static String getDefaultStyle()
	{
		return SERVER.defaultStyle.get();
	}

	public static String getDefaultElement()
	{
		return SERVER.defaultElement.get();
	}
}
