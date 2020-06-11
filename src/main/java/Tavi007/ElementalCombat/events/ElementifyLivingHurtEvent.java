package Tavi007.ElementalCombat.events;

import java.util.ArrayList;
import java.util.List;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.ElementalDefenceDataCapability;
import Tavi007.ElementalCombat.capabilities.IElementalDefenceData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class ElementifyLivingHurtEvent 
{
	@SubscribeEvent
	public static void elementifyLivingHurtEvent(LivingHurtEvent event)
	{
		ElementalCombat.LOGGER.info("Elementify Living Hurt Event fired.");
		DamageSource damageSource = event.getSource();
		LivingEntity target = event.getEntityLiving();
		
		// Get the elemental combat data from target
		LazyOptional<IElementalDefenceData> elem_def_cap = target.getCapability(ElementalDefenceDataCapability.DATA_CAPABILITY);
		elem_def_cap.ifPresent(cap -> {
			
			List<String> target_elem_abso = cap.getAbsorbList();
			List<String> target_elem_wall = cap.getWallList();
			List<String> target_elem_resi = cap.getResistanceList();
			List<String> target_elem_weak = cap.getWeaknessList();
			
			
			// Get the elemental combat data from damageSource
			// Empty for now. TODO: add capability to damageSource, if possible.
			List<String> source_elem_atck = new ArrayList<String>();
			
			
			// I might rewrite this part to be more time efficient. 
			// Keep in mind, that target_elem_abso and target_elem_wall are usually empty.
			float damageAmount = event.getAmount();
			
			//Check Absorption list first, because the other list doesn't need to be checked, if 'absorption' happens
			for (String abso : target_elem_abso)
			{
				if(source_elem_atck.contains(abso))
				{
					target.heal(damageAmount);
					event.setAmount(0.0f);
					return;
				}
			}
			
			//Check wall list next, because the remaining lists doesn't need to be checked, if 'wall' happens
			for (String wall : target_elem_wall)
			{
				if(source_elem_atck.contains(wall))
				{
					event.setAmount(0.0f);
					return;
				}
			}
			
			//Check resistance and weakness list last
			for (String resi : target_elem_resi)
			{
				if(source_elem_atck.contains(resi))
				{
					damageAmount = damageAmount/2;
				}
			}
			for (String weak : target_elem_weak)
			{
				if(source_elem_atck.contains(weak))
				{
					damageAmount = damageAmount*2;
				}
			}
			event.setAmount(damageAmount);
			return;
		
		});
	}
}
