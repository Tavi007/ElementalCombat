package Tavi007.ElementalCombat.client;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

public class CombatDataLayerClientComponent implements ClientTooltipComponent {

    private CombatDataLayerComponent component;

    public CombatDataLayerClientComponent(CombatDataLayerComponent component) {
        this.component = component;
    }

    public static CombatDataLayerClientComponent newComponent(CombatDataLayerComponent component) {
        return new CombatDataLayerClientComponent(component);
    }

    @Override
    public int getHeight() {
        return component.getNumberOfRows() * CombatDataLayerComponent.maxLineHeight;
    }

    @Override
    public int getWidth(Font p_169952_) {
        return CombatDataLayerComponent.maxLineWidth;
    }

    @Override
    public void renderText(Font font, int x, int y, Matrix4f matrix4f, MultiBufferSource.BufferSource bufferSource) {
        List<String> tooltip = component.getTooltip();
        for (int i = 0; i < tooltip.size(); i++) {
            font.drawInBatch(tooltip.get(i), x, y + i * CombatDataLayerComponent.maxLineHeight, 0, false, matrix4f, bufferSource, false, 0, 15728880);
        }
    }

    public void renderText(Font font, PoseStack poseStack, int posX, int posY) {
        List<String> tooltip = component.getTooltip();
        for (int i = 0; i < tooltip.size(); i++) {
            font.drawShadow(poseStack, tooltip.get(i), posX, posY + i * CombatDataLayerComponent.maxLineHeight, 16777215);
        }
    }

    public void renderImage(Font font, int x, int y, PoseStack poseStack,
            ItemRenderer itemRenderer, int p_169963_, TextureManager textureManager) {
        List<TextureData> textureData = component.getTextureData(x, y);
        textureData.forEach(data -> {
            ResourceLocation texture = new ResourceLocation(ElementalCombat.MOD_ID, "textures/icons/" + data.getName() + ".png");
            RenderSystem.setShaderTexture(0, texture);
            Gui.blit(poseStack,
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
