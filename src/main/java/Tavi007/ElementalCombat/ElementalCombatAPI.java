package Tavi007.ElementalCombat;

import org.apache.commons.lang3.text.WordUtils;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackDataCapability;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseDataCapability;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.loading.BiomeCombatProperties;
import Tavi007.ElementalCombat.loading.DamageSourceCombatProperties;
import Tavi007.ElementalCombat.loading.EntityCombatProperties;
import Tavi007.ElementalCombat.loading.ItemCombatProperties;
import Tavi007.ElementalCombat.network.EntityMessage;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.network.PacketDistributor;

@SuppressWarnings("deprecation")
public class ElementalCombatAPI 
{

	///////////////////
	// Living Entity //
	///////////////////

	/**
	 * Returns the attack-combat data {@link AttackData} of the {@link LivingEntity}.
	 * @param entity A LivingEntity.
	 * @return the AttackData, containing the attack style and attack element.
	 */
	public static AttackData getAttackData(LivingEntity entity) {
		return (AttackData) entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
	}

	/**
	 * Returns the attack-combat data {@link AttackData} of the {@link LivingEntity}, but the held itemstack data will be applied aswell. 
	 * @param entity A LivingEntity.
	 * @return the AttackData, containing the attack style and attack element.
	 */
	public static AttackData getAttackDataWithActiveItem(LivingEntity entity) {
		AttackData atckData = new AttackData();
		if(entity.getHeldItemMainhand().isEmpty()){
			//use data from livingEntity
			atckData.set(ElementalCombatAPI.getAttackData(entity));
		}
		else {
			//use data from held item
			atckData.set(ElementalCombatAPI.getAttackData(entity.getHeldItemMainhand()));

			//maybe mix and match with entity data? a wither skeleton will only use data from the stone sword...
			AttackData atckDataEntity = ElementalCombatAPI.getAttackData(entity);
			if (atckData.getStyle().equals(ServerConfig.getDefaultStyle())) {
				atckData.setStyle(atckDataEntity.getStyle());
			}
			if (atckData.getElement().equals(ServerConfig.getDefaultElement())) {
				atckData.setElement(atckDataEntity.getElement());
			}
		}
		return atckData;
	}

	/**
	 * Returns the defense-combat data {@link DefenseData} of the {@link LivingEntity}.
	 * @param entity A LivingEntity.
	 * @return the DefenseData, containing the style defense-mapping and element defense-mapping.
	 */
	public static DefenseData getDefenseData(LivingEntity entity) {
		return (DefenseData) entity.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
	}

	///////////////
	// ItemStack //
	///////////////


	/**
	 * Returns the attack-combat data {@link AttackData} of the {@link ItemStack}.
	 * @param stack An ItemStack.
	 * @return the AttackData, containing the attack style and attack element.
	 */
	public static AttackData getAttackData(ItemStack stack) {
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


	/**
	 * Returns the defense-combat data {@link DefenseData} of the {@link ItemStack}.
	 * @param stack An ItemStack.
	 * @return the DefenseData, containing the style defense-mapping and element defense-mapping.
	 */
	public static DefenseData getDefenseData(ItemStack stack) {
		if(stack.isEmpty()) {
			return new DefenseData();
		}
		else {
			DefenseData defenseData = (DefenseData) stack.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
			if (!defenseData.areEnchantmentChangesApplied()) {
				defenseData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
			}
			return defenseData;
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
	public static AttackData getAttackData(ProjectileEntity projectileEntity) {
		return (AttackData) projectileEntity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
	}


	/////////////////////
	// Helperfunctions //
	/////////////////////

	/**
	 * Adds additional {@link DefenseData} to the DefenseData of the {@link LivingEntity}. The values of the style and element mappings will be summed up
	 * and a message will be send to the client.
	 * @param dataToAdd The additional DefenseData.
	 * @param livingEntity The LivingEntity. 
	 */
	public static void addDefenseData(LivingEntity livingEntity, DefenseData dataToAdd) {
		if (dataToAdd.isEmpty()) return;
		DefenseData defData = ElementalCombatAPI.getDefenseData(livingEntity);
		AttackData atckData = ElementalCombatAPI.getAttackData(livingEntity);
		defData.add(dataToAdd);

		if (livingEntity instanceof ServerPlayerEntity) {
			EntityMessage messageToClient = new EntityMessage(atckData, dataToAdd, true, livingEntity.getEntityId());
			ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);
		}
	}

	/**
	 * Adds additional {@link DefenseData} to the DefenseData of the {@link ItemStack}. The values of the style and element mappings will be summed up.
	 * @param dataToAdd The additional DefenseData.
	 * @param stack The ItemStack. 
	 */
	public static void addDefenseData(ItemStack stack, DefenseData dataToAdd) {
		if (dataToAdd.isEmpty()) return;
		DefenseData defDataItem = ElementalCombatAPI.getDefenseData(stack);
		defDataItem.add(dataToAdd);
	}


	////////////////////////
	// get default values //
	////////////////////////

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

	/**
	 * Returns a copy of the default {@link EntityCombatProperties} of any {@link LivingEntity}.
	 * @param livingEntity The LivingEntity.
	 * @return copy of EntityCombatProperties.
	 */
	public static EntityCombatProperties getDefaultProperties(LivingEntity livingEntity) {
		ResourceLocation rlEntity = livingEntity.getType().getRegistryName();
		if (rlEntity == null) {
			return new EntityCombatProperties();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "entities/" + rlEntity.getPath());
		return new EntityCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rlProperties));

	}

	/**
	 * Returns a copy of the default {@link ItemCombatProperties} of any {@link ItemStack}.
	 * @param stack The ItemStack.
	 * @return copy of ItemCombatProperties.
	 */
	public static ItemCombatProperties getDefaultProperties(ItemStack stack) {
		ResourceLocation rlItem = stack.getItem().getRegistryName();
		if (rlItem == null) {
			return new ItemCombatProperties();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlItem.getNamespace(), "items/" + rlItem.getPath());
		return new ItemCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties));
	}

	/**
	 * Returns a copy of the default {@link BiomeCombatProperties} of any {@link Biome}.
	 * @param biome The Biome.
	 * @return copy of BiomeCombatProperties.
	 */
	public static BiomeCombatProperties getDefaultProperties(Biome biome) {
		ResourceLocation rlBiome = biome.getRegistryName();
		if (rlBiome == null) {
			return new BiomeCombatProperties();
		}
		ResourceLocation rlProperties = new ResourceLocation(rlBiome.getNamespace(), "biomes/" + rlBiome.getPath()); ;
		return new BiomeCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getBiomeDataFromLocation(rlProperties));
	}

	/**
	 * Returns a copy of the default {@link DamageSourceCombatProperties} of any {@link DamageSource}. 
	 * This includes lightning, burning, drowning, suffocating in a wall and so on.
	 * @param damageSource The DamageSource.
	 * @return copy of DamageSourceCombatProperties.
	 */
	public static DamageSourceCombatProperties getDefaultProperties(DamageSource damageSource) {
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
		return new DamageSourceCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getDamageSourceDataFromLocation(rlDamageSource));
	}
}
