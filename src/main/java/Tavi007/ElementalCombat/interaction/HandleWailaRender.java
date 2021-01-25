package Tavi007.ElementalCombat.interaction;

import java.util.List;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.util.RenderHelper;
import mcp.mobius.waila.api.event.WailaTooltipEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HandleWailaRender {

	@SubscribeEvent
	public static void onWailaRender(WailaTooltipEvent event) {
		List<ITextComponent> toolTip = event.getCurrentTip();
		AttackData atckData = new AttackData();
		DefenseData defData = new DefenseData();
		
		// check entity
		Entity entity = event.getAccessor().getEntity();
		if (entity != null) {
			if(entity instanceof LivingEntity) {
				atckData = ElementalCombatAPI.getAttackDataWithActiveItem((LivingEntity) entity);
				defData = ElementalCombatAPI.getDefenseData((LivingEntity) entity);
				
			}
			else if (entity instanceof ProjectileEntity) {
				atckData = ElementalCombatAPI.getAttackData((ProjectileEntity) entity);
			}
		}
		
		
		// add the text
		toolTip.addAll(RenderHelper.getDisplayText(atckData));
		toolTip.addAll(RenderHelper.getDisplayText(defData));
	}
}
