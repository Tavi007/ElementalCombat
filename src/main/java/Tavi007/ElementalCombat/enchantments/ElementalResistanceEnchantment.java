package Tavi007.ElementalCombat.enchantments;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.config.ServerConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ElementalResistanceEnchantment extends Enchantment implements IResistanceEnchantment {

    private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS,
        EquipmentSlotType.FEET };

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

    @Override
    public DefenseLayer getDefenseLayer(int level) {
        HashMap<String, Integer> defElement = new HashMap<String, Integer>();
        protectionType.elements.forEach(element -> defElement.put(element, (int) (level * ServerConfig.getEnchantmentScalingStyle() * protectionType.scaling)));
        protectionType.antiElements
            .forEach(element -> defElement.put(element, (int) (-level * ServerConfig.getEnchantmentScalingStyle() * protectionType.scalingAnti)));
        return new DefenseLayer(new HashMap<String, Integer>(), defElement);
    }

    public static enum Type {

        FIRE("fire", 1.0f, "ice", 0.5f, 10, 8),
        ICE("ice", 1.0f, "fire", 0.5f, 10, 8),
        WATER("water", 1.0f, "thunder", 0.5f, 10, 8),
        THUNDER("thunder", 1.0f, "water", 0.5f, 10, 8),
        DARKNESS("darkness", 1.0f, "light", 0.5f, 10, 8),
        LIGHT("light", 1.0f, "darkness", 0.5f, 10, 8),

        ELEMENT(10, 8, 0.2f, "fire", "ice", "water", "thunder");

        private final int minEnchantability;
        private final int levelCost;

        private final List<String> elements;
        private final List<String> antiElements;
        private final float scaling;
        private final float scalingAnti;

        private Type(String element, float scaling, String antiElement, float scalingAnti, int minEnchantability, int levelCost) {
            this.minEnchantability = minEnchantability;
            this.levelCost = levelCost;
            this.elements = Arrays.asList(element);
            this.scaling = scaling;
            this.antiElements = Arrays.asList(antiElement);
            this.scalingAnti = scalingAnti;
        }

        private Type(int minEnchantability, int levelCost, float scaling, String... elements) {
            this.minEnchantability = minEnchantability;
            this.levelCost = levelCost;
            this.elements = Arrays.asList(elements);
            this.scaling = scaling;
            this.antiElements = Collections.emptyList();
            this.scalingAnti = 1.0f;
        }

        public int getMinimalEnchantability() {
            return this.minEnchantability;
        }

        public int getEnchantIncreasePerLevel() {
            return this.levelCost;
        }
    }
}
