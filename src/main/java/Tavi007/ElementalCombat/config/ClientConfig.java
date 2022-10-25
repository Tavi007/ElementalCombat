package Tavi007.ElementalCombat.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ClientConfig {

    public static final ForgeConfigSpec CONFIG_SPEC;
    private static final ClientConfig CLIENT;

    private static boolean enableHUD = true;

    private final EnumValue hudAnchor;
    private final IntValue xOffSet;
    private final IntValue yOffSet;
    private final DoubleValue scale;
    private final IntValue iterationSpeed;

    private final BooleanValue doubleRowDefenseHUD;
    private final BooleanValue doubleRowDefenseTooltip;
    private final BooleanValue activeHWYLA;
    private final BooleanValue doubleRowDefenseHWYLA;

    static {
        Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);

        CONFIG_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    ClientConfig(ForgeConfigSpec.Builder builder) {
        hudAnchor = builder
            .comment("Decides in which corner the Hud should be displayed.")
            .defineEnum("hudAnchor", HudAnchor.BOTTOM_RIGHT);
        xOffSet = builder
            .comment("Offsets the hud along the x-coordinate. The direction depends on HudAnchor")
            .defineInRange("xOffSet", 0, 0, 500);
        yOffSet = builder
            .comment("Offsets the hud along the y-coordinate. The direction depends on HudAnchor")
            .defineInRange("yOffSet", 0, 0, 500);
        scale = builder
            .comment("The multiplier of the combat data HUD size.")
            .defineInRange("scale", 1.0D, 0.25D, 4.0D);
        iterationSpeed = builder
            .comment("The number of ticks until the defense data iterates in any HUD element.")
            .defineInRange("ticks", 30, 1, 200);
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
        if (enableHUD) {
            enableHUD = false;
        } else {
            enableHUD = true;
        }
    }

    public static boolean isLeft() {
        return HudAnchor.BOTTOM_LEFT.equals(CLIENT.hudAnchor.get())
            || HudAnchor.TOP_LEFT.equals(CLIENT.hudAnchor.get());
    }

    public static boolean isTop() {
        return HudAnchor.TOP_LEFT.equals(CLIENT.hudAnchor.get())
            || HudAnchor.TOP_RIGHT.equals(CLIENT.hudAnchor.get());
    }

    public static int getXOffset() {
        return CLIENT.xOffSet.get();
    }

    public static int getYOffset() {
        return CLIENT.yOffSet.get();
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
