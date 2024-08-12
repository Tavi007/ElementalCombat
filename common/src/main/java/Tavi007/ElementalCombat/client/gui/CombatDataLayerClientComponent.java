package Tavi007.ElementalCombat.client.gui;

import Tavi007.ElementalCombat.common.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.util.List;

public class CombatDataLayerClientComponent implements ClientTooltipComponent {

    private final CombatDataLayerComponent component;

    public CombatDataLayerClientComponent(CombatDataLayerComponent component) {
        this.component = component;
    }

    public static CombatDataLayerClientComponent newComponent(CombatDataLayerComponent component) {
        return new CombatDataLayerClientComponent(component);
    }

    @Override
    public int getHeight() {
        return component.getNumberOfRows() * component.getLineHeight(Minecraft.getInstance().font);
    }

    @Override
    public int getWidth(Font font) {
        return component.getWidth(font);
    }

    public void renderText(Font font, GuiGraphics guiGraphics, int posX, int posY) {
        this.renderText(font, posX, posY, guiGraphics.pose().last().pose(), guiGraphics.bufferSource());
    }

    @Override
    public void renderText(Font font, int x, int y, Matrix4f matrix4f, MultiBufferSource.BufferSource bufferSource) {
        List<String> tooltip = component.getTooltip();
        for (int i = 0; i < tooltip.size(); i++) {
            font.drawInBatch(tooltip.get(i),
                    (float) x,
                    (float) y + i * component.getLineHeight(font),
                    0,
                    false,
                    matrix4f,
                    bufferSource,
                    Font.DisplayMode.NORMAL,
                    0,
                    15728880,
                    false);
        }
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        List<TextureData> textureData = component.getTextureData(font, x, y);
        textureData.forEach(data -> {
            ResourceLocation texture = new ResourceLocation(Constants.MOD_ID, "textures/icons/" + data.getName() + ".png");
            guiGraphics.blit(texture,
                    data.getPosX(),
                    data.getPosY(),
                    0,
                    0,
                    CombatDataLayerComponent.iconSize,
                    CombatDataLayerComponent.iconSize,
                    CombatDataLayerComponent.iconSize,
                    CombatDataLayerComponent.iconSize);
        });
    }

}
