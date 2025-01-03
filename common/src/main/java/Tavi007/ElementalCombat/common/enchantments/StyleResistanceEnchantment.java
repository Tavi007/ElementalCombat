package Tavi007.ElementalCombat.common.enchantments;

import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.server.ServerConfigAccessors;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.HashMap;

public class StyleResistanceEnchantment extends Enchantment implements IResistanceEnchantment {

    public static final StyleResistanceEnchantment EXPLOSION_RESISTANCE = new StyleResistanceEnchantment(Type.EXPLOSION);
    public static final StyleResistanceEnchantment PROJECTILE_RESISTANCE = new StyleResistanceEnchantment(Type.PROJECTILE);

    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    public final StyleResistanceEnchantment.Type protectionType;

    public StyleResistanceEnchantment(StyleResistanceEnchantment.Type typeIn) {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, ARMOR_SLOTS);
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
        HashMap<String, Integer> defStyle = new HashMap<>();
        defStyle.put(protectionType.style, level * ServerConfigAccessors.getEnchantmentScalingStyle());
        return new DefenseLayer(defStyle, new HashMap<>());
    }

    public enum Type {

        PROJECTILE(10, 8, "projectile"),
        EXPLOSION(10, 8, "explosion"),
        MAGIC(10, 8, "magic");

        private final int minEnchantability;
        private final int levelCost;
        private final String style;

        Type(int minEnchantability, int levelCost, String style) {
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
