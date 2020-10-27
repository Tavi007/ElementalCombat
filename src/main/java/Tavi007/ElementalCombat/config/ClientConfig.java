package Tavi007.ElementalCombat.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

//@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.MOD)
public class ClientConfig
{
	public static final ForgeConfigSpec CONFIG_SPEC;
	private static final ClientConfig CLIENT;

	private static boolean enableHUD = true;
	
	public final BooleanValue isTop;
	public final BooleanValue isLeft;
	public final DoubleValue scale;
	public final BooleanValue textShadow;

	static
	{
		Pair<ClientConfig,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);

		CONFIG_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

	ClientConfig(ForgeConfigSpec.Builder builder)
	{
		isTop = builder
				.comment("Display information on the top side.")
				.define("isTop", false);
		isLeft = builder
				.comment("Display information on the left side.")
				.define("isLeft", false);
		scale = builder
				.comment("The multiplier of the defense data HUD.")
				.defineInRange("scale", 1.0D, 0.25D, 1.0D);
		textShadow = builder
				.comment("If true the defense data of the player is rendered with a shadow.")
				.define("textShadow", true);
	}

	public static boolean isHUDEnabled()
	{
		return enableHUD;
	}
	
	public static void toogleHUD() {
		if (enableHUD) {enableHUD = false;}
		else {enableHUD = true;}
	}

	public static boolean isLeft()
	{
		return CLIENT.isLeft.get();
	}

	public static boolean isTop()
	{
		return CLIENT.isTop.get();
	}

	public static double scale()
	{
		return CLIENT.scale.get();
	}

	public static boolean textShadow()
	{
		return CLIENT.textShadow.get();
	}
}