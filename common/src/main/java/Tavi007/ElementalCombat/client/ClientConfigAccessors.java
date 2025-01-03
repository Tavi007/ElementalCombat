package Tavi007.ElementalCombat.client;

import Tavi007.ElementalCombat.client.gui.HudAnchor;

import java.util.function.Supplier;

public class ClientConfigAccessors {


    private static boolean enableHUD = true;
    private static Supplier<Integer> xOffset;
    private static Supplier<Integer> yOffset;
    private static Supplier<Double> scale;
    private static Supplier<Integer> iterationSpeed;
    private static Supplier<Boolean> doubleRowDefenseHUD;
    private static Supplier<Boolean> doubleRowDefenseTooltip;
    private static Supplier<HudAnchor> hudAnchor;

    public static void init(Supplier<HudAnchor> hudAnchor,
                            Supplier<Integer> xOffset,
                            Supplier<Integer> yOffset,
                            Supplier<Double> scale,
                            Supplier<Integer> iterationSpeed,
                            Supplier<Boolean> doubleRowDefenseHUD,
                            Supplier<Boolean> doubleRowDefenseTooltip) {
        ClientConfigAccessors.hudAnchor = hudAnchor;
        ClientConfigAccessors.xOffset = xOffset;
        ClientConfigAccessors.yOffset = yOffset;
        ClientConfigAccessors.scale = scale;
        ClientConfigAccessors.iterationSpeed = iterationSpeed;
        ClientConfigAccessors.doubleRowDefenseHUD = doubleRowDefenseHUD;
        ClientConfigAccessors.doubleRowDefenseTooltip = doubleRowDefenseTooltip;
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

    public static int getIterationSpeed() {
        return ClientConfigAccessors.iterationSpeed.get();
    }

    public static boolean isDoubleRowDefenseHUD() {
        return ClientConfigAccessors.doubleRowDefenseHUD.get();
    }

    public static boolean isDoubleRowDefenseTooltip() {
        return ClientConfigAccessors.doubleRowDefenseTooltip.get();
    }

    public static boolean isHUDEnabled() {
        return ClientConfigAccessors.enableHUD;
    }

    public static void toogleHUD() {
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
