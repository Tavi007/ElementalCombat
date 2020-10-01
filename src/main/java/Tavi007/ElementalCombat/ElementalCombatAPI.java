package Tavi007.ElementalCombat;

import java.util.HashMap;
import java.util.Map;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackDataCapability;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseDataCapability;
import Tavi007.ElementalCombat.enchantments.CombatEnchantments;
import Tavi007.ElementalCombat.loading.BiomeCombatProperties;
import Tavi007.ElementalCombat.loading.DamageSourceCombatProperties;
import Tavi007.ElementalCombat.loading.EntityCombatProperties;
import Tavi007.ElementalCombat.loading.ItemCombatProperties;
import Tavi007.ElementalCombat.network.DefenseDataMessageToClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.network.PacketDistributor;

public class ElementalCombatAPI 
{

	///////////////////
	// Living Entity //
	///////////////////
	
	/*
	 * Returns the @return AttackData of the @param livingEntity.
	 */
	public static AttackData getAttackData(LivingEntity entity){
		return (AttackData) entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
	}

	/*
	 * Returns the @return DefenseData of the @param livingEntity.
	 */
	public static DefenseData getDefenseData(LivingEntity entity){
		return (DefenseData) entity.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
	}

	///////////////
	// ItemStack //
	///////////////

	/*
	 * Returns the @return AttackData of the @param ItemStack.
	 */
	public static AttackData getAttackData(ItemStack itemStack){
		AttackData attackData = (AttackData) itemStack.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
		return attackData;
	}

	/*
	 * Returns the @return DefenseData of the @param itemStack.
	 */
	public static DefenseData getDefenseData(ItemStack itemStack){
		DefenseData defenseData = (DefenseData) itemStack.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
		return defenseData;
	}

	public static AttackData getAttackDataWithEnchantment(ItemStack itemStack){
		AttackData attackData = new AttackData((AttackData) itemStack.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData()));

		if (!(itemStack.getItem() instanceof EnchantedBookItem)) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
			enchantments.forEach((key, value) -> {

				//currently only comparing strings.
				//maybe change to resourceLocation later, so other mods can interact with this as well.
				//sword
				if(key.getName() == Enchantments.FIRE_ASPECT.getName()) {attackData.setElement("fire");}
				if(key.getName() == CombatEnchantments.ICE_ASPECT.getName()) {attackData.setElement("ice");}
				if(key.getName() == CombatEnchantments.WATER_ASPECT.getName()) {attackData.setElement("water");}
				if(key.getName() == CombatEnchantments.THUNDER_ASPECT.getName()) {attackData.setElement("thunder");}
				//bow
				if(key.getName() == Enchantments.FLAME.getName()) {attackData.setElement("fire");}
				//trident
				if(key.getName() == Enchantments.CHANNELING.getName()) {attackData.setElement("thunder");}
			});
		}
		return attackData;
	}

	public static DefenseData getDefenseDataWithEnchantment(ItemStack itemStack){
		DefenseData defenseData = new DefenseData((DefenseData) itemStack.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData()));

		if (!(itemStack.getItem() instanceof EnchantedBookItem)) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
			HashMap<String, Integer> defStyle = new HashMap<String, Integer>();
			HashMap<String, Integer> defElement = new HashMap<String, Integer>();
			enchantments.forEach((key, level) -> {
				//currently only comparing strings.
				//maybe change to resourceLocation later, so other mods can interact with this as well.

				// elemental enchantments
				if(key.getName() == CombatEnchantments.ICE_RESISTANCE.getName()) {
					defElement.put("ice", level*ElementalCombat.SCALE_ENCHANTMENT);
					defElement.put("fire", -level*ElementalCombat.SCALE_ENCHANTMENT);
				}
				else if(key.getName() == CombatEnchantments.FIRE_RESISTANCE.getName()) {
					defElement.put( "fire", level*ElementalCombat.SCALE_ENCHANTMENT);
					defElement.put( "ice", -level*ElementalCombat.SCALE_ENCHANTMENT);
				}
				else if(key.getName() == CombatEnchantments.WATER_RESISTANCE.getName()) {
					defElement.put( "water", level*ElementalCombat.SCALE_ENCHANTMENT);
					defElement.put( "thunder", -level*ElementalCombat.SCALE_ENCHANTMENT);
				}
				else if(key.getName() == CombatEnchantments.THUNDER_RESISTANCE.getName()) {
					defElement.put( "thunder", level*ElementalCombat.SCALE_ENCHANTMENT);
					defElement.put( "water", -level*ElementalCombat.SCALE_ENCHANTMENT);
				}

				// style enchantments
				if(key.getName() == CombatEnchantments.BLAST_PROTECTION.getName()) {
					defStyle.put("explosion", level*ElementalCombat.SCALE_ENCHANTMENT);
				}
				else if(key.getName() == CombatEnchantments.PROJECTILE_PROTECTION.getName()) {
					defStyle.put("projectile", level*ElementalCombat.SCALE_ENCHANTMENT);
				}
			});

			DefenseData defenseDataEnchantment = new DefenseData(defStyle, defElement);
			defenseDataEnchantment.add(defenseData);
			return defenseDataEnchantment;
		}
		return defenseData;
	}


	/////////////////
	// Projectiles //
	/////////////////

	/*
	 * Returns the @return AttackData of the @param projectileEntity.
	 */
	public static AttackData getAttackData(ProjectileEntity projectileEntity){
		return (AttackData) projectileEntity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
	}


	/////////////////////
	// Helperfunctions //
	/////////////////////

	/*
	 * adds @param dataToAdd to the DefenseData of the @param livingEntity 
	 */
	public static void addDefenseData(LivingEntity livingEntity, DefenseData dataToAdd) {
		if (dataToAdd.isEmpty()) return;
		DefenseData defDataEntity = ElementalCombatAPI.getDefenseData(livingEntity);
		defDataEntity.add(dataToAdd);
		DefenseDataMessageToClient messageToClient = new DefenseDataMessageToClient(dataToAdd, livingEntity.getUniqueID());
		ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);

	}

	/*
	 * adds @param dataToAdd to the DefenseData of the @param itemStack 
	 */
	public static void addDefenseData(ItemStack itemStack, DefenseData dataToAdd) {
		if (dataToAdd.isEmpty()) return;
		DefenseData defDataItem = ElementalCombatAPI.getDefenseData(itemStack);
		defDataItem.add(dataToAdd);
	}

	////////////////////////
	// get default values //
	////////////////////////

	/*
	 * Returns the default @return BiomeCombatProperties of the @param biome.
	 */
	public static BiomeCombatProperties getDefaultProperties(Biome biome) {
		ResourceLocation rlBiome = biome.getRegistryName();
		ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "biomes/" + rlBiome.getNamespace() + "/" + rlBiome.getPath()); ;
		return new BiomeCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getBiomeDataFromLocation(rlProperties));
	}

	/*
	 * Returns the default @return DamageSourceCombatProperties of the @param damageSource.
	 */
	public static DamageSourceCombatProperties getDefaultProperties(DamageSource damageSource) {
		ResourceLocation rlDamageSource=null;
		// do other mods implement their own natural damageSource? If so, how could I get the mod id from it?
		// for now do not use Namespace.
		if(damageSource.isExplosion()) {
			rlDamageSource = new ResourceLocation(ElementalCombat.MOD_ID, "damage_sources/explosion");
		}
		else if(damageSource.isMagicDamage()) {
			rlDamageSource = new ResourceLocation(ElementalCombat.MOD_ID, "damage_sources/magic");
		}
		else {
			rlDamageSource = new ResourceLocation(ElementalCombat.MOD_ID, "damage_sources/" + damageSource.getDamageType().toLowerCase());
		}
		return new DamageSourceCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getDamageSourceDataFromLocation(rlDamageSource));

	}

	/*
	 * Returns the default @return EntityCombatProperties of the @param livingEntity.
	 */
	public static EntityCombatProperties getDefaultProperties(LivingEntity livingEntity) {
		ResourceLocation rlEntity = livingEntity.getType().getRegistryName();
		ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "entities/" + rlEntity.getNamespace() + "/" + rlEntity.getPath());
		return new EntityCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rlProperties));

	}

	/*
	 * Returns the default @return ItemCombatProperties of the @param itemStack.
	 */
	public static ItemCombatProperties getDefaultProperties(ItemStack itemStack) {
		ResourceLocation rlItem = itemStack.getItem().getRegistryName();
		ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "items/" + rlItem.getNamespace() + "/" + rlItem.getPath());
		return new ItemCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties));
	}
}
