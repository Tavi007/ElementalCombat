package Tavi007.ElementalCombat;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

public class Configuration
{
	public static final ForgeConfigSpec CONFIG_SPEC;
	private static final Configuration CONFIG;

	public final BooleanValue enabled;
	public final BooleanValue isTop;
	public final BooleanValue isLeft;
	public final DoubleValue scale;
	public final BooleanValue textShadow;

	static
	{
		Pair<Configuration,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Configuration::new);

		CONFIG_SPEC = specPair.getRight();
		CONFIG = specPair.getLeft();
	}

	Configuration(ForgeConfigSpec.Builder builder)
	{
		enabled = builder
				.comment("true if the defense data of the player should be shown, false otherwise")
				.define("enabled", true);
		isTop = builder
				.comment("Display information on the top side")
				.define("isTop", false);
		isLeft = builder
				.comment("Display information on the left side")
				.define("isLeft", false);
		scale = builder
				.comment("The size of the defense data of the player (multiplier)")
				.defineInRange("scale", 1.0D, 0.1D, 1.0D);
		textShadow = builder
				.comment("true if the defense data of the player should be rendered with a shadow, false otherwise")
				.define("textShadow", true);
	}

	public static boolean enabled()
	{
		return CONFIG.enabled.get();
	}

	public static boolean isLeft()
	{
		return CONFIG.isLeft.get();
	}

	public static boolean isTop()
	{
		return CONFIG.isTop.get();
	}

	public static double scale()
	{
		return CONFIG.scale.get();
	}

	public static boolean textShadow()
	{
		return CONFIG.textShadow.get();
	}
}