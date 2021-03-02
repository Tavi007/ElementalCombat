package Tavi007.ElementalCombat.api;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackDataCapability;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.loading.AttackOnlyCombatProperties;
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
		return (AttackData) entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
	}

	/**
	 * Returns the attack-combat data {@link AttackData} of the {@link LivingEntity}, but the held itemstack data will be applied aswell. 
	 * @param entity A LivingEntity.
	 * @return the AttackData, containing the attack style and attack element.
	 */
	public static AttackData getWithActiveItem(LivingEntity entity) {
		AttackData atckData = new AttackData();
		if(entity.getHeldItemMainhand().isEmpty()){
			//use data from livingEntity
			atckData.set(get(entity));
		}
		else {
			//use data from held item
			atckData.set(get(entity.getHeldItemMainhand()));

			//maybe mix and match with entity data? a wither skeleton will only use data from the stone sword...
			AttackData atckDataEntity = get(entity);
			if (atckData.getStyle().equals(ServerConfig.getDefaultStyle())) {
				atckData.setStyle(atckDataEntity.getStyle());
			}
			if (atckData.getElement().equals(ServerConfig.getDefaultElement())) {
				atckData.setElement(atckDataEntity.getElement());
			}
		}
		return atckData;
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
		return (AttackData) projectileEntity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
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
		String sourceElement;
		String sourceStyle;
		if(immediateSource instanceof LivingEntity) {
			AttackData atckCap = getWithActiveItem((LivingEntity) immediateSource);
			sourceStyle = atckCap.getStyle();
			sourceElement = atckCap.getElement();
		}
		else if(immediateSource instanceof ProjectileEntity) {
			AttackData atckCap = get((ProjectileEntity) immediateSource);
			sourceStyle = atckCap.getStyle();
			sourceElement = atckCap.getElement();
		}
		else {
			AttackOnlyCombatProperties damageSourceProperties = DefaultProperties.get(damageSource);
			sourceStyle = damageSourceProperties.getAttackStyle();
			sourceElement = damageSourceProperties.getAttackElement();
		}

		//default values in case style or element is empty (which should not happen)
		if (sourceStyle.isEmpty()) {sourceStyle = ServerConfig.getDefaultStyle();}
		if (sourceElement.isEmpty()) {sourceElement = ServerConfig.getDefaultElement();}

		return new AttackData(sourceStyle, sourceElement);
	}
}
