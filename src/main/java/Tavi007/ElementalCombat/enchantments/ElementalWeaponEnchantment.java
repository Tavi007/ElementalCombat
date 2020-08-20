package Tavi007.ElementalCombat.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ElementalWeaponEnchantment extends Enchantment{

	private static final EquipmentSlotType[] WEAPON_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.MAINHAND};

	public final ElementalWeaponEnchantment.Type protectionType;

	public ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type typeIn) {
		super(Rarity.UNCOMMON, EnchantmentType.WEAPON, WEAPON_SLOTS);
		this.protectionType = typeIn;
		switch(typeIn.typeName) {
		case("ice"): 
			super.name = "Ice Aspect";
		break;
		case("water"): 
			super.name = "Water Aspect";
		break;
		case("thunder"):
			super.name = "Thunder Aspect";
		break;
		}
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	public int getMaxLevel() {
		return 2;
	}

	protected boolean canApplyTogether(Enchantment ench) {
		if(ench instanceof ElementalWeaponEnchantment) {
			return ((ElementalWeaponEnchantment) ench).protectionType == this.protectionType;
		}
		return false;
	
	}

	public static enum Type {
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
