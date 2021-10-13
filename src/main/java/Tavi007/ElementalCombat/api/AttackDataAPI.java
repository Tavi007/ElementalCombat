package Tavi007.ElementalCombat.api;

import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.attack.AttackDataCapability;
import Tavi007.ElementalCombat.api.attack.AttackLayer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

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
	public static AttackData updateItemLayer(LivingEntity entity) {
		AttackData attackDataEntity = get(entity);
		AttackData attackDataItem = get(entity.getActiveItemStack());
		attackDataEntity.putLayer(new ResourceLocation("item"), attackDataItem.toLayer());
		return attackDataEntity;
	}

	/////////////////////
	// Helperfunctions //
	/////////////////////

	/**
	 * Set the attack-combat data {@link AttackData} of the {@link LivingEntity}. Also sends message to clients for syncronization.
	 * @param livingEntity A LivingEntity.
	 * @param attackDataToSet The AttackData to be set as the attack values of the LivingEntity.
	 */
	public static void putLayer(LivingEntity livingEntity, AttackLayer layer, ResourceLocation location) {
		AttackData atckData = get(livingEntity);
		atckData.putLayer(location, layer);
		if(livingEntity.isServerWorld()) {
			NetworkAPI.syncAttackLayerMessageForClients(livingEntity, layer, location);
		}
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
		if(immediateSource instanceof LivingEntity) {
			return get((LivingEntity) immediateSource);
		}
		else if(immediateSource instanceof ProjectileEntity) {
			return get((ProjectileEntity) immediateSource);
		}
		else {
			AttackData data = new AttackData();
			data.putLayer(new ResourceLocation("base"), BasePropertiesAPI.getAttackData(damageSource));
			return data;
		}
	}
}
