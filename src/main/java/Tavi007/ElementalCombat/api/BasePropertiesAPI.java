package Tavi007.ElementalCombat.api;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.init.EnchantmentList;
import Tavi007.ElementalCombat.loading.AttackOnlyCombatProperties;
import Tavi007.ElementalCombat.loading.BiomeCombatProperties;
import Tavi007.ElementalCombat.loading.MobCombatProperties;
import Tavi007.ElementalCombat.loading.ItemCombatProperties;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class BasePropertiesAPI {

	////////////////
	// Attackdata //
	////////////////

	/**
	 * Returns a copy of the default {@link AttackData} of any {@link LivingEntity}.
	 * @param livingEntity The LivingEntity.
	 * @return copy of AttackData.
	 */
	public static AttackLayer getAttackData(LivingEntity livingEntity) {
		ResourceLocation rlEntity = livingEntity.getType().getRegistryName();
		if (rlEntity == null) {
			return new AttackLayer();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "mobs/" + rlEntity.getPath());
		MobCombatProperties property = new MobCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getMobDataFromLocation(rlProperties));
		return new AttackLayer(property.getAttackStyle(), property.getAttackElement());
	}

	/**
	 * Returns a copy of the default {@link AttackData} of any {@link ItemStack}.
	 * @param stack The ItemStack.
	 * @return copy of AttackData.
	 */
	public static AttackLayer getAttackData(ItemStack stack) {
		ResourceLocation rlItem = stack.getItem().getRegistryName();
		if (rlItem == null) {
			return new AttackLayer();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlItem.getNamespace(), "items/" + rlItem.getPath());
		ItemCombatProperties property = new ItemCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties));
		return new AttackLayer(property.getAttackStyle(), property.getAttackElement());
	}

	/**
	 * Returns a copy of the default {@link AttackData} of the {@link EffectInstance}.
	 * Currently only checks for INSTANT_DAMAGE, which has the style 'magic'. Might rework at some point.
	 * @param effect The EffectInstance.
	 * @return copy of AttackData.
	 */
	public static AttackLayer getAttackLayer(EffectInstance effect) {
		AttackLayer base = new AttackLayer();
		if(effect.getPotion() == Effects.INSTANT_DAMAGE) {
			base.setStyle("magic");
		}
		return base;
	}

	/**
	 * Returns a copy of the default {@link AttackData} of the {@link Enchantment}.
	 * Currently only checks hardcoded values. Might rework at some point.
	 * @param ench The Enchantment.
	 * @return copy of AttackData.
	 */
	public static AttackLayer getAttackLayer(Enchantment ench) {
		AttackLayer layer = new AttackLayer();
		if(ench.getName().equals(Enchantments.FIRE_ASPECT.getName())) {
			layer.setElement("fire");
		} else if(ench.getName().equals(EnchantmentList.ICE_ASPECT.get().getName())) {
			layer.setElement("ice");
		} else if(ench.getName().equals(EnchantmentList.WATER_ASPECT.get().getName())) {
			layer.setElement("water");
		} else if(ench.getName().equals(EnchantmentList.THUNDER_ASPECT.get().getName())) {
			layer.setElement("thunder");
		} else if(ench.getName().equals(EnchantmentList.DARKNESS_ASPECT.get().getName())) {
			layer.setElement("darkness");
		} else if(ench.getName().equals(EnchantmentList.LIGHT_ASPECT.get().getName())) {
			layer.setElement("light");
		} else if(ench.getName().equals(Enchantments.FLAME.getName())) {
			layer.setElement("fire");
		} else if(ench.getName().equals(Enchantments.CHANNELING.getName())) {
			layer.setElement("thunder");
		}
		return layer;
	}

	/**
	 * Returns a copy of the default {@link AttackData} of any {@link DamageSource}. 
	 * This includes lightning, burning, drowning, suffocating in a wall and so on.
	 * @param damageSource The DamageSource.
	 * @return copy of AttackData.
	 */
	public static AttackLayer getAttackData(DamageSource damageSource) {
		ResourceLocation rlDamageSource = null;
		// do other mods implement their own natural damageSource? If so, how could I get the mod id from it?
		// for now do not use Namespace.
		if(damageSource.isExplosion()) {
			rlDamageSource = new ResourceLocation("minecraft", "damage_sources/explosion");
		} else if(damageSource.isMagicDamage()) {
			rlDamageSource = new ResourceLocation("minecraft", "damage_sources/magic");
		} else {
			rlDamageSource = new ResourceLocation("minecraft", "damage_sources/" + damageSource.getDamageType().toLowerCase());
		}
		AttackOnlyCombatProperties property = new AttackOnlyCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getDamageSourceDataFromLocation(rlDamageSource));
		return new AttackLayer(property.getAttackStyle(), property.getAttackElement());
	}

	/**
	 * Returns a copy of the default {@link AttackData} of any {@link ProjectileEntity}. 
	 * @param projectile The Projectile.
	 * @return copy of AttackData.
	 */
	public static AttackLayer getAttackData(ProjectileEntity projectile) {
		if(projectile instanceof ProjectileItemEntity) {
			ItemStack stack = ((ProjectileItemEntity) projectile).getItem();
			return getAttackData(stack);
		}
		ResourceLocation resourcelocation = projectile.getType().getRegistryName();
		ResourceLocation rlProjectile = new ResourceLocation(resourcelocation.getNamespace(), "projectiles/" + resourcelocation.getPath());
		AttackOnlyCombatProperties property = new AttackOnlyCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getProjectileDataFromLocation(rlProjectile));
		return new AttackLayer(property.getAttackStyle(), property.getAttackElement());
	}

	/////////////////
	// Defensedata //
	/////////////////

	/**
	 * Returns a copy of the default {@link DefenseData} of any {@link LivingEntity}.
	 * @param livingEntity The LivingEntity.
	 * @return copy of DefenseData.
	 */
	public static DefenseLayer getDefenseLayer(LivingEntity livingEntity) {
		ResourceLocation rlEntity = livingEntity.getType().getRegistryName();
		if (rlEntity == null) {
			return new DefenseLayer();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "mobs/" + rlEntity.getPath());
		MobCombatProperties property = new MobCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getMobDataFromLocation(rlProperties));
		return new DefenseLayer(property.getDefenseStyle(), property.getDefenseElement());
	}

	/**
	 * Returns a copy of the default {@link DefenseData} of any {@link ItemStack}.
	 * @param stack The ItemStack.
	 * @return copy of DefenseData.
	 */
	public static DefenseLayer getDefenseLayer(ItemStack stack) {
		ResourceLocation rlItem = stack.getItem().getRegistryName();
		if (rlItem == null) {
			return new DefenseLayer();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlItem.getNamespace(), "items/" + rlItem.getPath());
		ItemCombatProperties property = new ItemCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties));
		return new DefenseLayer(property.getDefenseStyle(), property.getDefenseElement());
	}

	/**
	 * Returns a copy of the default {@link DefenseData} of any {@link Enchantment}.
	 * @param ench The Enchantment.
	 * @return copy of DefenseData.
	 */
	public static DefenseLayer getDefenseLayer(Enchantment ench, int level) {
		HashMap<String, Integer> defElement = new HashMap<String, Integer>();
		HashMap<String, Integer> defStyle = new HashMap<String, Integer>();
		if (level != 0) {
			// elemental enchantments
			if(ench == Enchantments.FIRE_PROTECTION) {
				defElement.put( "fire", level*ServerConfig.getEnchantmentScaling());
				defElement.put( "ice", -level*ServerConfig.getEnchantmentScaling()/2);
			}
			else if(ench == EnchantmentList.ICE_PROTECTION.get()) {
				defElement.put("ice", level*ServerConfig.getEnchantmentScaling());
				defElement.put("fire", -level*ServerConfig.getEnchantmentScaling()/2);
			}
			else if(ench == EnchantmentList.WATER_PROTECTION.get()) {
				defElement.put( "water", level*ServerConfig.getEnchantmentScaling());
				defElement.put( "thunder", -level*ServerConfig.getEnchantmentScaling()/2);
			}
			else if(ench == EnchantmentList.THUNDER_PROTECTION.get()) {
				defElement.put( "thunder", level*ServerConfig.getEnchantmentScaling());
				defElement.put( "water", -level*ServerConfig.getEnchantmentScaling()/2);
			}
			else if(ench == EnchantmentList.DARKNESS_PROTECTION.get()) {
				defElement.put( "darkness", level*ServerConfig.getEnchantmentScaling());
				defElement.put( "light", -level*ServerConfig.getEnchantmentScaling()/2);
			}
			else if(ench == EnchantmentList.LIGHT_PROTECTION.get()) {
				defElement.put( "light", level*ServerConfig.getEnchantmentScaling());
				defElement.put( "darkness", -level*ServerConfig.getEnchantmentScaling()/2);
			}
			else if(ench == EnchantmentList.ELEMENT_PROTECTION.get()) {
				defElement.put( "fire", level*ServerConfig.getEnchantmentScaling()/5);
				defElement.put( "water", level*ServerConfig.getEnchantmentScaling()/5);
				defElement.put( "ice", level*ServerConfig.getEnchantmentScaling()/5);
				defElement.put( "thunder", level*ServerConfig.getEnchantmentScaling()/5);
			}

			// style enchantments
			if(ench == Enchantments.BLAST_PROTECTION) {
				defStyle.put("explosion", level*ServerConfig.getEnchantmentScaling());
			}
			else if(ench == Enchantments.PROJECTILE_PROTECTION) {
				defStyle.put("projectile", level*ServerConfig.getEnchantmentScaling());
			}
			else if(ench == EnchantmentList.MAGIC_PROTECTION.get()) {
				defStyle.put("magic", level*ServerConfig.getEnchantmentScaling());
			}
		}
		return new DefenseLayer(defStyle, defElement);
	}

	/**
	 * Returns a copy of the default {@link DefenseData} of a Biome at position {@link BlockPos}.
	 * @param world A World.
	 * @param position The BlockPos
	 * @return copy of DefenseData.
	 */
	public static DefenseLayer getDefenseLayer(World world, BlockPos position) {
		DefenseLayer defData = new DefenseLayer();
		Biome biome = world.getBiome(position);
		ResourceLocation rlBiome = biome.getRegistryName();
		if (rlBiome == null) {
			rlBiome = world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(biome);
		}
		if(rlBiome == null) { 
			return defData;
		}
		ResourceLocation rlProperties = new ResourceLocation(rlBiome.getNamespace(), "biomes/" + rlBiome.getPath()); ;
		BiomeCombatProperties property = new BiomeCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getBiomeDataFromLocation(rlProperties));
		defData.addElement(property.getDefenseElement());
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
		ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "mobs/" + rlEntity.getPath());
		return ElementalCombat.COMBAT_PROPERTIES_MANGER.getMobDataFromLocation(rlProperties).getBiomeDependency();
	}
}
