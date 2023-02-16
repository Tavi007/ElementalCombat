package Tavi007.ElementalCombat.enchantments;

import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ElementalWeaponEnchantment extends Enchantment {

    private static final EquipmentSlotType[] WEAPON_SLOTS = new EquipmentSlotType[] { EquipmentSlotType.MAINHAND };

    public final ElementalWeaponEnchantment.Type protectionType;

    public ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type typeIn) {
        super(Rarity.UNCOMMON, EnchantmentType.WEAPON, WEAPON_SLOTS);
        this.protectionType = typeIn;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel() {
        return 1;
    }

    public AttackLayer getAttackLayer() {
        AttackLayer layer = new AttackLayer();
        layer.setElement(protectionType.element);
        return layer;
    }

    protected boolean canApplyTogether(Enchantment ench) {
        if (ench instanceof ElementalWeaponEnchantment) {
            return ((ElementalWeaponEnchantment) ench).protectionType == this.protectionType;
        }
        return false;

    }

    public static enum Type {

        ICE(10, 8, "ice"),
        WATER(10, 8, "water"),
        THUNDER(10, 8, "thunder"),
        DARKNESS(10, 8, "darkness"),
        LIGHT(10, 8, "light");

        private final int minEnchantability;
        private final int levelCost;
        private final String element;

        private Type(int minEnchantability, int levelCost, String element) {
            this.minEnchantability = minEnchantability;
            this.levelCost = levelCost;
            this.element = element;
        }

        public int getMinimalEnchantability() {
            return this.minEnchantability;
        }

        public int getEnchantIncreasePerLevel() {
            return this.levelCost;
        }
    }
}
