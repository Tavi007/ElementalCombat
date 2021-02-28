package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.init.ParticleList;
import Tavi007.ElementalCombat.loading.DamageSourceCombatProperties;
import Tavi007.ElementalCombat.network.DisableDamageRenderMessage;
import Tavi007.ElementalCombat.network.EntityMessage;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class ServerEvents {

	@SubscribeEvent
	public static void addReloadListenerEvent(AddReloadListenerEvent event) {
		event.addListener(ElementalCombat.COMBAT_PROPERTIES_MANGER);
		ElementalCombat.LOGGER.info("ReloadListener for combat data registered.");
	}

	@SubscribeEvent
	public static void entityJoinWorld(EntityJoinWorldEvent event) {
		if(!event.getWorld().isRemote()) { // only server side should check
			Entity entity = event.getEntity();

			// for synchronization after switching dimensions
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				DefenseData defData = ElementalCombatAPI.getDefenseData(livingEntity);
				AttackData atckData = ElementalCombatAPI.getAttackData(livingEntity);

				if (livingEntity instanceof ServerPlayerEntity) {
					EntityMessage messageToClient = new EntityMessage(atckData, defData, false, livingEntity.getEntityId());
					ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);
				}
			}

			// for newly spawned projectiles.
			else if(entity instanceof ProjectileEntity) {
				if(entity.ticksExisted == 0){
					ProjectileEntity projectile = (ProjectileEntity) entity;
					Entity  source = projectile.func_234616_v_();
					if(source != null && source instanceof LivingEntity){
						AttackData sourceData = ElementalCombatAPI.getAttackDataWithActiveItem((LivingEntity) source);

						//copy elemental attack capability
						//maybe also check type of projectile?
						AttackData projectileData = ElementalCombatAPI.getAttackData(projectile);
						projectileData.setElement(sourceData.getElement());
						projectileData.setStyle("projectile");
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void elementifyLivingHurtEvent(LivingHurtEvent event) {
		DamageSource damageSource = event.getSource();
		Entity immediateSource = damageSource.getImmediateSource();

		// no modification. Entity should take normal damage and die eventually.
		if(damageSource == DamageSource.OUT_OF_WORLD) {
			return;	
		}

		// Get combat data from source
		String sourceElement;
		String sourceStyle;
		if(immediateSource instanceof LivingEntity) {
			AttackData atckCap = ElementalCombatAPI.getAttackDataWithActiveItem((LivingEntity) immediateSource);
			sourceStyle = atckCap.getStyle();
			sourceElement = atckCap.getElement();
		}
		else if(immediateSource instanceof ProjectileEntity) {
			AttackData atckCap = ElementalCombatAPI.getAttackData((ProjectileEntity) immediateSource);
			sourceStyle = atckCap.getStyle();
			sourceElement = atckCap.getElement();
		}
		else {
			DamageSourceCombatProperties damageSourceProperties = ElementalCombatAPI.getDefaultProperties(damageSource);
			sourceStyle = damageSourceProperties.getAttackStyle();
			sourceElement = damageSourceProperties.getAttackElement();
		}

		//default values in case style or element is empty (which should not happen)
		if (sourceStyle.isEmpty()) {sourceStyle = ServerConfig.getDefaultStyle();}
		if (sourceElement.isEmpty()) {sourceElement = ServerConfig.getDefaultElement();}

		// compute new Damage value  
		LivingEntity target = event.getEntityLiving();
		float damageAmount = event.getAmount();
		// Get the protection data from target
		DefenseData defCap = ElementalCombatAPI.getDefenseData(target);
		float defenseStyleScaling = Math.max(0.0f, DefenseDataHelper.getScaling(defCap.getStyleFactor(), sourceStyle));
		float defenseElementScaling = DefenseDataHelper.getScaling(defCap.getElementFactor(), sourceElement);
		damageAmount = (float) (damageAmount*defenseStyleScaling*defenseElementScaling);

		// display particles
		displayParticle(defenseStyleScaling, defenseElementScaling, target);

		// heals the target, if damage is lower than 0
		if(damageAmount <= 0) {
			target.heal(-damageAmount);
			event.setCanceled(true);
			damageAmount = 0;

			// send message to disable the hurt animation and sound.
			DisableDamageRenderMessage messageToClient = new DisableDamageRenderMessage(target.getEntityId());
			ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);

			// plays a healing sound 
			SoundEvent sound = SoundEvents.ENTITY_PLAYER_LEVELUP; //need better sound
			target.getEntityWorld().playSound(null, target.getPosition(), sound, SoundCategory.MASTER, 1.0f, 2.0f);
		}

		event.setAmount(damageAmount);
	}

	@SuppressWarnings("resource")
	private static void displayParticle(float scalingStyle, float scalingElement, LivingEntity entityHit) {

		if(scalingStyle < 1) {Minecraft.getInstance().particles.addParticleEmitter(entityHit, ParticleList.RESIST_STYLE.get());}
		else if (scalingStyle > 1) {Minecraft.getInstance().particles.addParticleEmitter(entityHit, ParticleList.CRIT_STYLE.get());}
		
		if(scalingElement < 0) {Minecraft.getInstance().particles.addParticleEmitter(entityHit, ParticleList.ABSORB.get());}
		else if (scalingElement >= 0 && scalingElement < 1) {Minecraft.getInstance().particles.addParticleEmitter(entityHit, ParticleList.RESIST_ELEMENT.get());}
		else if (scalingElement > 1) {Minecraft.getInstance().particles.addParticleEmitter(entityHit, ParticleList.CRIT_ELEMENT.get());}
	}
}