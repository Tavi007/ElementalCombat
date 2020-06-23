package Tavi007.ElementalCombat.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalData;
import Tavi007.ElementalCombat.capabilities.ElementalAttackData;
import Tavi007.ElementalCombat.capabilities.ElementalAttackDataCapability;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseData;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseDataCapability;
import Tavi007.ElementalCombat.capabilities.IElementalAttackData;
import Tavi007.ElementalCombat.capabilities.IElementalDefenseData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
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
		DamageSource damageSource = event.getSource();
		LivingEntity target = event.getEntityLiving();
		
		
		// Get elemental data from attack
		// check if source is an entity
		Map<String,Integer> source_elem_atck = new HashMap<String,Integer>();
		Entity source = damageSource.getImmediateSource();
		if(source!=null) 
		{
			// damage source should be either a mob, player or projectile (arrow/trident/witherskull)
			IElementalAttackData elem_atck_cap = new ElementalAttackData();
			if(source instanceof LivingEntity)
			{
				//mob or player
				// TODO: combine livingEntity attackSet with item attackSet? 
				// right now a WitherSkeleton won't deal any elemental dmg, since the dmg source is the stone_sword.  
				LivingEntity livingEntitySource = (LivingEntity) source;
				if(livingEntitySource.getHeldItemMainhand().isEmpty())
				{
					elem_atck_cap = livingEntitySource.getCapability(ElementalAttackDataCapability.ATK_DATA_CAPABILITY, null).orElse(new ElementalAttackData());
					System.out.println(livingEntitySource.getHeldItemMainhand().getDisplayName().getString());
					System.out.println("Source is " + source.getDisplayName().getString());
				}
				else
				{
					elem_atck_cap = livingEntitySource.getHeldItemMainhand().getCapability(ElementalAttackDataCapability.ATK_DATA_CAPABILITY, null).orElse(new ElementalAttackData());
					System.out.println("Source is " + livingEntitySource.getHeldItemMainhand().getDisplayName().getString()+ " hold by " + source.getDisplayName().getString());
				}
			}
			else
			{
				//projectile
				if(source instanceof AbstractArrowEntity)
				{
					AbstractArrowEntity arrow = (AbstractArrowEntity) source;
				}
				
				elem_atck_cap = source.getCapability(ElementalAttackDataCapability.ATK_DATA_CAPABILITY, null).orElse(new ElementalAttackData());
				System.out.println("Source is " + source.getDisplayName().getString());
			}
			source_elem_atck = elem_atck_cap.getAttackMap();
		}
		else
		{
			// fill List, if Source is not an entity, but a 'natural occurrence'.
			if(damageSource.isFireDamage())
			{
				source_elem_atck.put("fire",1);
			}
			else if(damageSource.getDamageType() == "drown")
			{
				source_elem_atck.put("water",1);
			}
			else if(damageSource.getDamageType() == "lightningBolt")
			{
				source_elem_atck.put("thunder",1);
			}
			
			//not sure, if i really want these
			else if(damageSource.getDamageType() == "cactus" || 
			   damageSource.getDamageType() == "sweetBerryBush")
			{
				source_elem_atck.put("plant",1);
			}
			else if(damageSource.getDamageType() == "wither")
			{
				source_elem_atck.put("unholy",1); //maybe element 'death'/'unholy'?
			}
		}
		
		// if attack doesn't have elemental properties, no need to check defense properties
		if( !source_elem_atck.isEmpty())
		{
			// Get the elemental combat data from target
			IElementalDefenseData elem_def_cap = target.getCapability(ElementalDefenseDataCapability.DEF_DATA_CAPABILITY, null).orElse(new ElementalDefenseData());

			Set<String> target_elem_absorb = elem_def_cap.getAbsorbSet();
			Set<String> target_elem_immune = elem_def_cap.getImmunitySet();
			Set<String> target_elem_resistant = elem_def_cap.getResistanceSet();
			Set<String> target_elem_weakness = elem_def_cap.getWeaknessSet();
			
			float damageAmount = event.getAmount();
			float newDamageAmount = 0;
			float valueSum = 0;
			
			Set<String> keySet = source_elem_atck.keySet();
			for(String key : keySet)
			{
				Integer value = source_elem_atck.get(key);
				valueSum += value;
				if (target_elem_absorb.contains(key))
				{
					newDamageAmount -= damageAmount*value;
					//add particle effect
				}
				else if (target_elem_immune.contains(key))
				{
					//add particle effect
				}
				else if (target_elem_resistant.contains(key))
				{
					newDamageAmount += damageAmount*value/2;
					//add particle effect
				}
				else if (target_elem_weakness.contains(key))
				{
					newDamageAmount += damageAmount*value*2;
					//add particle effect
				}
			}
			event.setAmount(newDamageAmount/valueSum);
		}
	}
}
