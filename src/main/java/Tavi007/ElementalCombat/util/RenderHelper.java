package Tavi007.ElementalCombat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class RenderHelper {

    private static final FontRenderer fontRenderer = Minecraft.getInstance().font;
    private static final TextureManager textureManager = Minecraft.getInstance().textureManager;

    private static final String textAttack = "Attack:";
    private static final String textSeperatorOnlyAttack = "---------";
    private static final String textDefense = "Defense:";
    private static final String textSeperator = "---------------";
    private static final int iconSize = 8;

    private static final int widthAttack = fontRenderer.width(textAttack) + 2;
    private static final int widthDefense = fontRenderer.width(textDefense) + 2;
    public static final int maxLineHeight = fontRenderer.lineHeight + 1;
    public static final int maxLineWidth = fontRenderer.width(textDefense + " -999%") + iconSize + 2;

    private static int iteratorCounter = 0;

    public static void tickIteratorCounter() {
        iteratorCounter++;
        if (iteratorCounter == Integer.MAX_VALUE) {
            iteratorCounter = 0;
        }
    }

    public static int getTooltipIndexAttack(List<? extends ITextProperties> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getString().contains(textAttack)) {
                return i;
            }
        }
        return -1;
    }

    public static int getTooltipIndexDefense(List<? extends ITextProperties> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getString().contains(textDefense)) {
                return i;
            }
        }
        return -1;
    }

    private static String getCurrentDefenseName(DefenseLayer defenseLayer) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.putAll(defenseLayer.getElementFactor());
        map.putAll(defenseLayer.getStyleFactor());
        return (new ArrayList<String>(map.keySet())).get(iteratorCounter % map.size());
    }

    private static int getCurrentDefenseFactor(DefenseLayer defenseLayer) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.putAll(defenseLayer.getElementFactor());
        map.putAll(defenseLayer.getStyleFactor());
        return (new ArrayList<Integer>(map.values())).get(iteratorCounter % map.size());
    }

    private static boolean isCurrentDefenseFactorStyle(DefenseLayer defenseLayer) {
        return iteratorCounter >= defenseLayer.getElementFactor().size();
    }

    private static String getCurrentElementDefenseName(DefenseLayer defenseLayer) {
        return getCurrentDefenseName(new DefenseLayer(new HashMap<>(), defenseLayer.getElementFactor()));
    }

    private static int getCurrentElementDefenseFactor(DefenseLayer defenseLayer) {
        return getCurrentDefenseFactor(new DefenseLayer(new HashMap<>(), defenseLayer.getElementFactor()));
    }

    private static String getCurrentStyleDefenseName(DefenseLayer defenseLayer) {
        return getCurrentDefenseName(new DefenseLayer(defenseLayer.getStyleFactor(), new HashMap<>()));
    }

    private static int getCurrentStyleDefenseFactor(DefenseLayer defenseLayer) {
        return getCurrentDefenseFactor(new DefenseLayer(defenseLayer.getStyleFactor(), new HashMap<>()));
    }

    // Tooltip stuff
    public static void renderTooltip(List<ITextComponent> tooltip, MatrixStack matrixStack, int x, int y) {
        for (int i = 0; i < tooltip.size(); i++) {
            fontRenderer.drawShadow(matrixStack, tooltip.get(i).getString(), x, y + i * RenderHelper.maxLineHeight, TextFormatting.GRAY.getColor());
        }
    }

    public static void addTooltipSeperator(List<ITextComponent> tooltip, boolean hasDefenseData) {
        if (hasDefenseData) {
            tooltip.add(new StringTextComponent(TextFormatting.GRAY + textSeperator + TextFormatting.RESET));
        } else {
            tooltip.add(new StringTextComponent(TextFormatting.GRAY + textSeperatorOnlyAttack + TextFormatting.RESET));
        }
    }

    public static void addTooltip(List<ITextComponent> tooltip, boolean inTwoRows, @Nullable AttackLayer attackLayer, @Nullable DefenseLayer defenseLayer) {
        if (attackLayer != null) {
            tooltip.add(new StringTextComponent(TextFormatting.GRAY + textAttack));
        }
        if (defenseLayer != null && !defenseLayer.isEmpty()) {
            if (inTwoRows) {
                if (!defenseLayer.getElementFactor().isEmpty()) {
                    int factor = getCurrentElementDefenseFactor(defenseLayer);
                    tooltip.add(new StringTextComponent(TextFormatting.GRAY + textDefense + "   " + getPercentage(factor, false)));
                }
                if (!defenseLayer.getStyleFactor().isEmpty()) {
                    int factor = getCurrentStyleDefenseFactor(defenseLayer);
                    tooltip.add(new StringTextComponent(TextFormatting.GRAY + textDefense + "   " + getPercentage(factor, true)));
                }
            } else {
                int factor = getCurrentDefenseFactor(defenseLayer);
                boolean isStyle = isCurrentDefenseFactorStyle(defenseLayer);
                tooltip.add(new StringTextComponent(TextFormatting.GRAY + textDefense + "   " + getPercentage(factor, isStyle)));
            }
        }
    }

    public static String getPercentage(Integer factor, boolean isStyle) {
        Integer percentage = Math.round(DefenseDataHelper.getPercentage(factor, isStyle) * 100);
        TextFormatting textFormatting = TextFormatting.GRAY;
        if (percentage < 0) {
            textFormatting = TextFormatting.RED;
        }
        if (percentage > 0 && percentage < 100) {
            textFormatting = TextFormatting.BLUE;
        }
        if (percentage == 100) {
            textFormatting = TextFormatting.YELLOW;
        }
        if (percentage > 100) {
            textFormatting = TextFormatting.GREEN;
        }

        if (percentage < 0) {
            return textFormatting + String.valueOf(percentage) + "%" + TextFormatting.RESET;
        } else {
            return textFormatting + "+" + String.valueOf(percentage) + "%" + TextFormatting.RESET;
        }
    }

    // icon render stuff
    // posX and posY are the start coords of the "Attack: "- String. Calculation will be relative from there on out.
    public static void renderAttackIcons(AttackLayer layer, MatrixStack matrixStack, int posX, int posY) {
        renderIcon(layer.getElement(), matrixStack, posX + widthAttack, posY);
        renderIcon(layer.getStyle(), matrixStack, posX + widthAttack + iconSize + 2, posY);
    }

    // posX and posY are the start coords of the "Defense: "- String. Calculation will be relative from there on out.
    public static void renderDefenseIcons(DefenseLayer layer, boolean inTwoRows, MatrixStack matrixStack, int posX, int posY) {
        if (inTwoRows) {
            if (!layer.getElementFactor().isEmpty()) {
                renderIcon(getCurrentElementDefenseName(layer), matrixStack, posX + widthDefense, posY);
                posY += maxLineHeight;
            }
            if (!layer.getStyleFactor().isEmpty()) {
                renderIcon(getCurrentStyleDefenseName(layer), matrixStack, posX + widthDefense, posY);
            }
        } else {
            renderIcon(getCurrentDefenseName(layer), matrixStack, posX + widthDefense, posY);
        }
    }

    public static void renderIcon(String name, MatrixStack matrixStack, int posX, int posY) {
        textureManager.bind(new ResourceLocation(ElementalCombat.MOD_ID, "textures/icons/" + name + ".png"));
        AbstractGui.blit(matrixStack, posX, posY, 0, 0, iconSize, iconSize, iconSize, iconSize);
        textureManager.bind(AbstractGui.GUI_ICONS_LOCATION);
    }
}
