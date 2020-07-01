package Tavi007.ElementalCombat.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.ElementalAttackData;
import Tavi007.ElementalCombat.capabilities.IElementalAttackData;
import Tavi007.ElementalCombat.capabilities.IElementalDefenseData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
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
		Map<String,Integer> sourceElemAtck = new HashMap<String,Integer>();
		Entity source = damageSource.getImmediateSource();
		if(source!=null) 
		{
			// damage source should be either a mob, player or projectile (arrow/trident/witherskull)
			IElementalAttackData elemAtckCap = new ElementalAttackData();
			if(source instanceof LivingEntity)
			{
				LivingEntity livingEntitySource = (LivingEntity) source;
				if(livingEntitySource.getHeldItemMainhand().isEmpty())
				{
					//use data from livingEntity
					elemAtckCap = ElementalCombatAPI.getElementalAttackData(livingEntitySource);
				}
				else
				{
					//use data from item
					elemAtckCap = ElementalCombatAPI.getElementalAttackData(livingEntitySource.getHeldItemMainhand());
				}
			}
			else if (source instanceof ProjectileEntity)
			{
				//projectile
				elemAtckCap = ElementalCombatAPI.getElementalAttackData((ProjectileEntity) source);
			}
			sourceElemAtck = elemAtckCap.getAttackMap();
		}
		else
		{
			// fill List, if Source is not an entity, but a 'natural occurrence'.
			if(damageSource.isFireDamage()){
				sourceElemAtck.put("fire",1);
			}
			else if(damageSource == DamageSource.DROWN){
				sourceElemAtck.put("water",1);
			}
			else if(damageSource == DamageSource.WITHER){
				sourceElemAtck.put("unholy",1); //maybe element 'death'/'unholy'?
			}
			else if(damageSource == DamageSource.IN_WALL){
				sourceElemAtck.put("earth",1); 
			}
			else if(damageSource == DamageSource.CACTUS || 
			   damageSource == DamageSource.SWEET_BERRY_BUSH){
				sourceElemAtck.put("plant",1);
			}
			else if(damageSource == DamageSource.LIGHTNING_BOLT){
				sourceElemAtck.put("thunder",1);
			}
		}
		
		// if attack has no elemental properties, it is of type 'natural'
		// this can make mobs immune etc. to non-elemental attacks
		if( sourceElemAtck.isEmpty())
		{
			sourceElemAtck.put("natural", 1);
		}
		
		// Get the elemental combat data from target
		IElementalDefenseData elemDefCap = ElementalCombatAPI.getElementalDefenseData(target);
		Set<String> targetElemAbsorb = elemDefCap.getAbsorbSet();
		Set<String> targetElemImmunity = elemDefCap.getImmunitySet();
		Set<String> targetElemResistance = elemDefCap.getResistanceSet();
		Set<String> targetElemWeakness = elemDefCap.getWeaknessSet();
		
		// compute new Damage value and display particle effects
		float damageAmount = event.getAmount();
		float newDamageAmount = 0;
		float valueSum = 0;
		Set<String> keySet = sourceElemAtck.keySet();
		for(String key : keySet)
		{
			Integer value = sourceElemAtck.get(key);
			valueSum += value;
			if (targetElemAbsorb.contains(key)) //highest priority
			{
				newDamageAmount -= damageAmount*value;
				//add particle effect
			}
			else if (targetElemImmunity.contains(key)) // second highest priority
			{
				//add particle effect
			}
			else if (targetElemResistance.contains(key)) // third
			{
				newDamageAmount += damageAmount*value/2;
				//add particle effect
			}
			else if (targetElemWeakness.contains(key)) // last
			{
				newDamageAmount += damageAmount*value*2;
				//add particle effect
			}
			else
			{
				newDamageAmount += damageAmount*value;
			}
		}
		
		if(valueSum == 0) // Something has gone wrong, as this can only happen for incorrect data (check datapacks or someone messed up in his mod) 
		{
			ElementalCombat.LOGGER.info("Elemental valueSum should never be 0. Use default damage instead.");
			return;
		}
		newDamageAmount = newDamageAmount/valueSum;
		
		// prints for testing
		boolean doPrint = true;
		if (doPrint)
		{
			System.out.println("\n" + 
							   "Target: " + target.getDisplayName().getString() + "\n" +
							   "Absorb: " + targetElemAbsorb + "\n" +
							   "Immunity: " + targetElemImmunity + "\n" +
							   "Resistance: " + targetElemResistance + "\n" +
							   "Weakness: " + targetElemWeakness + "\n" +
							   "DamageType: " + sourceElemAtck + "\n" +
							   "old value: " + damageAmount + "\n" +
							   "new value: " + newDamageAmount/valueSum);
		}
		
		// stop the 'hurt'-animation from firing, if no damage is dealt.
		if(newDamageAmount <= 0)
		{
			if(newDamageAmount < 0)
			{
				target.heal(-newDamageAmount);
			}
			event.setCanceled(true);
		}
	}
}
