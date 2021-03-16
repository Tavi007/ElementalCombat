package Tavi007.ElementalCombat.interaction;

import java.awt.Dimension;

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

public class WailaTooltipRenderer implements ITooltipRenderer {

	@Override
	public Dimension getSize(CompoundNBT data, ICommonAccessor accessor) {
		if(ClientConfig.isHWYLAActive()) {
			return new Dimension(RenderHelper.maxLineWidth, RenderHelper.maxLineHeight*2);
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
//				RenderHelper.addTooltip(event.getCurrentTip(), attackData, defenseData);
			}
			else if(stack != null) {
				attackData = AttackDataAPI.get(stack);
				defenseData = DefenseDataAPI.get(stack);
//				RenderHelper.addTooltip(event.getCurrentTip(), attackData, defenseData);
			}
			else {
				attackData = null;
				defenseData = null;
			}

			MatrixStack matrixStack = new MatrixStack();
			if(attackData != null) {
				RenderHelper.renderAttackIcons(attackData, matrixStack, x, y);
			}
			if(defenseData != null && !defenseData.isEmpty()) {
				RenderHelper.renderDefenseIcons(defenseData, matrixStack, x, y + RenderHelper.maxLineHeight);
			}
			
		}
	}

}
