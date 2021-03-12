package Tavi007.ElementalCombat.interaction;

import java.awt.Rectangle;

import com.mojang.blaze3d.matrix.MatrixStack;

import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.util.RenderHelper;
import mcp.mobius.waila.api.event.WailaRenderEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HandleWailaRender {

	static AttackData attackData;
	static DefenseData defenseData;

	static float attackPosY;
	static float defensePosY;
	
	static final int extraWidth = 6;

	@SubscribeEvent
	public static void onWailaRenderPre(WailaRenderEvent.Pre event) {	
		// check entity
		Entity entity = event.getAccessor().getEntity();
		if (entity != null) {
			if(entity instanceof LivingEntity) {
				attackData = AttackDataAPI.getWithActiveItem((LivingEntity) entity);
				defenseData = DefenseDataAPI.get((LivingEntity) entity);
			}
			else if (entity instanceof ProjectileEntity) {
				attackData = AttackDataAPI.get((ProjectileEntity) entity);
			}

			// increase box and render information
			Rectangle box = event.getPosition();
			attackPosY = box.height;
			box.height += RenderHelper.maxLineHeight;
			if(!defenseData.isEmpty()) {
				defensePosY = box.height;
				box.height += RenderHelper.maxLineHeight;
				box.width = Math.max(box.width, RenderHelper.maxLineWidth + extraWidth*2);
			}
		}
	}

	@SubscribeEvent
	public static void onWailaRenderPost(WailaRenderEvent.Post event) {	
		Rectangle box = event.getPosition();
		MatrixStack matrixStack = new MatrixStack();

		if(attackData != null) {
			RenderHelper.render(attackData, matrixStack, box.x + extraWidth, attackPosY);
		}
		if(defenseData != null && !defenseData.isEmpty()) {
			RenderHelper.render(defenseData, matrixStack, box.x + extraWidth, defensePosY);
		}

		attackData = null;
		defenseData = null;
	}

}
