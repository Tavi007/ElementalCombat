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
				.comment("true if emoji should be used for enchantments data, false will use text only")
				.define("isEmojiEnabled", true);
		defaultElement = builder
				.comment("true if emoji should be used for enchantments data, false will use text only")
				.define("defaultElement", "natural");
		defaultStyle = builder
				.comment("true if emoji should be used for enchantments data, false will use text only")
				.define("defaultStyle", "basic");
		maxFactor = builder
				.comment("true if emoji should be used for enchantments data, false will use text only")
				.defineInRange("maxFactor", 25, 1, 100);
		enchantmentScaling = builder
				.comment("true if emoji should be used for enchantments data, false will use text only")
				.defineInRange("enchantmentScaling", 2, 1, 10);
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
