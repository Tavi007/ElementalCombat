package Tavi007.ElementalCombat.events;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Random;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.ElementalAttack;
import Tavi007.ElementalCombat.capabilities.ElementalDefense;
import Tavi007.ElementalCombat.particle.CombatParticleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
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
		if(source!=null) {
			// damage source should be either a mob, player or projectile (arrow/trident/witherskull)
			ElementalAttack elemAtckCap = new ElementalAttack();
			if(source instanceof LivingEntity){
				LivingEntity livingEntitySource = (LivingEntity) source;
				if(livingEntitySource.getHeldItemMainhand().isEmpty()){
					//use data from livingEntity
					elemAtckCap = ElementalCombatAPI.getElementalAttackData(livingEntitySource);
				}
				else{
					//use data from item
					elemAtckCap = ElementalCombatAPI.getElementalAttackData(livingEntitySource.getHeldItemMainhand());
				}
			}
			else if (source instanceof ProjectileEntity){
				//projectile
				elemAtckCap = ElementalCombatAPI.getElementalAttackData((ProjectileEntity) source);
			}
			else {
				ElementalCombat.LOGGER.info("Uknown damageSource case. How did you land here?");
			}
			sourceElemAtck = elemAtckCap.getAttackMap();
		}
		else{
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
		if( sourceElemAtck.isEmpty()){
			sourceElemAtck.put("natural", 1);
		}
		
		// Get the elemental combat data from target
		ElementalDefense elemDefCap = ElementalCombatAPI.getElementalDefenseData(target);
		Set<String> targetElemAbsorb = elemDefCap.getAbsorbSet();
		Set<String> targetElemImmunity = elemDefCap.getImmunitySet();
		Set<String> targetElemResistance = elemDefCap.getResistanceSet();
		Set<String> targetElemWeakness = elemDefCap.getWeaknessSet();
		
		// compute new Damage value and display particle effects
		float damageAmount = event.getAmount();
		float newDamageAmount = 0;
		float valueSum = 0;
		
		Vector3d eyePos = target.getEyePosition(0);
		final double POSITION_WOBBLE_AMOUNT = 0.01;
		Random rand = new Random();
		double xpos = eyePos.x + POSITION_WOBBLE_AMOUNT * (rand.nextDouble() - 0.5);
		double ypos = eyePos.y + POSITION_WOBBLE_AMOUNT * (rand.nextDouble() - 0.5);
		double zpos = eyePos.z + POSITION_WOBBLE_AMOUNT * (rand.nextDouble() - 0.5);

	    Color tint = new Color(1.00f, 1.00f, 1.0f);
	    double diameter = 1;
	      
		Set<String> keySet = sourceElemAtck.keySet();
		for(String key : keySet)
		{
			Integer value = sourceElemAtck.get(key);
			valueSum += value;
			
			double xSpeed = 0;
			double ySpeed = 1;
			double zSpeed = 0;
			
			target.getEntityWorld().addParticle(new CombatParticleData(tint, diameter), xpos, ypos, zpos, xSpeed, ySpeed, zSpeed);
			
			
			if (targetElemAbsorb.contains(key)){ //highest priority
				newDamageAmount -= damageAmount*value;
				//add particle effect
				}
			else if (targetElemImmunity.contains(key)){ // second highest priority
				//add particle effect
			}
			else if (targetElemResistance.contains(key)){ // third
				newDamageAmount += damageAmount*value/2;
				//add particle effect
			}
			else if (targetElemWeakness.contains(key)){ // last
				newDamageAmount += damageAmount*value*2;
				//add particle effect
			}
			else{
				newDamageAmount += damageAmount*value;
			}
		}
		
		if(valueSum == 0) {
			// Something has gone wrong, as this can only happen for incorrect data.
			// Datapacks should be error proof. Someone probably messed up in his mod. 
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
		// not tested yet.
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
