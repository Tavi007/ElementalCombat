package Tavi007.ElementalCombat.client.gui;

import Tavi007.ElementalCombat.client.data.TextureData;
import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.util.DamageCalculationHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Tavi007.ElementalCombat.common.Constants.iconSize;

public class GuiHelper {
    public static final int TEXTURE_DELTA = iconSize + 2;
    public static final int MAX_ICONS_PRE_ROW = 3;

    public static void renderTextureData(@NotNull Font font, @NotNull GuiGraphics guiGraphics, TextureData textureData) {
        // render icon image
        ResourceLocation texture = new ResourceLocation(Constants.MOD_ID, "textures/icons/" + textureData.name() + ".png");
        RenderSystem.setShaderTexture(0, texture);
        int x = textureData.posX();
        int y = textureData.posY();
        guiGraphics.blit(texture, x, y, 0, 0, iconSize, iconSize, iconSize, iconSize);

        // render percentage value, if it exists
        String text = textureData.text();
        if (text != null) {
            float scale = 0.5f; // 75% of normal size
            int padding = 1; // pixels from the edge
            float scaledWidth = font.width(text) * scale;
            float scaledHeight = font.lineHeight * scale;

            float textX = x + iconSize - scaledWidth;
            float textY = y + iconSize - scaledHeight;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(scale, scale, 1.0f);
            guiGraphics.drawString(
                    font,
                    text,
                    (int) (textX / scale),
                    (int) (textY / scale),
                    0xFFFFFF,
                    true
            );
            guiGraphics.pose().popPose();
        }
    }

    public static List<TextureData> getAttackTextures(String style, String element, int posX, int posY) {
        List<TextureData> textureData = new ArrayList<>();
        textureData.add(new TextureData(
                style,
                posX,
                posY,
                null));
        textureData.add(new TextureData(
                element,
                posX + TEXTURE_DELTA,
                posY,
                null));
        return textureData;
    }

    // map could either be style or element map.
    // returns the texture data list and the number of rows it fills
    public static Tuple<List<TextureData>, Integer> getDefenseTextures(Map<String, Integer> style,
                                                                       Map<String, Integer> element,
                                                                       int posX,
                                                                       int posY) {
        List<TextureData> textureData = getDefenseTextures(style, true, posX, posY);

        int i = getNumberOfRows(style);
        posY = posY + TEXTURE_DELTA * i;
        textureData.addAll(getDefenseTextures(element, false, posX, posY));
        return new Tuple<>(textureData, i + getNumberOfRows(element));
    }

    public static int getNumberOfRows(Map<String, Integer> map) {
        // TODO: use Math.ceilDiv(map.size(), MAX_ICONS_PRE_ROW) once i use Java 18
        return (int) Math.ceil((double) map.size() / MAX_ICONS_PRE_ROW);
    }

    private static List<TextureData> getDefenseTextures(Map<String, Integer> map,
                                                        boolean isStyle,
                                                        int posX,
                                                        int posY) {
        List<TextureData> textureData = new ArrayList<>();

        List<String> keys = (new ArrayList<>(map.keySet()));
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String text = getText(map.get(key), isStyle);
            textureData.add(new TextureData(
                    key,
                    posX + (i % MAX_ICONS_PRE_ROW) * TEXTURE_DELTA,
                    posY,
                    text));
            if ((i + 1) % MAX_ICONS_PRE_ROW == 0) {
                posY = posY + TEXTURE_DELTA;
            }
        }
        return textureData;
    }

    private static String getText(Integer factor, boolean isStyle) {
        int percentage = Math.round(DamageCalculationHelper.getPercentage(factor, isStyle) * 100);
        String text = "";
        if (percentage < 0) {
            text = ChatFormatting.RED + String.valueOf(-percentage);
        } else if (percentage < 100) {
            text = ChatFormatting.BLUE + String.valueOf(percentage);
        } else if (percentage == 100) {
            text = ChatFormatting.YELLOW + "B";
        } else {
            text = ChatFormatting.GREEN + String.valueOf(percentage - 100);
        }
        return text + ChatFormatting.RESET;
    }
}
