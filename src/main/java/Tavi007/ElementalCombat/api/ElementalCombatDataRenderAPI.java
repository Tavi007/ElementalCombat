package Tavi007.ElementalCombat.api;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.util.RenderHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class ElementalCombatDataRenderAPI {

    public static Dimension getTooltipDimension(boolean showDoubleRowDefense, LivingEntity entity) {
        AttackLayer attackLayer = AttackDataAPI.getFullDataAsLayer(entity);
        DefenseLayer defenseLayer = DefenseDataAPI.getFullDataAsLayer(entity);
        return getTooltipDimension(showDoubleRowDefense, attackLayer, defenseLayer);
    }

    public static Dimension getTooltipDimension(boolean showDoubleRowDefense, ItemStack stack) {
        AttackLayer attackLayer = AttackDataAPI.getFullDataAsLayer(stack);
        if (!attackLayer.isDefault()) {
            attackLayer = null;
        }
        DefenseLayer defenseLayer = DefenseDataAPI.getFullDataAsLayer(stack);
        return getTooltipDimension(showDoubleRowDefense, attackLayer, defenseLayer);
    }

    public static Dimension getTooltipDimension(boolean showDoubleRowDefense, AttackLayer attackLayer, DefenseLayer defenseLayer) {
        int height = 0;
        if (attackLayer != null) {
            height += RenderHelper.maxLineHeight;
        }
        if (defenseLayer != null && !defenseLayer.isEmpty()) {
            height += RenderHelper.maxLineHeight;
            if (showDoubleRowDefense && !defenseLayer.getElementFactor().isEmpty() && !defenseLayer.getStyleFactor().isEmpty()) {
                height += RenderHelper.maxLineHeight;
            }
        }
        return new Dimension(RenderHelper.maxLineWidth, height);
    }

    public static void renderTextAndIcons(MatrixStack matrixStack,
            int x,
            int y,
            boolean showDoubleRowDefense,
            ItemStack stack) {
        AttackLayer attackLayer = AttackDataAPI.getFullDataAsLayer(stack);
        DefenseLayer defenseLayer = DefenseDataAPI.getFullDataAsLayer(stack);
        renderTextAndIcons(matrixStack, x, y, showDoubleRowDefense, attackLayer, defenseLayer);
    }

    public static void renderTextAndIcons(MatrixStack matrixStack,
            int x,
            int y,
            boolean showDoubleRowDefense,
            LivingEntity entity) {
        AttackLayer attackLayer = AttackDataAPI.getFullDataAsLayer(entity);
        DefenseLayer defenseLayer = DefenseDataAPI.getFullDataAsLayer(entity);
        renderTextAndIcons(matrixStack, x, y, showDoubleRowDefense, attackLayer, defenseLayer);
    }

    public static void renderTextAndIcons(MatrixStack matrixStack,
            int x,
            int y,
            boolean showDoubleRowDefense,
            AttackLayer attackLayer,
            DefenseLayer defenseLayer) {
        List<ITextComponent> tooltip = new ArrayList<ITextComponent>();
        RenderHelper.addTooltip(tooltip, showDoubleRowDefense, attackLayer, defenseLayer);
        RenderHelper.renderTooltip(tooltip, matrixStack, x, y);

        if (attackLayer != null) {
            RenderHelper.renderAttackIcons(attackLayer, matrixStack, x, y);
            y += RenderHelper.maxLineHeight;

        }
        if (defenseLayer != null && !defenseLayer.isEmpty()) {
            RenderHelper.renderDefenseIcons(defenseLayer, showDoubleRowDefense, matrixStack, x, y);
        }
    }
}
