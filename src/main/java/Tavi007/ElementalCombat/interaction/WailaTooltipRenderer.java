package Tavi007.ElementalCombat.interaction;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.util.AttackDataHelper;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import Tavi007.ElementalCombat.util.RenderHelper;
import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class WailaTooltipRenderer implements ITooltipRenderer {

    @Override
    public Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
        if (ClientConfig.isHWYLAActive()) {
            if (accessor.getEntity() != null && accessor.getEntity() instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) accessor.getEntity();
                int height = RenderHelper.maxLineHeight;
                DefenseData defenseData = DefenseDataHelper.get(living);
                if (!defenseData.isEmpty()) {
                    height += RenderHelper.maxLineHeight;
                    if (ClientConfig.isDoubleRowDefenseHWYLA() && !defenseData.getElementFactor().isEmpty() && !defenseData.getStyleFactor().isEmpty()) {
                        height += RenderHelper.maxLineHeight;
                    }
                }
                return new Dimension(RenderHelper.maxLineWidth, height);
            } else if (accessor.getStack() != null) {
                ItemStack stack = accessor.getStack();
                int height = 0;
                if (!AttackDataHelper.get(stack).isDefault()) {
                    height += RenderHelper.maxLineHeight;
                }
                DefenseData defenseData = DefenseDataHelper.get(stack);
                if (!defenseData.isEmpty()) {
                    height += RenderHelper.maxLineHeight;
                    if (ClientConfig.isDoubleRowDefenseHWYLA() && !defenseData.getElementFactor().isEmpty() && !defenseData.getStyleFactor().isEmpty()) {
                        height += RenderHelper.maxLineHeight;
                    }

                }
                return new Dimension(RenderHelper.maxLineWidth, height);
            }
        }
        return new Dimension();
    }

    @Override
    public void draw(PoseStack matrices, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        if (ClientConfig.isHWYLAActive()) {

            AttackData attackData;
            DefenseData defenseData;
            // check entity
            Entity entity = accessor.getEntity();
            ItemStack stack = accessor.getStack();
            if (entity != null && entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                attackData = AttackDataHelper.get(livingEntity);
                defenseData = DefenseDataHelper.get(livingEntity);
            } else if (stack != null) {
                attackData = AttackDataHelper.get(stack);
                defenseData = DefenseDataHelper.get(stack);
            } else {
                attackData = null;
                defenseData = null;
            }

            // rendering starts here
            PoseStack matrixStack = new PoseStack();
            List<Component> tooltip = new ArrayList<Component>();
            RenderHelper.addTooltip(tooltip, ClientConfig.isDoubleRowDefenseHWYLA(), attackData, defenseData);
            RenderHelper.renderTooltip(tooltip, matrixStack, x, y);

            if (attackData != null) {
                RenderHelper.renderAttackIcons(attackData, matrixStack, x, y);
                y += RenderHelper.maxLineHeight;

            }
            if (defenseData != null && !defenseData.isEmpty()) {
                RenderHelper.renderDefenseIcons(defenseData, ClientConfig.isDoubleRowDefenseHWYLA(), matrixStack, x, y);
            }

        }
    }

}
