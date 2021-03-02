package Tavi007.ElementalCombat.api;

import org.apache.commons.lang3.text.WordUtils;

import Tavi007.ElementalCombat.ElementalCombat;
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

@SuppressWarnings("deprecation")
public class DefaultProperties {
	/**
	 * Returns a copy of the default {@link EntityCombatProperties} of any {@link LivingEntity}.
	 * @param livingEntity The LivingEntity.
	 * @return copy of EntityCombatProperties.
	 */
	public static EntityCombatProperties get(LivingEntity livingEntity) {
		ResourceLocation rlEntity = livingEntity.getType().getRegistryName();
		if (rlEntity == null) {
			return new EntityCombatProperties();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "entities/" + rlEntity.getPath());
		return ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rlProperties);

	}

	/**
	 * Returns a copy of the default {@link ItemCombatProperties} of any {@link ItemStack}.
	 * @param stack The ItemStack.
	 * @return copy of ItemCombatProperties.
	 */
	public static ItemCombatProperties get(ItemStack stack) {
		ResourceLocation rlItem = stack.getItem().getRegistryName();
		if (rlItem == null) {
			return new ItemCombatProperties();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlItem.getNamespace(), "items/" + rlItem.getPath());
		return ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties);
	}

	/**
	 * Returns a copy of the default {@link BiomeCombatProperties} of any {@link Biome}.
	 * @param biome The Biome.
	 * @return copy of BiomeCombatProperties.
	 */
	public static BiomeCombatProperties get(Biome biome) {
		ResourceLocation rlBiome = biome.getRegistryName();
		if (rlBiome == null) {
			return new BiomeCombatProperties();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlBiome.getNamespace(), "biomes/" + rlBiome.getPath()); ;
		return ElementalCombat.COMBAT_PROPERTIES_MANGER.getBiomeDataFromLocation(rlProperties);
	}

	/**
	 * Returns a copy of the default {@link AttackOnlyCombatProperties} of any {@link DamageSource}. 
	 * This includes lightning, burning, drowning, suffocating in a wall and so on.
	 * @param damageSource The DamageSource.
	 * @return copy of AttackOnlyCombatProperties.
	 */
	public static AttackOnlyCombatProperties get(DamageSource damageSource) {
		ResourceLocation rlDamageSource=null;
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
		return ElementalCombat.COMBAT_PROPERTIES_MANGER.getDamageSourceDataFromLocation(rlDamageSource);
	}

	/**
	 * Returns a copy of the default {@link AttackOnlyCombatProperties} of any {@link ProjectileEntity}. 
	 * This includes lightning, burning, drowning, suffocating in a wall and so on.
	 * @param projectile The Projectile.
	 * @return copy of AttackOnlyCombatProperties.
	 */
	public static AttackOnlyCombatProperties get(ProjectileEntity projectile) {
		ResourceLocation resourcelocation = projectile.getType().getRegistryName();
		ResourceLocation rlDamageSource = new ResourceLocation(resourcelocation.getNamespace(), "projectiles/" + resourcelocation.getPath());
		return ElementalCombat.COMBAT_PROPERTIES_MANGER.getProjectileDataFromLocation(rlDamageSource);
	}
	
	/**
	 * Returns the mapped String, which is defined in the combat_properties_mapping.json.
	 * Always use this function, when you want to display the data.
	 * If the key (and therefore a mapped value) does not exist, return the capitalized key.
	 * @param key They key (aka the value on the left side in the .json)
	 * @return the corresponding String (aka the value on the right side in the .json)
	 */
	public static String getMappedString(String key) {
		String value = ElementalCombat.COMBAT_PROPERTIES_MANGER.getPropertiesMapping().getValue(key);
		if(value == null) {
			return WordUtils.capitalize(key);
		}
		return value; 
	}
}
