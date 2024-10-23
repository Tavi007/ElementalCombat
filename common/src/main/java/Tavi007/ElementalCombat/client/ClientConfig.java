package Tavi007.ElementalCombat.client;

import Tavi007.ElementalCombat.client.gui.HudAnchor;

import java.util.function.Supplier;

public class ClientConfig {


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
        ClientConfig.hudAnchor = hudAnchor;
        ClientConfig.xOffset = xOffset;
        ClientConfig.yOffset = yOffset;
        ClientConfig.scale = scale;
        ClientConfig.iterationSpeed = iterationSpeed;
        ClientConfig.doubleRowDefenseHUD = doubleRowDefenseHUD;
        ClientConfig.doubleRowDefenseTooltip = doubleRowDefenseTooltip;
    }

    public static HudAnchor getHudAnchor() {
        return ClientConfig.hudAnchor.get();
    }

    public static int getXOffset() {
        return ClientConfig.xOffset.get();
    }

    public static int getYOffset() {
        return ClientConfig.yOffset.get();
    }

    public static double getScale() {
        return ClientConfig.scale.get();
    }

    public static int getIterationSpeed() {
        return ClientConfig.iterationSpeed.get();
    }

    public static boolean isDoubleRowDefenseHUD() {
        return ClientConfig.doubleRowDefenseHUD.get();
    }

    public static boolean isDoubleRowDefenseTooltip() {
        return ClientConfig.doubleRowDefenseTooltip.get();
    }

    public static boolean isHUDEnabled() {
        return ClientConfig.enableHUD;
    }

    public static void toogleHUD() {
        ClientConfig.enableHUD = !ClientConfig.enableHUD;
    }

    public static boolean isLeft() {
        return HudAnchor.BOTTOM_LEFT.equals(hudAnchor.get())
                || HudAnchor.TOP_LEFT.equals(ClientConfig.hudAnchor.get());
    }

    public static boolean isTop() {
        return HudAnchor.TOP_LEFT.equals(ClientConfig.hudAnchor.get())
                || HudAnchor.TOP_RIGHT.equals(ClientConfig.hudAnchor.get());
    }
}
