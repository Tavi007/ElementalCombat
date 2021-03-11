package Tavi007.ElementalCombat.interaction;

import java.awt.Rectangle;
import java.util.List;

import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.util.RenderHelper;
import mcp.mobius.waila.api.event.WailaRenderEvent;
import mcp.mobius.waila.api.event.WailaTooltipEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HandleWailaRender {

	static int ticks=0;
	static int counter=0;
	
	@SubscribeEvent
	public static void onWailaRender(WailaRenderEvent.Pre event) {	
		// check entity
		Entity entity = event.getAccessor().getEntity();
		if (entity != null) {
			AttackData atckData = new AttackData();
			DefenseData defData = new DefenseData();
			if(entity instanceof LivingEntity) {
				atckData = AttackDataAPI.getWithActiveItem((LivingEntity) entity);
				defData = DefenseDataAPI.get((LivingEntity) entity);
				
			}
			else if (entity instanceof ProjectileEntity) {
				atckData = AttackDataAPI.get((ProjectileEntity) entity);
			}

			
			// increase box and render information
			Rectangle box = event.getPosition();
			
//			RenderHelper.render(atckData);
//			if(!defData.isEmpty()) {
//				RenderHelper.render(defData);
//			}
		}
	}
}
