package Tavi007.ElementalCombat.client;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.util.RenderHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;

public class ElementalCombatClientComponent implements ClientTooltipComponent {

    private ElementalCombatComponent component;

    public ElementalCombatClientComponent(ElementalCombatComponent component) {
        this.component = component;
    }

    public static ElementalCombatClientComponent newComponent(ElementalCombatComponent component) {
        return new ElementalCombatClientComponent(component);
    }

    @Override
    public int getHeight() {
        return component.getNumberOfLines() * RenderHelper.maxLineHeight;
    }

    @Override
    public int getWidth(Font p_169952_) {
        return RenderHelper.maxLineWidth;
    }

    @Override
    public void renderText(Font font, int x, int y, Matrix4f matrix4f, MultiBufferSource.BufferSource bufferSource) {

        List<String> tooltips = new ArrayList<>();
        if (component.hasAttackData()) {
            tooltips.add(ChatFormatting.GRAY + RenderHelper.textAttack);
        }

        if (component.hasDefenseData()) {
            DefenseData defenseData = component.getDefenseData();
            if (true) {
                if (!defenseData.getElementFactor().isEmpty()) {
                    int factor = RenderHelper.getCurrentElementDefenseFactor(defenseData);
                    tooltips.add(ChatFormatting.GRAY + RenderHelper.textDefense + "   " + RenderHelper.getPercentage(factor, false));
                }
                if (!defenseData.getStyleFactor().isEmpty()) {
                    int factor = RenderHelper.getCurrentStyleDefenseFactor(defenseData);
                    tooltips.add(ChatFormatting.GRAY + RenderHelper.textDefense + "   " + RenderHelper.getPercentage(factor, true));
                }
            }
        }

        for (int i = 0; i < tooltips.size(); i++) {
            font.drawInBatch(tooltips.get(i), x, y + i * RenderHelper.maxLineHeight, 0, false, matrix4f, bufferSource, false, 0, 15728880);
        }

    }

    @Override
    public void renderImage(Font font, int x, int y, PoseStack poseStack,
            ItemRenderer itemRenderer, int p_169963_, TextureManager textureManager) {
    }

}
