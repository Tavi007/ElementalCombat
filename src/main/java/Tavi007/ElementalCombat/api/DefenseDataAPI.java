package Tavi007.ElementalCombat.api;

import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.api.defense.DefenseDataCapability;
import Tavi007.ElementalCombat.api.defense.DefenseLayer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DefenseDataAPI {

	/**
	 * Returns the defense-combat data {@link DefenseData} of the {@link LivingEntity}.
	 * @param entity A LivingEntity.
	 * @return the DefenseData, containing the style defense-mapping and element defense-mapping.
	 */
	public static DefenseData get(LivingEntity entity) {
		DefenseData defenseData = (DefenseData) entity.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
		if(!defenseData.isInitialized()) {
			defenseData.initialize(entity);
		}
		return defenseData;
	}


	/**
	 * Returns the defense-combat data {@link DefenseData} of the {@link ItemStack}.
	 * @param stack An ItemStack.
	 * @return the DefenseData, containing the style defense-mapping and element defense-mapping.
	 */
	public static DefenseData get(ItemStack stack) {
		if(stack.isEmpty()) {
			return new DefenseData();
		}
		else {
			DefenseData defenseData = (DefenseData) stack.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
			if(!defenseData.isInitialized()) {
				defenseData.initialize(stack);
			}
			if (!defenseData.areEnchantmentChangesApplied()) {
				defenseData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
			}
			return defenseData;
		}
	}

	/////////////////////
	// Helperfunctions //
	/////////////////////
	
	public static void putLayer(LivingEntity livingEntity, DefenseLayer dataToAdd, ResourceLocation location) {
		DefenseData defData = get(livingEntity);
		defData.putLayer(location, dataToAdd);
		if(livingEntity.isServerWorld()) {
			NetworkAPI.syncDefenseLayerMessageForClients(livingEntity, dataToAdd, location);
		}
	}

	/**
	 * Adds additional {@link DefenseData} to the DefenseData of the {@link ItemStack}. The values of the style and element mappings will be summed up.
	 * @param dataToAdd The additional DefenseData.
	 * @param stack The ItemStack. 
	 */
	public static void putLayer(ItemStack stack, DefenseLayer dataToAdd, ResourceLocation location) {
		DefenseData defDataItem = get(stack);
		defDataItem.putLayer(location, dataToAdd);
	}
}
