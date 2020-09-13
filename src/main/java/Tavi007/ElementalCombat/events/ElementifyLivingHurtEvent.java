package Tavi007.ElementalCombat.events;

import java.util.Random;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.loading.DamageSourceCombatProperties;
import Tavi007.ElementalCombat.particle.ParticleList;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
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
		String damageType = damageSource.getDamageType();
		System.out.println("Damage: " + damageSource.getDamageType());

		// no modification. Entity should take normal damage and die eventually.
		if(damageSource == DamageSource.OUT_OF_WORLD) {
			return;	
		}

		// Get combat data from attack
		String sourceElement;
		String sourceStyle;

		// check damageType
		if (damageType == "player" || damageType == "mob") {
			LivingEntity livingEntitySource = (LivingEntity) damageSource.getImmediateSource();
			if(livingEntitySource.getHeldItemMainhand().isEmpty()){
				//use data from livingEntity
				AttackData atckCap = ElementalCombatAPI.getAttackData(livingEntitySource);
				sourceStyle = atckCap.getStyle();
				sourceElement = atckCap.getElement();
			}
			else {
				//use data from held item
				AttackData atckCap = ElementalCombatAPI.getAttackDataWithEnchantment(livingEntitySource.getHeldItemMainhand());
				sourceStyle = atckCap.getStyle();
				sourceElement = atckCap.getElement();

				//maybe mix and match with entity data? a wither skeleton will only use data from the stone sword...
				AttackData atckCapEntity = ElementalCombatAPI.getAttackData(livingEntitySource);
				if (sourceStyle == ElementalCombat.DEFAULT_STYLE) {sourceStyle = atckCapEntity.getStyle();}
				if (sourceElement == ElementalCombat.DEFAULT_ELEMENT) {sourceElement = atckCapEntity.getElement();}
			}
		}
		else if(damageSource.isProjectile()) {
			AttackData atckCap = ElementalCombatAPI.getAttackData((ProjectileEntity) damageSource.getImmediateSource());
			sourceStyle = atckCap.getStyle();
			sourceElement = atckCap.getElement();
		}
		else {
			DamageSourceCombatProperties damageSourceProperties = ElementalCombatAPI.getDefaultProperties(damageSource);
			sourceStyle = damageSourceProperties.getAttackStyle();
			sourceElement = damageSourceProperties.getAttackElement();
		}

		//defaul values in case style or element is empty
		if (sourceStyle.isEmpty()) {sourceStyle = "basic";}
		if (sourceElement.isEmpty()) {sourceElement = "natural";}

		// compute new Damage value  
		LivingEntity target = event.getEntityLiving();
		float damageAmount = event.getAmount();
		// Get the protection data from target
		DefenseData defCap = ElementalCombatAPI.getDefenseData(target);
		float defenseStyleScaling = DefenseDataHelper.getScaling(defCap.getStyleFactor(), sourceStyle);
		float defenseElementScaling = DefenseDataHelper.getScaling(defCap.getElementFactor(), sourceElement);
		damageAmount = (float) (damageAmount*defenseStyleScaling*defenseElementScaling);

		// display particles
		displayParticle(defenseStyleScaling, defenseElementScaling, target.getEyePosition(0), (ServerWorld) target.getEntityWorld());

		// heals the target, if damage is lower than 0
		if(damageAmount <= 0)
		{
			target.heal(-damageAmount);
			event.setCanceled(true);
			damageAmount = 0;
		}

		event.setAmount(damageAmount);
	}

	private static void displayParticle(float scalingStyle, float scalingElement, Vector3d position, ServerWorld world) {
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
