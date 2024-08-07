package Tavi007.ElementalCombat.api;

import java.awt.Dimension;

import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.client.CombatDataLayerClientComponent;
import Tavi007.ElementalCombat.client.CombatDataLayerComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

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
        CombatDataLayerClientComponent clientComponent = new CombatDataLayerClientComponent(
            new CombatDataLayerComponent(
                attackLayer,
                defenseLayer,
                true,
                true,
                showDoubleRowDefense));

        Minecraft mc = Minecraft.getInstance();
        return new Dimension(
            clientComponent.getWidth(mc.font),
            clientComponent.getHeight());
    }

    public static void renderTextAndIcons(GuiGraphics guiGraphics,
            int x,
            int y,
            boolean showDoubleRowDefense,
            ItemStack stack) {
        AttackLayer attackLayer = AttackDataAPI.getFullDataAsLayer(stack);
        DefenseLayer defenseLayer = DefenseDataAPI.getFullDataAsLayer(stack);
        renderTextAndIcons(guiGraphics, x, y, showDoubleRowDefense, attackLayer, defenseLayer);
    }

    public static void renderTextAndIcons(GuiGraphics guiGraphics,
            int x,
            int y,
            boolean showDoubleRowDefense,
            LivingEntity entity) {
        AttackLayer attackLayer = AttackDataAPI.getFullDataAsLayer(entity);
        DefenseLayer defenseLayer = DefenseDataAPI.getFullDataAsLayer(entity);
        renderTextAndIcons(guiGraphics, x, y, showDoubleRowDefense, attackLayer, defenseLayer);
    }

    public static void renderTextAndIcons(GuiGraphics guiGraphics,
            int x,
            int y,
            boolean showDoubleRowDefense,
            AttackLayer attackLayer,
            DefenseLayer defenseLayer) {
        CombatDataLayerClientComponent clientComponent = new CombatDataLayerClientComponent(
            new CombatDataLayerComponent(
                attackLayer,
                defenseLayer,
                true,
                true,
                showDoubleRowDefense));

        // rendering starts here
        Minecraft mc = Minecraft.getInstance();
        clientComponent.renderText(mc.font,
            guiGraphics,
            x,
            y); // nullpointer here
        clientComponent.renderImage(mc.font, x, y, guiGraphics);
    }
}
