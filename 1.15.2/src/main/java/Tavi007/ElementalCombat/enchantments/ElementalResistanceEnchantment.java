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
		this.setRegistryName("elemental_resistance_" + typeIn.typeName);
		switch(typeIn.typeName) {
		case("fire"): 
			super.name = "Fire Resistance";
	    	break;
		case("ice"): 
			super.name = "Ice Resistance";
	    	break;
		case("water"): 
			super.name = "Water Resistance";
	    	break;
		case("thunder"): 
			super.name = "Thunder Resistance";
	    	break;
		}
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
		return 5;
	}

	public static enum Type {
		FIRE("fire", 10, 8),
		ICE("ice", 10, 8),
		WATER("water", 10, 8),
		THUNDER("thunder", 10, 8);

		private final String typeName;
		private final int minEnchantability;
		private final int levelCost;

		private Type(String typeName, int minEnchantability, int levelCost) {
			this.typeName = typeName;
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
