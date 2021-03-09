package Tavi007.ElementalCombat.api;

import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.attack.AttackDataCapability;
import Tavi007.ElementalCombat.config.ServerConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class AttackDataAPI {
	
	///////////////////
	// Living Entity //
	///////////////////

	/**
	 * Returns the attack-combat data {@link AttackData} of the {@link LivingEntity}.
	 * @param entity A LivingEntity.
	 * @return the AttackData, containing the attack style and attack element.
	 */
	public static AttackData get(LivingEntity entity) {
		AttackData attackData = (AttackData) entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
		if(!attackData.isInitialized()) {
			attackData.initialize(entity);
		}
		return attackData;
	}

	/**
	 * Returns the attack-combat data {@link AttackData} of the {@link LivingEntity}, but the held itemstack data will be applied aswell. 
	 * @param entity A LivingEntity.
	 * @return the AttackData, containing the attack style and attack element.
	 */
	public static AttackData getWithActiveItem(LivingEntity entity) {
		AttackData attackData = new AttackData();
		if(entity.getHeldItemMainhand().isEmpty()){
			//use data from livingEntity
			attackData.set(get(entity));
		}
		else {
			//use data from held item
			attackData.set(get(entity.getHeldItemMainhand()));

			//maybe mix and match with entity data? a wither skeleton will only use data from the stone sword...
			AttackData atckDataEntity = get(entity);
			if (attackData.getStyle().equals(ServerConfig.getDefaultStyle())) {
				attackData.setStyle(atckDataEntity.getStyle());
			}
			if (attackData.getElement().equals(ServerConfig.getDefaultElement())) {
				attackData.setElement(atckDataEntity.getElement());
			}
		}
		return attackData;
	}

	///////////////
	// ItemStack //
	///////////////


	/**
	 * Returns the attack-combat data {@link AttackData} of the {@link ItemStack}.
	 * @param stack An ItemStack.
	 * @return the AttackData, containing the attack style and attack element.
	 */
	public static AttackData get(ItemStack stack) {
		if(stack.isEmpty()) {
			return new AttackData();
		}
		else {
			AttackData attackData = (AttackData) stack.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
			if(!attackData.isInitialized()) {
				attackData.initialize(stack);
			}
			if (!attackData.areEnchantmentChangesApplied()) {
				attackData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
			}
			return attackData;
		}
	}

	/////////////////
	// Projectiles //
	/////////////////

	/**
	 * Returns the attack-combat data {@link AttackData} of the {@link ProjectileEntity}.
	 * @param projectileEntity A ProjectileEntity.
	 * @return the AttackData, containing the attack style and attack element.
	 */
	public static AttackData get(ProjectileEntity projectileEntity) {
		AttackData attackData = (AttackData) projectileEntity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
		if(!attackData.isInitialized()) {
			attackData.initialize(projectileEntity);
		}
		return attackData;
	}

	///////////////////
	// DamageSources //
	///////////////////

	/**
	 * Returns the attack-combat data {@link AttackData} of the {@link DamageSource}.
	 * @param damageSource A DamageSource.
	 * @return the AttackData, containing the attack style and attack element.
	 */
	public static AttackData get(DamageSource damageSource) {
		Entity immediateSource = damageSource.getImmediateSource();

		// Get combat data from source
		AttackData atckCap;
		if(immediateSource instanceof LivingEntity) {
			atckCap = getWithActiveItem((LivingEntity) immediateSource);
		}
		else if(immediateSource instanceof ProjectileEntity) {
			atckCap = get((ProjectileEntity) immediateSource);
		}
		else {
			atckCap = DefaultPropertiesAPI.getAttackData(damageSource);
		}
		return new AttackData(atckCap);
	}
}
