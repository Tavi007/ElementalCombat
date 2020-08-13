package Tavi007.ElementalCombat.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Random;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.ElementalAttack;
import Tavi007.ElementalCombat.capabilities.defense.ElementalDefense;
import Tavi007.ElementalCombat.particle.ParticleList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
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
				//use data from projectile
				elemAtckCap = ElementalCombatAPI.getElementalAttackData((ProjectileEntity) source);
			}
			else {
				ElementalCombat.LOGGER.info("Uknown damageSource case. How did you land here?");
			}
			sourceElemAtck = elemAtckCap.getElementalAttack();
		}
		else{
			// fill List, if Source is not an entity, but a 'natural occurrence'.
			// maybe set data through json files? more customization this way
			if(damageSource.isFireDamage()){
				sourceElemAtck.put("fire",1);
			}
			else if(damageSource == DamageSource.DROWN){
				sourceElemAtck.put("water",1);
			}
			else if(damageSource == DamageSource.WITHER){
				sourceElemAtck.put("unholy",1);
			}
			else if(damageSource == DamageSource.MAGIC){
				sourceElemAtck.put("magic",1);
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
			else if(damageSource == DamageSource.OUT_OF_WORLD) {
				// no modification. Entity should take normal damage and die eventually.
				return;	
			}
		}
		
		// if attack has no elemental properties, it is of type 'natural'
		// this can make mobs immune etc. to non-elemental attacks
		// this check might me redundant, but is left here just to be sure.
		if( sourceElemAtck.isEmpty()){
			sourceElemAtck.put("natural", 1);
		}
		
		// Get the elemental combat data from target
		ElementalDefense elemDefCap = ElementalCombatAPI.getElementalDefenseData(target);
		Set<String> targetElemAbsorbtion = elemDefCap.getElementalAbsorption();
		Set<String> targetElemImmunity = elemDefCap.getElementalImmunity();
		HashMap<String, Integer> targetElemResistance = elemDefCap.getElementalResistance();
		HashMap<String, Integer> targetElemWeakness = elemDefCap.getElementalWeakness();
		
		// compute new Damage value and display particle effects
		float damageAmount = event.getAmount();
		float newDamageAmount = 0.0f;
		float valueSum = 0.0f;
		
		
		Vector3d eyePos = target.getEyePosition(0);
		final double POSITION_WOBBLE_AMOUNT = 0.01;
		Random rand = new Random();
		double xpos = eyePos.x + POSITION_WOBBLE_AMOUNT * (rand.nextDouble() - 0.5);
		double ypos = eyePos.y + POSITION_WOBBLE_AMOUNT * (rand.nextDouble() - 0.5);
		double zpos = eyePos.z + POSITION_WOBBLE_AMOUNT * (rand.nextDouble() - 0.5);

		double xoff = 0.0; 
		double yoff = 0.0; 
		double zoff = 0.0; 
		double speed = 0.2D;
		
		Set<String> keySet = sourceElemAtck.keySet();
		for(String key : keySet)
		{
			Integer value = sourceElemAtck.get(key);
			valueSum += value;
			if (targetElemAbsorbtion.contains(key)){ //highest priority
				newDamageAmount -= damageAmount*value;
				((ServerWorld) target.getEntityWorld()).spawnParticle(ParticleList.ABSORPTION_PARTICLE.get(), xpos, ypos, zpos, 1, xoff, yoff, zoff, speed);
			}
			else if (targetElemImmunity.contains(key)){ // second highest priority
				((ServerWorld) target.getEntityWorld()).spawnParticle(ParticleList.IMMUNITY_PARTICLE.get(), xpos, ypos, zpos, 1, xoff, yoff, zoff, speed);
			}
			else if (targetElemResistance.containsKey(key)){ // third
				newDamageAmount += damageAmount*value/(targetElemResistance.get(key)+1);
				((ServerWorld) target.getEntityWorld()).spawnParticle(ParticleList.RESISTANCE_PARTICLE.get(), xpos, ypos, zpos, 1, xoff, yoff, zoff, speed);
			}
			else if (targetElemWeakness.containsKey(key)){ // last
				newDamageAmount += damageAmount*value*(targetElemWeakness.get(key)+1);
				((ServerWorld) target.getEntityWorld()).spawnParticle(ParticleList.WEAKNESS_PARTICLE.get(), xpos, ypos, zpos, 1, xoff, yoff, zoff, speed);
			}
			else{
				newDamageAmount += damageAmount*value;
			}
		}
		
		if(valueSum == 0) {
			// Something has gone wrong, as this can only happen for incorrect data.
			// Datapacks should be error proof. Someone probably messed up in his mod and used the API incorrectly. 
			ElementalCombat.LOGGER.info("Elemental valueSum should never be 0. Use default damage instead.");
			return;
		}
		newDamageAmount = newDamageAmount/valueSum;
		
		// stop the 'hurt'-animation from firing, if no damage is dealt.
		// not tested yet.
		if(newDamageAmount <= 0)
		{
			target.heal(-newDamageAmount);
			event.setCanceled(true);
			
			newDamageAmount = 0;
		}
		
		event.setAmount(newDamageAmount);
	}
}
