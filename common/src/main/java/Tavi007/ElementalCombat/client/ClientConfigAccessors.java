package Tavi007.ElementalCombat.client;

import Tavi007.ElementalCombat.client.gui.HudAnchor;

import java.util.function.Supplier;

public class ClientConfigAccessors {


    private static boolean enableHUD = true;
    private static Supplier<Integer> xOffset;
    private static Supplier<Integer> yOffset;
    private static Supplier<Double> scale;
    private static Supplier<HudAnchor> hudAnchor;

    public static void init(Supplier<HudAnchor> hudAnchor,
                            Supplier<Integer> xOffset,
                            Supplier<Integer> yOffset,
                            Supplier<Double> scale) {
        ClientConfigAccessors.hudAnchor = hudAnchor;
        ClientConfigAccessors.xOffset = xOffset;
        ClientConfigAccessors.yOffset = yOffset;
        ClientConfigAccessors.scale = scale;
    }

    public static HudAnchor getHudAnchor() {
        return ClientConfigAccessors.hudAnchor.get();
    }

    public static int getXOffset() {
        return ClientConfigAccessors.xOffset.get();
    }

    public static int getYOffset() {
        return ClientConfigAccessors.yOffset.get();
    }

    public static double getScale() {
        return ClientConfigAccessors.scale.get();
    }


    public static boolean isHUDEnabled() {
        return ClientConfigAccessors.enableHUD;
    }

    public static void toggleHUD() {
        ClientConfigAccessors.enableHUD = !ClientConfigAccessors.enableHUD;
    }

    public static boolean isLeft() {
        return HudAnchor.BOTTOM_LEFT.equals(hudAnchor.get())
                || HudAnchor.TOP_LEFT.equals(ClientConfigAccessors.hudAnchor.get());
    }

    public static boolean isTop() {
        return HudAnchor.TOP_LEFT.equals(ClientConfigAccessors.hudAnchor.get())
                || HudAnchor.TOP_RIGHT.equals(ClientConfigAccessors.hudAnchor.get());
    }
}
