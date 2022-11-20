package Tavi007.ElementalCombat.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ElementalWeaponEnchantment extends Enchantment {

    private static final EquipmentSlot[] WEAPON_SLOTS = new EquipmentSlot[] {
        EquipmentSlot.MAINHAND
    };

    public final ElementalWeaponEnchantment.Type protectionType;

    public ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type typeIn) {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON, WEAPON_SLOTS);
        this.protectionType = typeIn;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel() {
        return 1;
    }

    protected boolean canApplyTogether(Enchantment ench) {
        if (ench instanceof ElementalWeaponEnchantment) {
            return ((ElementalWeaponEnchantment) ench).protectionType == this.protectionType;
        }
        return false;

    }

    public static enum Type {

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
