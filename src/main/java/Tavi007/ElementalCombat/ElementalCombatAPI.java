package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackDataCapability;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseDataCapability;
import Tavi007.ElementalCombat.loading.BiomeCombatProperties;
import Tavi007.ElementalCombat.loading.DamageSourceCombatProperties;
import Tavi007.ElementalCombat.loading.EntityCombatProperties;
import Tavi007.ElementalCombat.loading.ItemCombatProperties;
import Tavi007.ElementalCombat.network.DefenseDataMessage;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
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
		if (!attackData.areEnchantmentChangesApplied()) {
			attackData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(itemStack));
		}
		return attackData;
	}

	/*
	 * Returns the @return DefenseData of the @param itemStack.
	 */
	public static DefenseData getDefenseData(ItemStack itemStack){
		DefenseData defenseData = (DefenseData) itemStack.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
		if (!defenseData.areEnchantmentChangesApplied()) {
			defenseData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(itemStack));
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
		if (livingEntity instanceof ServerPlayerEntity) {
			DefenseDataMessage messageToClient = new DefenseDataMessage(dataToAdd, livingEntity.getUniqueID(), true);
			ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);
		}
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
	 * Returns a copy of the default @return BiomeCombatProperties of the @param biome.
	 */
	public static BiomeCombatProperties getDefaultProperties(Biome biome) {
		ResourceLocation rlBiome = biome.getRegistryName();
		ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "biomes/" + rlBiome.getNamespace() + "/" + rlBiome.getPath()); ;
		return new BiomeCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getBiomeDataFromLocation(rlProperties));
	}

	/*
	 * Returns a copy of the default @return DamageSourceCombatProperties of the @param damageSource.
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
	 * Returns a copy of the default @return EntityCombatProperties of the @param livingEntity.
	 */
	public static EntityCombatProperties getDefaultProperties(LivingEntity livingEntity) {
		ResourceLocation rlEntity = livingEntity.getType().getRegistryName();
		ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "entities/" + rlEntity.getNamespace() + "/" + rlEntity.getPath());
		return new EntityCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rlProperties));

	}

	/*
	 * Returns a copy of the default @return ItemCombatProperties of the @param itemStack.
	 */
	public static ItemCombatProperties getDefaultProperties(ItemStack itemStack) {
		ResourceLocation rlItem = itemStack.getItem().getRegistryName();
		ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "items/" + rlItem.getNamespace() + "/" + rlItem.getPath());
		return new ItemCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties));
	}
}
