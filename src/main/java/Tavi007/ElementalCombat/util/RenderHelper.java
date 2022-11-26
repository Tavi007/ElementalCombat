package Tavi007.ElementalCombat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class RenderHelper {

    private static final Font fontRenderer = Minecraft.getInstance().font;

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

    public static int getTooltipIndexAttack(List<? extends ClientTooltipComponent> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().contains(textAttack)) {
                return i;
            }
        }
        return -1;
    }

    public static int getTooltipIndexDefense(List<? extends ClientTooltipComponent> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().contains(textDefense)) {
                return i;
            }
        }
        return -1;
    }

    private static String getCurrentDefenseName(DefenseData data) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.putAll(data.getElementFactor());
        map.putAll(data.getStyleFactor());
        return (new ArrayList<String>(map.keySet())).get(iteratorCounter % map.size());
    }

    private static int getCurrentDefenseFactor(DefenseData data) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.putAll(data.getElementFactor());
        map.putAll(data.getStyleFactor());
        return (new ArrayList<Integer>(map.values())).get(iteratorCounter % map.size());
    }

    private static boolean isCurrentDefenseFactorStyle(DefenseData data) {
        return iteratorCounter >= data.getElementFactor().size();
    }

    private static String getCurrentElementDefenseName(DefenseData data) {
        DefenseData onlyElement = new DefenseData();
        onlyElement.putLayer(new ResourceLocation(ElementalCombat.MOD_ID, "render"), new DefenseLayer(new HashMap<>(), data.getElementFactor()));
        return getCurrentDefenseName(onlyElement);
    }

    private static int getCurrentElementDefenseFactor(DefenseData data) {
        DefenseData onlyElement = new DefenseData();
        onlyElement.putLayer(new ResourceLocation(ElementalCombat.MOD_ID, "render"), new DefenseLayer(new HashMap<>(), data.getElementFactor()));
        return getCurrentDefenseFactor(onlyElement);
    }

    private static String getCurrentStyleDefenseName(DefenseData data) {
        DefenseData onlyStyle = new DefenseData();
        onlyStyle.putLayer(new ResourceLocation(ElementalCombat.MOD_ID, "render"), new DefenseLayer(data.getStyleFactor(), new HashMap<>()));
        return getCurrentDefenseName(onlyStyle);
    }

    private static int getCurrentStyleDefenseFactor(DefenseData data) {
        DefenseData onlyStyle = new DefenseData();
        onlyStyle.putLayer(new ResourceLocation(ElementalCombat.MOD_ID, "render"), new DefenseLayer(data.getStyleFactor(), new HashMap<>()));
        return getCurrentDefenseFactor(onlyStyle);
    }

    // Tooltip stuff
    public static void renderTooltip(List<Component> tooltip, PoseStack poseStack, int x, int y) {
        for (int i = 0; i < tooltip.size(); i++) {
            fontRenderer.drawShadow(poseStack, tooltip.get(i).getString(), x, y + i * RenderHelper.maxLineHeight, ChatFormatting.GRAY.getColor());
        }
    }

    public static void addTooltipSeperator(List<Component> tooltip, boolean hasDefenseData) {
        if (hasDefenseData) {
            tooltip.add(new TextComponent(ChatFormatting.GRAY + textSeperator + ChatFormatting.RESET));
        } else {
            tooltip.add(new TextComponent(ChatFormatting.GRAY + textSeperatorOnlyAttack + ChatFormatting.RESET));
        }
    }

    public static void addTooltip(List<Component> tooltip, boolean inTwoRows, @Nullable AttackData attackData, @Nullable DefenseData defenseData) {
        if (attackData != null) {
            tooltip.add(new TextComponent(ChatFormatting.GRAY + textAttack));
        }
        if (defenseData != null && !defenseData.isEmpty()) {
            if (inTwoRows) {
                if (!defenseData.getElementFactor().isEmpty()) {
                    int factor = getCurrentElementDefenseFactor(defenseData);
                    tooltip.add(new TextComponent(ChatFormatting.GRAY + textDefense + "   " + getPercentage(factor, false)));
                }
                if (!defenseData.getStyleFactor().isEmpty()) {
                    int factor = getCurrentStyleDefenseFactor(defenseData);
                    tooltip.add(new TextComponent(ChatFormatting.GRAY + textDefense + "   " + getPercentage(factor, true)));
                }
            } else {
                int factor = getCurrentDefenseFactor(defenseData);
                boolean isStyle = isCurrentDefenseFactorStyle(defenseData);
                tooltip.add(new TextComponent(ChatFormatting.GRAY + textDefense + "   " + getPercentage(factor, isStyle)));
            }
        }
    }

    public static String getPercentage(Integer factor, boolean isStyle) {
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

    // icon render stuff
    // posX and posY are the start coords of the "Attack: "- String. Calculation will be relative from there on out.
    public static void renderAttackIcons(AttackData data, PoseStack poseStack, int posX, int posY) {
        renderIcon(data.getElement(), poseStack, posX + widthAttack, posY);
        renderIcon(data.getStyle(), poseStack, posX + widthAttack + iconSize + 2, posY);
    }

    // posX and posY are the start coords of the "Defense: "- String. Calculation will be relative from there on out.
    public static void renderDefenseIcons(DefenseData data, boolean inTwoRows, PoseStack poseStack, int posX, int posY) {
        if (inTwoRows) {
            if (!data.getElementFactor().isEmpty()) {
                renderIcon(getCurrentElementDefenseName(data), poseStack, posX + widthDefense, posY);
                posY += maxLineHeight;
            }
            if (!data.getStyleFactor().isEmpty()) {
                renderIcon(getCurrentStyleDefenseName(data), poseStack, posX + widthDefense, posY);
            }
        } else {
            renderIcon(getCurrentDefenseName(data), poseStack, posX + widthDefense, posY);
        }
    }

    public static void renderIcon(String name, PoseStack poseStack, int posX, int posY) {
        ResourceLocation texture = new ResourceLocation(ElementalCombat.MOD_ID, "textures/icons/" + name + ".png");
        RenderSystem.setShaderTexture(0, texture);
        Gui.blit(poseStack, posX, posY, 0, 0, iconSize, iconSize, iconSize, iconSize);
    }
}
