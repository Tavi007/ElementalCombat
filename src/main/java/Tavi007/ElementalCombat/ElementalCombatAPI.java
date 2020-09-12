package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackDataCapability;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseDataCapability;
import Tavi007.ElementalCombat.loading.BiomeCombatProperties;
import Tavi007.ElementalCombat.loading.DamageSourceCombatProperties;
import Tavi007.ElementalCombat.loading.EntityCombatProperties;
import Tavi007.ElementalCombat.loading.ItemCombatProperties;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public class ElementalCombatAPI 
{

	//LivingEntity
	public static AttackData getAttackData(LivingEntity entity){
		return (AttackData) entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
	}

	public static DefenseData getDefenseData(LivingEntity entity){
		return (DefenseData) entity.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
	}

	//ItemStack
	public static AttackData getAttackData(ItemStack item){
		AttackData attackData = (AttackData) item.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
		if(!attackData.areEnchantmentsApplied() && !(item.getItem() instanceof EnchantedBookItem)) {
			attackData.applyEnchantments(EnchantmentHelper.getEnchantments(item));
		}
		return attackData;
	}

	public static DefenseData getDefenseData(ItemStack item){
		DefenseData defenseData = (DefenseData) item.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
		if(!defenseData.areEnchantmentsApplied() && !(item.getItem() instanceof EnchantedBookItem)) {
			defenseData.applyEnchantments(EnchantmentHelper.getEnchantments(item));
			}
		return defenseData;
	}

	//Projectiles
	public static AttackData getAttackData(ProjectileEntity entity){
		return (AttackData) entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
	}
	
	//get default values
	public static BiomeCombatProperties getDefaultProperties(Biome biome) {
		ResourceLocation rlBiome = biome.getRegistryName();
		ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "biomes/" + rlBiome.getNamespace() + "/" + rlBiome.getPath()); ;
		return new BiomeCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getBiomeDataFromLocation(rlProperties));
	}

	public static DamageSourceCombatProperties getDefaultProperties(DamageSource source) {
		ResourceLocation rlDamageSource=null;
		// do other mods implement their own natural damageSource? If so, how could I get the mod id from it?
		// for now do not use Namespace.
		if(source.isExplosion()) {
			rlDamageSource = new ResourceLocation(ElementalCombat.MOD_ID, "damage_sources/explosion");
		}
		else if(source.isMagicDamage()) {
			rlDamageSource = new ResourceLocation(ElementalCombat.MOD_ID, "damage_sources/magic");
		}
		else {
			rlDamageSource = new ResourceLocation(ElementalCombat.MOD_ID, "damage_sources/" + source.getDamageType().toLowerCase());
		}
		return new DamageSourceCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getDamageSourceDataFromLocation(rlDamageSource));
		
	}

	public static EntityCombatProperties getDefaultProperties(LivingEntity entity) {
		ResourceLocation rlEntity = entity.getType().getRegistryName();
		ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "entities/" + rlEntity.getNamespace() + "/" + rlEntity.getPath());
		return new EntityCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rlProperties));

	}

	public static ItemCombatProperties getDefaultProperties(ItemStack item) {
		ResourceLocation rlItem = item.getItem().getRegistryName();
		ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "items/" + rlItem.getNamespace() + "/" + rlItem.getPath());
		return new ItemCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties));
	}
}
