package Tavi007.ElementalCombat.client.gui;

import Tavi007.ElementalCombat.client.ClientConfig;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.util.DamageCalculationHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombatDataLayerComponent implements TooltipComponent {

    public static final int iconSize = 8;
    private static final String textAttack = "Attack:";
    private static final String textDefense = "Defense:";
    private static int tickCounter = 0;
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

    public static void increaseTickCounter() { // call once per game tick
        tickCounter++;
        if (tickCounter >= ClientConfig.getIterationSpeed()) {
            iteratorCounter++;
            if (iteratorCounter == Integer.MAX_VALUE) {
                iteratorCounter = 0;
            }
            tickCounter = 0;
        }
    }

    private static String getPercentage(Integer factor, boolean isStyle) {
        Integer percentage = Math.round(DamageCalculationHelper.getPercentage(factor, isStyle) * 100);
        ChatFormatting ChatFormatting = net.minecraft.ChatFormatting.GRAY;
        if (percentage < 0) {
            ChatFormatting = net.minecraft.ChatFormatting.RED;
        }
        if (percentage > 0 && percentage < 100) {
            ChatFormatting = net.minecraft.ChatFormatting.BLUE;
        }
        if (percentage == 100) {
            ChatFormatting = net.minecraft.ChatFormatting.YELLOW;
        }
        if (percentage > 100) {
            ChatFormatting = net.minecraft.ChatFormatting.GREEN;
        }

        if (percentage < 0) {
            return ChatFormatting + String.valueOf(percentage) + "%" + net.minecraft.ChatFormatting.RESET;
        } else {
            return ChatFormatting + "+" + percentage + "%" + net.minecraft.ChatFormatting.RESET;
        }
    }

    public int getWidth(Font font) {
        if (hasDefense()) {
            return font.width(textDefense + " -999%") + iconSize + 2;
        }
        return font.width(textAttack) + (iconSize + 2) * 2;
    }

    public int getLineHeight(Font font) {
        return font.lineHeight + 1;
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
            tooltip.add(ChatFormatting.GRAY + textAttack);
        }

        if (shouldDefenseLayerBeDisplayed()) {
            if (showDefenseInDoubleRow) {
                if (hasStyleDefense()) {
                    int factor = getCurrentFactor(defenseLayer.getStyles());
                    tooltip.add(ChatFormatting.GRAY +
                            textDefense +
                            "   " +
                            getPercentage(factor, false));
                }
                if (hasElementDefense()) {
                    int factor = getCurrentFactor(defenseLayer.getElements());
                    tooltip.add(ChatFormatting.GRAY +
                            textDefense +
                            "   " +
                            getPercentage(factor, false));
                }
            } else {
                HashMap<String, Integer> map = new HashMap<>();
                map.putAll(defenseLayer.getStyles());
                map.putAll(defenseLayer.getElements());
                int factor = getCurrentFactor(map);
                if (!map.isEmpty()) {
                    tooltip.add(ChatFormatting.GRAY +
                            textDefense +
                            "   " +
                            getPercentage(factor, false));
                }
            }
        }
        return tooltip;
    }

    public List<TextureData> getTextureData(Font font, int posX, int posY) {
        int widthAttack = font.width(textAttack) + 2;
        int widthDefense = font.width(textDefense) + 2;
        int maxLineHeight = getLineHeight(font);

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
                            getCurrentName(defenseLayer.getStyles()),
                            posX + widthDefense,
                            posY));
                    posY += maxLineHeight;
                }
                if (hasElementDefense()) {
                    textureData.add(new TextureData(
                            getCurrentName(defenseLayer.getElements()),
                            posX + widthDefense,
                            posY));
                }
            } else {
                HashMap<String, Integer> map = new HashMap<>();
                map.putAll(defenseLayer.getStyles());
                map.putAll(defenseLayer.getElements());
                if (!map.isEmpty()) {
                    textureData.add(new TextureData(
                            getCurrentName(map),
                            posX + widthDefense,
                            posY));
                }
            }
        }
        return textureData;
    }

    private boolean hasAttack() {
        return attackLayer != null && !attackLayer.isDefault();
    }

    private boolean shouldAttackLayerBeDisplayed() {
        return alwaysShowAttack || hasAttack();
    }

    private boolean hasDefense() {
        return defenseLayer != null && !defenseLayer.isEmpty();
    }

    private boolean shouldDefenseLayerBeDisplayed() {
        return alwaysShowDefense || hasDefense();
    }

    private boolean hasElementDefense() {
        return !defenseLayer.isElementEmpty();
    }

    private boolean hasStyleDefense() {
        return !defenseLayer.isStyleEmpty();
    }

    private int getCurrentFactor(Map<String, Integer> map) {
        int index = iteratorCounter % map.size();
        return (new ArrayList<Integer>(map.values())).get(index);
    }

    private String getCurrentName(Map<String, Integer> map) {
        int index = iteratorCounter % map.size();
        return (new ArrayList<String>(map.keySet())).get(index);
    }
}
