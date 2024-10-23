package Tavi007.ElementalCombat.client;

import Tavi007.ElementalCombat.client.gui.HudAnchor;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ForgeClientConfig {

    public static final ForgeConfigSpec CONFIG_SPEC;
    private static final ForgeClientConfig CLIENT;

    private static final boolean enableHUD = true;

    static {
        Pair<ForgeClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ForgeClientConfig::new);

        CONFIG_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    ForgeClientConfig(ForgeConfigSpec.Builder builder) {
        ClientConfig.init(builder
                        .comment("Decides in which corner the Hud should be displayed.")
                        .defineEnum("hudAnchor", HudAnchor.BOTTOM_RIGHT),
                builder
                        .comment("Offsets the hud along the x-coordinate. The direction depends on HudAnchor")
                        .defineInRange("xOffSet", 0, 0, 500),
                builder
                        .comment("Offsets the hud along the y-coordinate. The direction depends on HudAnchor")
                        .defineInRange("yOffSet", 0, 0, 500),
                builder
                        .comment("The multiplier of the combat data HUD size.")
                        .defineInRange("scale", 1.0D, 0.25D, 4.0D),
                builder
                        .comment("value for the speed of defense data iteratation in any HUD element. 45 equals about 1 second")
                        .defineInRange("speed", 45, 20, 100),
                builder
                        .comment("If true, displays the defense values of the HUD in two rows (split in element and style).")
                        .define("doubleRowDefenseHUD", true),
                builder
                        .comment("If true, displays the defense values of the item tooltip in two rows (split in element and style).")
                        .define("doubleRowDefenseTooltip", true)
        );
    }
}
