package Tavi007.ElementalCombat.interaction;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.util.RenderHelper;
import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;

public class WailaTooltipRenderer implements ITooltipRenderer {

	@Override
	public Dimension getSize(CompoundNBT data, ICommonAccessor accessor) {
		if(ClientConfig.isHWYLAActive()) {
			if(accessor.getEntity() != null && accessor.getEntity() instanceof LivingEntity) {
				LivingEntity living = (LivingEntity) accessor.getEntity();
				int height = RenderHelper.maxLineHeight;
				DefenseData defenseData = DefenseDataAPI.get(living);
				if (!defenseData.isEmpty()) {
					height += RenderHelper.maxLineHeight;
					if(ClientConfig.isDoubleRowDefenseHWYLA() && !defenseData.getElementFactor().isEmpty() && !defenseData.getStyleFactor().isEmpty()) {
						height += RenderHelper.maxLineHeight;
					}
				}
				return new Dimension(RenderHelper.maxLineWidth, height);	
			}
			else if (accessor.getStack() != null) {
				ItemStack stack = accessor.getStack();
				int height = 0;
				if(!AttackDataAPI.get(stack).isDefault()) {
					height += RenderHelper.maxLineHeight;
				}
				DefenseData defenseData = DefenseDataAPI.get(stack);
				if (!defenseData.isEmpty()) {
					height += RenderHelper.maxLineHeight;
					if(ClientConfig.isDoubleRowDefenseHWYLA() && !defenseData.getElementFactor().isEmpty() && !defenseData.getStyleFactor().isEmpty()) {
						height += RenderHelper.maxLineHeight;
					}
					
				}
				return new Dimension(RenderHelper.maxLineWidth, height);	
			}
		}
		return new Dimension();
	}

	@Override
	public void draw(CompoundNBT data, ICommonAccessor accessor, int x, int y) {
		if(ClientConfig.isHWYLAActive()) {

			AttackData attackData;
			DefenseData defenseData;
			// check entity
			Entity entity = accessor.getEntity();
			ItemStack stack = accessor.getStack();
			if (entity != null && entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				attackData = AttackDataAPI.getWithActiveItem(livingEntity);
				defenseData = DefenseDataAPI.get(livingEntity);
			}
			else if(stack != null) {
				attackData = AttackDataAPI.get(stack);
				defenseData = DefenseDataAPI.get(stack);
			}
			else {
				attackData = null;
				defenseData = null;
			}

			//rendering starts here
			MatrixStack matrixStack = new MatrixStack();
			List<ITextComponent> tooltip = new ArrayList<ITextComponent>();
			RenderHelper.addTooltip(tooltip, ClientConfig.isDoubleRowDefenseHWYLA(), attackData, defenseData);
			RenderHelper.renderTooltip(tooltip, matrixStack, x, y);
			
			if(attackData != null) {
				RenderHelper.renderAttackIcons(attackData, matrixStack, x, y);	
				y += RenderHelper.maxLineHeight;

			}
			if(defenseData != null && !defenseData.isEmpty()) {
				RenderHelper.renderDefenseIcons(defenseData, ClientConfig.isDoubleRowDefenseHWYLA(), matrixStack, x, y);
			}

		}
	}

}
