package Tavi007.ElementalCombat.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ElementalResistanceEnchantment extends Enchantment{

	private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};

	public final ElementalResistanceEnchantment.Type protectionType;

	public ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type typeIn) {
		super(Rarity.UNCOMMON, EnchantmentType.ARMOR, ARMOR_SLOTS);
		this.protectionType = typeIn;
	}

	/**
	 * Determines if the enchantment passed can be applyied together with this enchantment.
	 */
	protected boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof ElementalResistanceEnchantment);
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 4;
	}

	public static enum Type {
		FIRE(10, 8),
		ICE(10, 8),
		WATER(10, 8),
		THUNDER(10, 8),
		DARKNESS(10, 8),
		LIGHT(10, 8);

		private final int minEnchantability;
		private final int levelCost;

		private Type(int minEnchantability, int levelCost) {
			this.minEnchantability = minEnchantability;
			this.levelCost = levelCost;
		}

		public int getMinimalEnchantability() {
			return this.minEnchantability;
		}

		public int getEnchantIncreasePerLevel() {
			return this.levelCost;
		}
	}
}
