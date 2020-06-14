package Tavi007.ElementalCombat.events;

import java.util.ArrayList;
import java.util.List;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.ElementalAttackDataCapability;
import Tavi007.ElementalCombat.capabilities.ElementalDefenceDataCapability;
import Tavi007.ElementalCombat.capabilities.IElementalAttackData;
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
		LazyOptional<IElementalDefenceData> elem_def_cap = target.getCapability(ElementalDefenceDataCapability.DEF_DATA_CAPABILITY);
		elem_def_cap.ifPresent(def_cap -> {

			List<String> source_elem_atck = new ArrayList<String>();
			List<String> target_elem_abso = def_cap.getAbsorbList();
			List<String> target_elem_wall = def_cap.getWallList();
			List<String> target_elem_resi = def_cap.getResistanceList();
			List<String> target_elem_weak = def_cap.getWeaknessList();
			
			// check if source is an entity
			if(damageSource.getImmediateSource()!=null) 
			{
				//can be either a mob or a projectile (arrow/trident/witherskull)
				// get lists from damageSource.getImmediateSource()?
				Entity source = damageSource.getImmediateSource();
				LazyOptional<IElementalAttackData> elem_atk_cap = source.getCapability(ElementalAttackDataCapability.ATK_DATA_CAPABILITY);
				elem_def_cap.ifPresent(atk_cap -> {
					
				System.out.println("Hello!");	
				});
			}
			else
			{
				// fill List, if Source is LAVA/ON_FIRE/IN_FIRE/etc.
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
			if(source_elem_atck!=null) 
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
			return;
		});
	}
}
