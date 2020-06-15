package Tavi007.ElementalCombat.events;

import java.util.ArrayList;
import java.util.List;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.ElementalAttackData;
import Tavi007.ElementalCombat.capabilities.ElementalAttackDataCapability;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseData;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseDataCapability;
import Tavi007.ElementalCombat.capabilities.IElementalAttackData;
import Tavi007.ElementalCombat.capabilities.IElementalDefenseData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
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
		
		// Get elemental data from attack
		// check if source is an entity
		List<String> source_elem_atck = new ArrayList<String>();
		if(damageSource.getImmediateSource()!=null) 
		{
			// damage source should be either a mob, player or projectile (arrow/trident/witherskull)
			// get lists from ImmediateSource?
			Entity source = damageSource.getImmediateSource();
			IElementalAttackData elem_atck_cap = source.getCapability(ElementalAttackDataCapability.ATK_DATA_CAPABILITY, null).orElse(new ElementalAttackData());
			source_elem_atck = elem_atck_cap.getAttackList();
		}
		else
		{
			// fill List, if Source is a 'natural occurrence'.
			if(damageSource.isFireDamage())
			{
				source_elem_atck.add("fire");
			}
			else if(damageSource.getDamageType() == "drown")
			{
				source_elem_atck.add("water");
			}
			else if(damageSource.getDamageType() == "lightningBolt")
			{
				source_elem_atck.add("thunder");
			}
			
			//not sure, if i really want these
			else if(damageSource.getDamageType() == "cactus" || 
			   damageSource.getDamageType() == "sweetBerryBush")
			{
				source_elem_atck.add("plant");
			}
			else if(damageSource.getDamageType() == "wither")
			{
				source_elem_atck.add("wither"); //maybe element 'death'/'unholy'?
			}
		}
		
		// if attack doesn't have elemental properties, no need to check defense properties
		if( !source_elem_atck.isEmpty())
		{
			// Get the elemental combat data from target
			IElementalDefenseData elem_def_cap = target.getCapability(ElementalDefenseDataCapability.DEF_DATA_CAPABILITY, null).orElse(new ElementalDefenseData());

			List<String> target_elem_abso = elem_def_cap.getAbsorbList();
			List<String> target_elem_wall = elem_def_cap.getWallList();
			List<String> target_elem_resi = elem_def_cap.getResistanceList();
			List<String> target_elem_weak = elem_def_cap.getWeaknessList();
			
			// for testing. To see, if lists are filled with data
			//System.out.println("weaknessList: " + target_elem_weak);
			//System.out.println("wallList: " + target_elem_wall);
			//System.out.println("absorbList: " + target_elem_abso);
			//System.out.println("resistanceList: " + target_elem_resi);
			//System.out.println("attackList: " + source_elem_atck);
			
			
			// I might rewrite this part to be more time efficient. 
			// TODO: I could change List<String> to the ListNBT. Then I wouldn't have to convert the lists in the capability.
			// Keep in mind, that target_elem_abso and target_elem_wall are usually empty.
			float damageAmount = event.getAmount();
			
			//Check Absorption list first, because the remaining lists don't need to be checked, if 'absorption' happens
			if(target_elem_abso!=null) //null should not happen, but this will make it safe.
			{
				for (String abso : target_elem_abso)
				{
					if(source_elem_atck.contains(abso))
					{
						target.heal(damageAmount);
						event.setAmount(0.0f);
						return;
					}
				}
			}
			
			//Check wall list next, because the remaining lists don't need to be checked, if 'wall' happens
			if(target_elem_wall!=null)
			{
				for (String wall : target_elem_wall)
				{
					if(source_elem_atck.contains(wall))
					{
						event.setAmount(0.0f);
						return;
					}
				}
			}
			
			//Check resistance and weakness list last
			if(target_elem_resi!=null  && target_elem_weak != null) 
			{
				for (String atck : source_elem_atck)
				{
					if(target_elem_resi.contains(atck))
					{
						damageAmount = damageAmount/2;
					}
					else if(target_elem_weak.contains(atck))
					{
						damageAmount = damageAmount*2;
					}
				}
			}
			event.setAmount(damageAmount);
		}
	}
}
