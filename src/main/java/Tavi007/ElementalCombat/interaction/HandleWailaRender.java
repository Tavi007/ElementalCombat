package Tavi007.ElementalCombat.interaction;

import java.awt.Rectangle;

import com.mojang.blaze3d.matrix.MatrixStack;

import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.util.RenderHelper;
import mcp.mobius.waila.api.event.WailaRenderEvent;
import mcp.mobius.waila.api.event.WailaTooltipEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HandleWailaRender {

	static AttackData attackData;
	static DefenseData defenseData;

	static int attackTooltipId;
	static int defenseTooltipId;

	@SubscribeEvent
	public static void onWailaTooltip(WailaTooltipEvent event) {	
		// check entity
		Entity entity = event.getAccessor().getEntity();
		if (entity != null) {
			if(entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				attackTooltipId = event.getCurrentTip().size();
				attackData = AttackDataAPI.getWithActiveItem(livingEntity);
				defenseData = DefenseDataAPI.get(livingEntity);
				defenseTooltipId = attackTooltipId + 1;
				RenderHelper.addTooltip(event.getCurrentTip(), attackData, defenseData);
			}
		}
		else {
			attackData = null;
			defenseData = null;
		}
	}

	@SubscribeEvent
	public static void onWailaRenderPost(WailaRenderEvent.Post event) {	
		MatrixStack matrixStack = new MatrixStack();
		Rectangle box = event.getPosition();
		if(attackData != null) {
			RenderHelper.renderAttackIcons(attackData, matrixStack, box.x + 6, box.y + 6 + attackTooltipId*RenderHelper.maxLineHeight);
		}
		if(defenseData != null && !defenseData.isEmpty()) {
			RenderHelper.renderDefenseIcons(defenseData, matrixStack, box.x + 6, box.y + 6 + defenseTooltipId*RenderHelper.maxLineHeight);
		}
	}

}
