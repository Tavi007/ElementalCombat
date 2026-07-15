package Tavi007.ElementalCombat.client.gui;

import Tavi007.ElementalCombat.client.data.TextData;
import Tavi007.ElementalCombat.client.data.TextureData;
import Tavi007.ElementalCombat.client.data.TooltipData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.List;

import static Tavi007.ElementalCombat.client.gui.GuiHelper.renderTextureData;

public class ElementalCombatTooltipComponent implements ClientTooltipComponent {

    private static final int iconSize = 8;
    private final TooltipData component;

    public ElementalCombatTooltipComponent(TooltipData component) {
        this.component = component;
    }

    public static ElementalCombatTooltipComponent newComponent(TooltipData component) {
        return new ElementalCombatTooltipComponent(component);
    }

    @Override
    public int getHeight() {
        return component.getHeight();
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return component.getWidth(font);
    }

    @Override
    public void renderText(@NotNull Font font, int x, int y, @NotNull Matrix4f matrix4f, MultiBufferSource.@NotNull BufferSource bufferSource) {
        List<TextData> textData = component.getTextData(x, y);
        textData.forEach(data -> {
            font.drawInBatch(
                    ChatFormatting.GRAY + data.textId() + ChatFormatting.RESET, // TODO: resolve Id
                    (float) data.posX(),
                    (float) data.posY(),
                    0,
                    false,
                    matrix4f,
                    bufferSource,
                    Font.DisplayMode.NORMAL,
                    0,
                    15728880,
                    false);
        });
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, @NotNull GuiGraphics guiGraphics) {
        List<TextureData> textures = component.getTextureData(font, x, y);
        textures.forEach(texture -> {
            renderTextureData(font, guiGraphics, texture);
        });
    }
}
