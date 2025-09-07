package Tavi007.ElementalCombat.client.gui;

import Tavi007.ElementalCombat.common.Constants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.List;

public class CombatDataTooltipComponent implements ClientTooltipComponent {

    private static final int iconSize = 8;
    private final CombatDataComponent component;

    public CombatDataTooltipComponent(CombatDataComponent component) {
        this.component = component;
    }

    public static CombatDataTooltipComponent newComponent(CombatDataComponent component) {
        return new CombatDataTooltipComponent(component);
    }

    @Override
    public int getHeight() {
        return component.getNumberOfRows() * component.getLineHeight(Minecraft.getInstance().font);
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return component.getWidth(font);
    }

    public void renderText(Font font, GuiGraphics guiGraphics, int posX, int posY) {
        this.renderText(font, posX, posY, guiGraphics.pose().last().pose(), guiGraphics.bufferSource());
    }

    @Override
    public void renderText(@NotNull Font font, int x, int y, @NotNull Matrix4f matrix4f, MultiBufferSource.@NotNull BufferSource bufferSource) {
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, @NotNull GuiGraphics guiGraphics) {
        List<TextureData> textures = component.getTextureData(font, x, y);
        textures.forEach(texture -> {
            renderIcon(font, texture.posX(), texture.posY(), guiGraphics, texture.name());
        });
    }

    private void renderIcon(@NotNull Font font, int x, int y, @NotNull GuiGraphics guiGraphics, @NotNull String iconName) {
        ResourceLocation texture = new ResourceLocation(Constants.MOD_ID, "textures/icons/" + iconName + ".png");
        RenderSystem.setShaderTexture(0, texture);
        guiGraphics.blit(texture, x, y, 0, 0, iconSize, iconSize, iconSize, iconSize);
    }

    private void renderDefenseDataIcon(@NotNull Font font, int x, int y, @NotNull GuiGraphics guiGraphics, @NotNull String iconName, @NotNull Integer percentage) {
        renderIcon(font, x, y, guiGraphics, iconName);
        String text = String.valueOf(percentage);
        int color;
        if (percentage > 100) {
            color = 0xFF00FF00; // green
        } else if (percentage == 100) {
            color = 0xFFFFFF00; // yellow
        } else if (percentage > 0) {
            color = 0xFF0000FF; // blue
        } else {
            color = 0xFFFF0000; // red
        }
        int textX = x + 19 - 2 - font.width(text); // same padding as vanilla
        int textY = y + 6 + 3;
        guiGraphics.drawString(font, text, textX, textY, color, true);
    }

}
