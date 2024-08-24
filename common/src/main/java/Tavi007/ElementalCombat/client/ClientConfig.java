package Tavi007.ElementalCombat.client;

import Tavi007.ElementalCombat.client.gui.HudAnchor;

public class ClientConfig {


    private static boolean enableHUD = true;

    private static HudAnchor hudAnchor;
    private static int xOffset;
    private static int yOffset;
    private static double scale;
    private static int iterationSpeed;
    private static boolean doubleRowDefenseHUD;
    private static boolean doubleRowDefenseTooltip;


    public static void init(HudAnchor hudAnchor, int xOffset, int yOffset, double scale, int iterationSpeed, boolean doubleRowDefenseHUD, boolean doubleRowDefenseTooltip) {
        ClientConfig.hudAnchor = hudAnchor;
        ClientConfig.xOffset = xOffset;
        ClientConfig.yOffset = yOffset;
        ClientConfig.scale = scale;
        ClientConfig.iterationSpeed = iterationSpeed;
        ClientConfig.doubleRowDefenseHUD = doubleRowDefenseHUD;
        ClientConfig.doubleRowDefenseTooltip = doubleRowDefenseTooltip;
    }

    public static HudAnchor getHudAnchor() {
        return ClientConfig.hudAnchor;
    }

    public static int getXOffset() {
        return ClientConfig.xOffset;
    }

    public static int getYOffset() {
        return ClientConfig.yOffset;
    }

    public static double getScale() {
        return ClientConfig.scale;
    }

    public static int getIterationSpeed() {
        return ClientConfig.iterationSpeed;
    }

    public static boolean isDoubleRowDefenseHUD() {
        return ClientConfig.doubleRowDefenseHUD;
    }

    public static boolean isDoubleRowDefenseTooltip() {
        return ClientConfig.doubleRowDefenseTooltip;
    }

    public static boolean isHUDEnabled() {
        return ClientConfig.enableHUD;
    }

    public static void toogleHUD() {
        ClientConfig.enableHUD = !ClientConfig.enableHUD;
    }

    public static boolean isLeft() {
        return HudAnchor.BOTTOM_LEFT.equals(hudAnchor)
                || HudAnchor.TOP_LEFT.equals(ClientConfig.hudAnchor);
    }

    public static boolean isTop() {
        return HudAnchor.TOP_LEFT.equals(ClientConfig.hudAnchor)
                || HudAnchor.TOP_RIGHT.equals(ClientConfig.hudAnchor);
    }
}
