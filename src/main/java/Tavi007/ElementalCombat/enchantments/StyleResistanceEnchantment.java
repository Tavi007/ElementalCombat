package Tavi007.ElementalCombat.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class StyleResistanceEnchantment extends Enchantment{

	private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};

	public final StyleResistanceEnchantment.Type protectionType;

	public StyleResistanceEnchantment(StyleResistanceEnchantment.Type typeIn) {
		super(Rarity.UNCOMMON, EnchantmentType.ARMOR, ARMOR_SLOTS);
		this.protectionType = typeIn;
	}

	/**
	 * Determines if the enchantment passed can be applyied together with this enchantment.
	 */
	protected boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof StyleResistanceEnchantment);
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 4;
	}

	public static enum Type {
		PROJECTILE(10, 8),
		EXPLOSION(10, 8),
		MAGIC(10, 8);

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
