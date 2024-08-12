package Tavi007.ElementalCombat.common.api;

import Tavi007.ElementalCombat.client.gui.CombatDataLayerClientComponent;
import Tavi007.ElementalCombat.client.gui.CombatDataLayerComponent;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

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
