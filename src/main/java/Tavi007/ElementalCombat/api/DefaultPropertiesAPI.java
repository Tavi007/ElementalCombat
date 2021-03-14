package Tavi007.ElementalCombat.api;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.loading.AttackOnlyCombatProperties;
import Tavi007.ElementalCombat.loading.BiomeCombatProperties;
import Tavi007.ElementalCombat.loading.EntityCombatProperties;
import Tavi007.ElementalCombat.loading.ItemCombatProperties;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class DefaultPropertiesAPI {
	
	////////////////
	// Attackdata //
	////////////////
	
	/**
	 * Returns a copy of the default {@link AttackData} of any {@link LivingEntity}.
	 * @param livingEntity The LivingEntity.
	 * @return copy of AttackData.
	 */
	public static AttackData getAttackData(LivingEntity livingEntity) {
		ResourceLocation rlEntity = livingEntity.getType().getRegistryName();
		if (rlEntity == null) {
			return new AttackData();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "entities/" + rlEntity.getPath());
		EntityCombatProperties property = new EntityCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rlProperties));
		return new AttackData(property.getAttackStyle(), property.getAttackElement());
	}

	/**
	 * Returns a copy of the default {@link AttackData} of any {@link ItemStack}.
	 * @param stack The ItemStack.
	 * @return copy of AttackData.
	 */
	public static AttackData getAttackData(ItemStack stack) {
		ResourceLocation rlItem = stack.getItem().getRegistryName();
		if (rlItem == null) {
			return new AttackData();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlItem.getNamespace(), "items/" + rlItem.getPath());
		ItemCombatProperties property = new ItemCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties));
		return new AttackData(property.getAttackStyle(), property.getAttackElement());
	}

	/**
	 * Returns a copy of the default {@link AttackData} of any {@link DamageSource}. 
	 * This includes lightning, burning, drowning, suffocating in a wall and so on.
	 * @param damageSource The DamageSource.
	 * @return copy of AttackData.
	 */
	public static AttackData getAttackData(DamageSource damageSource) {
		ResourceLocation rlDamageSource = null;
		// do other mods implement their own natural damageSource? If so, how could I get the mod id from it?
		// for now do not use Namespace.
		if(damageSource.isExplosion()) {
			rlDamageSource = new ResourceLocation("minecraft", "damage_sources/explosion");
		}
		else if(damageSource.isMagicDamage()) {
			rlDamageSource = new ResourceLocation("minecraft", "damage_sources/magic");
		}
		else {
			rlDamageSource = new ResourceLocation("minecraft", "damage_sources/" + damageSource.getDamageType().toLowerCase());
		}
		AttackOnlyCombatProperties property = new AttackOnlyCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getDamageSourceDataFromLocation(rlDamageSource));
		return new AttackData(property.getAttackStyle(), property.getAttackElement());
	}

	/**
	 * Returns a copy of the default {@link AttackData} of any {@link ProjectileEntity}. 
	 * This includes lightning, burning, drowning, suffocating in a wall and so on.
	 * @param projectile The Projectile.
	 * @return copy of AttackData.
	 */
	public static AttackData getAttackData(ProjectileEntity projectile) {
		ResourceLocation resourcelocation = projectile.getType().getRegistryName();
		ResourceLocation rlDamageSource = new ResourceLocation(resourcelocation.getNamespace(), "projectiles/" + resourcelocation.getPath());
		AttackOnlyCombatProperties property = new AttackOnlyCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getProjectileDataFromLocation(rlDamageSource));
		return new AttackData(property.getAttackStyle(), property.getAttackElement());
	}
	
	/////////////////
	// Defensedata //
	/////////////////
	
	/**
	 * Returns a copy of the default {@link DefenseData} of any {@link LivingEntity}.
	 * @param livingEntity The LivingEntity.
	 * @return copy of DefenseData.
	 */
	public static DefenseData getDefenseData(LivingEntity livingEntity) {
		ResourceLocation rlEntity = livingEntity.getType().getRegistryName();
		if (rlEntity == null) {
			return new DefenseData();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "entities/" + rlEntity.getPath());
		EntityCombatProperties property = new EntityCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rlProperties));
		return new DefenseData(property.getDefenseStyle(), property.getDefenseElement());
	}

	/**
	 * Returns a copy of the default {@link DefenseData} of any {@link ItemStack}.
	 * @param stack The ItemStack.
	 * @return copy of DefenseData.
	 */
	public static DefenseData getDefenseData(ItemStack stack) {
		ResourceLocation rlItem = stack.getItem().getRegistryName();
		if (rlItem == null) {
			return new DefenseData();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlItem.getNamespace(), "items/" + rlItem.getPath());
		ItemCombatProperties property = new ItemCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties));
		return new DefenseData(property.getDefenseStyle(), property.getDefenseElement());
		
	}

	/**
	 * Returns a copy of the default {@link DefenseData} of any {@link Biome}.
	 * @param biome The Biome.
	 * @return copy of DefenseData.
	 */
	public static DefenseData getDefenseData(Biome biome) {
		DefenseData defData = new DefenseData();
		ResourceLocation rlBiome = biome.getRegistryName();
		if (rlBiome == null) {
			return defData;
		}
		ResourceLocation rlProperties = new ResourceLocation(rlBiome.getNamespace(), "biomes/" + rlBiome.getPath()); ;
		BiomeCombatProperties property = new BiomeCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getBiomeDataFromLocation(rlProperties));
		defData.setElementFactor(property.getDefenseElement());
		return defData;
	}
	
	/////////////////
	// other stuff //
	/////////////////
	
	/**
	 * Returns the default value of the biome_dependency of any {@link LivingEntity}.
	 * @param livingEntity The LivingEntity.
	 * @return boolean isBiomeDependent.
	 */
	public static boolean isBiomeDependent(LivingEntity livingEntity) {
		ResourceLocation rlEntity = livingEntity.getType().getRegistryName();
		if (rlEntity == null) {
			return false;
		}
		ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "entities/" + rlEntity.getPath());
		return ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rlProperties).getBiomeDependency();
	}
}
