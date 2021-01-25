package Tavi007.ElementalCombat.interaction;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import mcp.mobius.waila.api.event.WailaRenderEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HandleHwylaRender {

	@SubscribeEvent
	public static void onWailaRender(WailaRenderEvent.Pre event) {
		Entity entity = event.getAccessor().getEntity();
		if (entity != null) {
			if(entity instanceof LivingEntity) {
				AttackData atckData = ElementalCombatAPI.getAttackData((LivingEntity) entity);
				DefenseData defData = ElementalCombatAPI.getDefenseData((LivingEntity) entity);
			}
			else if (entity instanceof ProjectileEntity) {
				AttackData atckData = ElementalCombatAPI.getAttackData((ProjectileEntity) entity);
			}
		}
		
		
	}
}
