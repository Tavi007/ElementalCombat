package Tavi007.ElementalCombat.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import Tavi007.ElementalCombat.util.RenderHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class CombatDataLayerComponent implements TooltipComponent {

    private static final Font fontRenderer = Minecraft.getInstance().font;
    private static final String textAttack = "Attack:";
    private static final String textDefense = "Defense:";
    private static final int widthAttack = fontRenderer.width(textAttack) + 2;
    private static final int widthDefense = fontRenderer.width(textDefense) + 2;

    public static final int iconSize = 8;
    public static final int maxLineHeight = fontRenderer.lineHeight + 1;
    public static final int maxLineWidth = fontRenderer.width(textDefense + " -999%") + iconSize + 2;

    private static int iteratorCounter = 0;

    private AttackLayer attackLayer = new AttackLayer();
    private DefenseLayer defenseLayer = new DefenseLayer();

    private boolean alwaysShowAttack = true;
    private boolean alwaysShowDefense = true;
    private boolean showDefenseInDoubleRow = true;

    public CombatDataLayerComponent(AttackLayer attackLayer, DefenseLayer defenseLayer,
            boolean alwaysShowAttack, boolean alwaysShowDefense, boolean showDefenseInDoubleRow) {
        this.attackLayer = attackLayer;
        this.defenseLayer = defenseLayer;
        this.alwaysShowAttack = alwaysShowAttack;
        this.alwaysShowDefense = alwaysShowDefense;
        this.showDefenseInDoubleRow = showDefenseInDoubleRow;
    }

    public int getNumberOfRows() {
        int noRows = 0;
        if (shouldAttackLayerBeDisplayed()) {
            noRows++;
        }

        if (shouldDefenseLayerBeDisplayed()) {
            if (showDefenseInDoubleRow) {
                if (hasStyleDefense()) {
                    noRows++;
                }
                if (hasElementDefense()) {
                    noRows++;
                }
            } else {
                noRows++;
            }
        }
        return noRows;
    }

    public List<String> getTooltip() {
        List<String> tooltip = new ArrayList<>();
        if (shouldAttackLayerBeDisplayed()) {
            tooltip.add(ChatFormatting.GRAY + RenderHelper.textAttack);
        }

        if (shouldDefenseLayerBeDisplayed()) {
            if (showDefenseInDoubleRow) {
                if (hasStyleDefense()) {
                    int factor = getCurrentFactor(defenseLayer.getStyleFactor());
                    tooltip.add(ChatFormatting.GRAY +
                        textDefense +
                        "   " +
                        getPercentage(factor, false));
                }
                if (hasElementDefense()) {
                    int factor = getCurrentFactor(defenseLayer.getElementFactor());
                    tooltip.add(ChatFormatting.GRAY +
                        textDefense +
                        "   " +
                        getPercentage(factor, false));
                }
            } else {
                HashMap<String, Integer> map = new HashMap<>();
                map.putAll(defenseLayer.getStyleFactor());
                map.putAll(defenseLayer.getElementFactor());
                int factor = getCurrentFactor(map);
                tooltip.add(ChatFormatting.GRAY +
                    textDefense +
                    "   " +
                    getPercentage(factor, false));
            }
        }
        return tooltip;
    }

    public List<TextureData> getTextureData(int posX, int posY) {
        List<TextureData> textureData = new ArrayList<>();
        if (shouldAttackLayerBeDisplayed()) {
            textureData.add(new TextureData(
                attackLayer.getElement(),
                posX + widthAttack,
                posY));
            textureData.add(new TextureData(
                attackLayer.getStyle(),
                posX + widthAttack + iconSize + 2,
                posY));
            posY += maxLineHeight;
        }

        if (shouldDefenseLayerBeDisplayed()) {
            if (showDefenseInDoubleRow) {
                if (hasStyleDefense()) {
                    textureData.add(new TextureData(
                        getCurrentName(defenseLayer.getStyleFactor()),
                        posX + widthDefense,
                        posY));
                    posY += maxLineHeight;
                }
                if (hasElementDefense()) {
                    textureData.add(new TextureData(
                        getCurrentName(defenseLayer.getElementFactor()),
                        posX + widthDefense,
                        posY));
                }
            } else {
                HashMap<String, Integer> map = new HashMap<>();
                map.putAll(defenseLayer.getStyleFactor());
                map.putAll(defenseLayer.getElementFactor());
                textureData.add(new TextureData(
                    getCurrentName(map),
                    posX + widthDefense,
                    posY));
            }
        }
        return textureData;
    }

    private boolean shouldAttackLayerBeDisplayed() {
        return alwaysShowAttack
            || (attackLayer != null && !attackLayer.isDefault());
    }

    private boolean shouldDefenseLayerBeDisplayed() {
        return alwaysShowDefense
            || (defenseLayer != null && !defenseLayer.isEmpty());
    }

    private boolean hasElementDefense() {
        return !defenseLayer.isElementEmpty();
    }

    private boolean hasStyleDefense() {
        return !defenseLayer.isStyleEmpty();
    }

    private int getCurrentFactor(HashMap<String, Integer> map) {
        return (new ArrayList<Integer>(map.values())).get(iteratorCounter % map.size());
    }

    private String getCurrentName(HashMap<String, Integer> map) {
        return (new ArrayList<String>(map.keySet())).get(iteratorCounter % map.size());
    }

    private static String getPercentage(Integer factor, boolean isStyle) {
        Integer percentage = Math.round(DefenseDataHelper.getPercentage(factor, isStyle) * 100);
        ChatFormatting ChatFormatting = net.minecraft.ChatFormatting.GRAY;
        if (percentage < 0) {
            ChatFormatting = ChatFormatting.RED;
        }
        if (percentage > 0 && percentage < 100) {
            ChatFormatting = ChatFormatting.BLUE;
        }
        if (percentage == 100) {
            ChatFormatting = ChatFormatting.YELLOW;
        }
        if (percentage > 100) {
            ChatFormatting = ChatFormatting.GREEN;
        }

        if (percentage < 0) {
            return ChatFormatting + String.valueOf(percentage) + "%" + ChatFormatting.RESET;
        } else {
            return ChatFormatting + "+" + String.valueOf(percentage) + "%" + ChatFormatting.RESET;
        }
    }
}
