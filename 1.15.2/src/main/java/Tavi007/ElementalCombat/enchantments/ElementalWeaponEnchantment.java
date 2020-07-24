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
		this.setRegistryName("elemental_attack_" + typeIn.typeName);
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
