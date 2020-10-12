package Tavi007.ElementalCombat.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class ServerConfig {
	public static final ForgeConfigSpec CONFIG_SPEC;
	private static final ServerConfig SERVER;

	public final BooleanValue useEmoji;
	
	static
	{
		Pair<ServerConfig,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);

		CONFIG_SPEC = specPair.getRight();
		SERVER = specPair.getLeft();
	}

	ServerConfig(ForgeConfigSpec.Builder builder)
	{
		useEmoji = builder
				.comment("true if emoji should be used for enchantments data, false will use text only")
				.define("useEmoji", false);
	}

	public static boolean useEmoji()
	{
		return SERVER.useEmoji.get();
	}
}
