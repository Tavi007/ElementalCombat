package Tavi007.ElementalCombat.enchantments;

import java.util.HashMap;

import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.config.ServerConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class StyleResistanceEnchantment extends Enchantment implements IResistanceEnchantment {

    public static final StyleResistanceEnchantment EXPLOSION_RESISTANCE = new StyleResistanceEnchantment(Type.EXPLOSION);
    public static final StyleResistanceEnchantment PROJECTILE_RESISTANCE = new StyleResistanceEnchantment(Type.PROJECTILE);

    private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS,
        EquipmentSlotType.FEET };

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

    @Override
    public DefenseLayer getDefenseLayer(int level) {
        HashMap<String, Integer> defStyle = new HashMap<String, Integer>();
        defStyle.put(protectionType.style, level * ServerConfig.getEnchantmentScalingStyle());
        return new DefenseLayer(defStyle, new HashMap<String, Integer>());
    }

    public static enum Type {

        PROJECTILE(10, 8, "projectile"),
        EXPLOSION(10, 8, "explosion"),
        MAGIC(10, 8, "magic");

        private final int minEnchantability;
        private final int levelCost;
        private final String style;

        private Type(int minEnchantability, int levelCost, String style) {
            this.minEnchantability = minEnchantability;
            this.levelCost = levelCost;
            this.style = style;
        }

        public int getMinimalEnchantability() {
            return this.minEnchantability;
        }

        public int getEnchantIncreasePerLevel() {
            return this.levelCost;
        }
    }
}
