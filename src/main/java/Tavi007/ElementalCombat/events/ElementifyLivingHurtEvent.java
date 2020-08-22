package Tavi007.ElementalCombat.events;

import java.util.Random;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
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
		if(damageSource == DamageSource.OUT_OF_WORLD) {
			// no modification. Entity should take normal damage and die eventually.
			return;	
		}
		
		
		LivingEntity target = event.getEntityLiving();
		
		
		// Get elemental data from attack
		// check if source is an entity
		String sourceElement = "";
		String sourceStyle = "";
		
		Entity source = damageSource.getImmediateSource();
		if(source!=null) {
			// damage source should be either a mob, player or projectile (arrow/trident/witherskull)
			AttackData atckCap = new AttackData();
			if(source instanceof LivingEntity){
				LivingEntity livingEntitySource = (LivingEntity) source;
				if(livingEntitySource.getHeldItemMainhand().isEmpty()){
					//use data from livingEntity
					atckCap = ElementalCombatAPI.getAttackData(livingEntitySource);
				}
				else{
					//use data from item
					atckCap = ElementalCombatAPI.getAttackData(livingEntitySource.getHeldItemMainhand());
				}
			}
			else if (source instanceof ProjectileEntity){
				//use data from projectile
				atckCap = ElementalCombatAPI.getAttackData((ProjectileEntity) source);
			}
			else {
				ElementalCombat.LOGGER.info("Uknown damageSource case. How did you land here?");
			}
			
			sourceStyle = atckCap.getStyle();
			sourceElement = atckCap.getElement();
		}
		else{
			// fill List, if Source is not an entity, but a 'natural occurrence'.
			// maybe set data through json files? more customization this way
			if(damageSource.isFireDamage()){
				sourceElement = "fire";
			}
			else if(damageSource == DamageSource.DROWN){
				sourceElement = "water";
			}
			else if(damageSource == DamageSource.WITHER){
				sourceElement = "unholy";
			}
			else if(damageSource == DamageSource.MAGIC){
				sourceStyle = "magic";
			}
			else if(damageSource == DamageSource.IN_WALL){
				sourceElement = "earth";
			}
			else if(damageSource == DamageSource.CACTUS || 
			   damageSource == DamageSource.SWEET_BERRY_BUSH){
				sourceElement = "plant";
			}
			else if(damageSource == DamageSource.LIGHTNING_BOLT){
				sourceElement = "thunder";
			}
		}
		
		// compute new Damage value  
		float damageAmount = event.getAmount();
		// Get the elemental combat data from target
		DefenseData defCap = ElementalCombatAPI.getDefenseData(target);
		Double defenseStyleScaling = defCap.getStyleScaling().get(sourceStyle);
		Double defenseElementScaling = defCap.getElementScaling().get(sourceElement);
		
		if (defenseStyleScaling == null) {defenseStyleScaling = 1.0;}
		if (defenseElementScaling == null) {defenseElementScaling = 1.0;}
		damageAmount = (float) (damageAmount*defenseStyleScaling*defenseElementScaling);
		
		// display particles
		displayParticle(defenseStyleScaling, defenseElementScaling, target.getEyePosition(0), (ServerWorld) target.getEntityWorld());
		
		// stop the 'hurt'-animation from firing, if no damage is dealt.
		// not tested yet.
		if(damageAmount <= 0)
		{
			target.heal(-damageAmount);
			event.setCanceled(true);
			damageAmount = 0;
		}
		
		event.setAmount(damageAmount);
	}

	private static void displayParticle(Double scalingStyle, Double scalingElement, Vector3d position, ServerWorld world) {
		final double POSITION_WOBBLE_AMOUNT = 0.01;
		Random rand = new Random();
		double xpos = position.x + POSITION_WOBBLE_AMOUNT * (rand.nextDouble() - 0.5);
		double ypos = position.y + POSITION_WOBBLE_AMOUNT * (rand.nextDouble() - 0.5);
		double zpos = position.z + POSITION_WOBBLE_AMOUNT * (rand.nextDouble() - 0.5);

		double xoff = 0.0; 
		double yoff = 0.0; 
		double zoff = 0.0; 
		double speed = 0.2D;
		
		if (scalingStyle < 0) {world.spawnParticle(ParticleList.STYLE_ABSORPTION.get(), xpos, ypos, zpos, 1, xoff, yoff, zoff, speed);}
		else if(scalingStyle == 0) {world.spawnParticle(ParticleList.STYLE_IMMUNITY.get(), xpos, ypos, zpos, 1, xoff, yoff, zoff, speed);}
		else if(scalingStyle > 0 && scalingStyle < 1) {world.spawnParticle(ParticleList.STYLE_RESISTANCE.get(), xpos, ypos, zpos, 1, xoff, yoff, zoff, speed);}
		else if(scalingStyle > 1) {world.spawnParticle(ParticleList.STYLE_WEAKNESS.get(), xpos, ypos, zpos, 1, xoff, yoff, zoff, speed);}

		if (scalingElement < 0) {world.spawnParticle(ParticleList.ELEMENT_ABSORPTION.get(), xpos, ypos, zpos, 1, xoff, yoff, zoff, speed);}
		else if(scalingElement == 0) {world.spawnParticle(ParticleList.ELEMENT_IMMUNITY.get(), xpos, ypos, zpos, 1, xoff, yoff, zoff, speed);}
		else if(scalingElement > 0 && scalingElement < 1) {world.spawnParticle(ParticleList.ELEMENT_RESISTANCE.get(), xpos, ypos, zpos, 1, xoff, yoff, zoff, speed);}
		else if(scalingElement > 1) {world.spawnParticle(ParticleList.ELEMENT_WEAKNESS.get(), xpos, ypos, zpos, 1, xoff, yoff, zoff, speed);}
	}

}
