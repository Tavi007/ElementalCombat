package Tavi007.ElementalCombat.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ServerConfig {
	public static final ForgeConfigSpec CONFIG_SPEC;
	private static final ServerConfig SERVER;

	private final BooleanValue isEmojiEnabled;
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
		isEmojiEnabled = builder
				.comment("If true emojies are used for enchantment data. When false, it will use only text.")
				.define("isEmojiEnabled", false);
		defaultElement = builder
				.comment("The default element.")
				.define("defaultElement", "normal");
		defaultStyle = builder
				.comment("The default style.")
				.define("defaultStyle", "basic");
		maxFactor = builder
				.comment("The maximal combat factor. See vanilla Enchantment Protection Factor.")
				.defineInRange("maxFactor", 100, 1, 200);
		enchantmentScaling = builder
				.comment("scaling for protection enchantments. See vanilla Enchantment Protection Factor.")
				.defineInRange("enchantmentScaling", 8, 1, 25);
	}

	public static boolean isEmojiEnabled()
	{
		return SERVER.isEmojiEnabled.get();
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
