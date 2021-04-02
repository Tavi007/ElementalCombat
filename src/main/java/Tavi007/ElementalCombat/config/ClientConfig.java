package Tavi007.ElementalCombat.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ClientConfig
{
	public static final ForgeConfigSpec CONFIG_SPEC;
	private static final ClientConfig CLIENT;

	private static boolean enableHUD = true;
	
	private final BooleanValue isTop;
	private final BooleanValue isLeft;
	private final DoubleValue scale;
	private final IntValue iterationSpeed;

	private final BooleanValue doubleRowDefenseHUD;
	private final BooleanValue doubleRowDefenseTooltip;
	private final BooleanValue activeHWYLA;
	private final BooleanValue doubleRowDefenseHWYLA;

	static
	{
		Pair<ClientConfig,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);

		CONFIG_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

	ClientConfig(ForgeConfigSpec.Builder builder)
	{
		isTop = builder
				.comment("If true, displays combat data HUD on the top side.")
				.define("isTop", false);
		isLeft = builder
				.comment("If true, displays combat data HUD on the left side.")
				.define("isLeft", false);
		scale = builder
				.comment("The multiplier of the combat data HUD size.")
				.defineInRange("scale", 1.0D, 0.25D, 4.0D);
		iterationSpeed = builder
				.comment("The number of ticks until the defense data iterates in any HUD element. 20 ticks are about 1 second.")
				.defineInRange("ticks", 20, 1, 100);
		doubleRowDefenseHUD = builder
				.comment("If true, displays the defense values of the HUD in two rows (split in element and style).")
				.define("doubleRowDefenseHUD", true);
		doubleRowDefenseTooltip = builder
				.comment("If true, displays the defense values of the item tooltip in two rows (split in element and style).")
				.define("doubleRowDefenseTooltip", true);
		activeHWYLA = builder
				.comment("Activate/Deactivate WAILA/HWYLA plugin.")
				.define("activateWaila", true);
		doubleRowDefenseHWYLA = builder
				.comment("If true, displays the defense values of the WAILA/HWYLA tooltip in two rows (split in element and style).")
				.define("doubleRowDefenseHWYLA", true);
	}

	public static boolean isHUDEnabled() {
		return enableHUD;
	}
	
	public static void toogleHUD() {
		if (enableHUD) {enableHUD = false;}
		else {enableHUD = true;}
	}

	public static boolean isLeft() {
		return CLIENT.isLeft.get();
	}

	public static boolean isTop() {
		return CLIENT.isTop.get();
	}

	public static double scale() {
		return CLIENT.scale.get();
	}

	public static int iterationSpeed() {
		return CLIENT.iterationSpeed.get();
	}

	public static boolean isDoubleRowDefenseHUD() {
		return CLIENT.doubleRowDefenseHUD.get();
	}

	public static boolean isDoubleRowDefenseTooltip() {
		return CLIENT.doubleRowDefenseTooltip.get();
	}

	public static boolean isHWYLAActive() {
		return CLIENT.activeHWYLA.get();
	}

	public static boolean isDoubleRowDefenseHWYLA() {
		return CLIENT.doubleRowDefenseHWYLA.get();
	}
}