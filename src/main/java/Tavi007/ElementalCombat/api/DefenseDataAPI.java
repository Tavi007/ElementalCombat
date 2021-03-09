package Tavi007.ElementalCombat.api;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.api.defense.DefenseDataCapability;
import Tavi007.ElementalCombat.network.EntityMessage;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.network.PacketDistributor;

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

	/**
	 * Adds additional {@link DefenseData} to the DefenseData of the {@link LivingEntity}. The values of the style and element mappings will be summed up
	 * and a message will be send to the client.
	 * @param dataToAdd The additional DefenseData.
	 * @param livingEntity The LivingEntity. 
	 */
	public static void add(LivingEntity livingEntity, DefenseData dataToAdd) {
		if (dataToAdd.isEmpty()) return;
		DefenseData defData = get(livingEntity);
		AttackData atckData = AttackDataAPI.get(livingEntity);
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
	public static void add(ItemStack stack, DefenseData dataToAdd) {
		if (dataToAdd.isEmpty()) return;
		DefenseData defDataItem = get(stack);
		defDataItem.add(dataToAdd);
	}
	
	/**
	 * No set-method for DefenseData available, because it will only lead to synchronization problem down the line.
	 * Always try to apply a change relative to the current data.
	 */
}
