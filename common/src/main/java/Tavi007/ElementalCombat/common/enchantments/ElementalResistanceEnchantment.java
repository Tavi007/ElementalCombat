package Tavi007.ElementalCombat.common.enchantments;

import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.server.ServerConfigAccessors;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ElementalResistanceEnchantment extends Enchantment implements IResistanceEnchantment {

    public static final ElementalResistanceEnchantment FIRE_RESISTANCE = new ElementalResistanceEnchantment(Type.FIRE);

    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    public final ElementalResistanceEnchantment.Type protectionType;

    public ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type typeIn) {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, ARMOR_SLOTS);
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
        protectionType.elements
                .forEach(element -> defElement.put(element, (int) (level * ServerConfigAccessors.getEnchantmentScalingElement() * protectionType.scaling)));
        protectionType.antiElements
                .forEach(element -> defElement.put(element, (int) (-level * ServerConfigAccessors.getEnchantmentScalingElement() * protectionType.scalingAnti)));
        return new DefenseLayer(new HashMap<String, Integer>(), defElement);
    }

    public enum Type {

        FIRE("fire", 1.0f, "ice", 0.5f, 10, 8),
        ICE("ice", 1.0f, "fire", 0.5f, 10, 8),
        WATER("water", 1.0f, "thunder", 0.5f, 10, 8),
        THUNDER("thunder", 1.0f, "water", 0.5f, 10, 8),
        DARKNESS("darkness", 1.0f, "light", 0.5f, 10, 8),
        LIGHT("light", 1.0f, "darkness", 0.5f, 10, 8),
        EARTH("earth", 1.0f, "wind", 0.5f, 10, 8),
        WIND("wind", 1.0f, "earth", 0.5f, 10, 8),
        FLORA(10, 8, 1.0f, "flora"),

        ELEMENT(10, 8, 0.2f, "fire", "ice", "water", "thunder", "flora", "earth", "wind");

        private final int minEnchantability;
        private final int levelCost;
        private final List<String> elements;
        private final List<String> antiElements;
        private final float scaling;
        private final float scalingAnti;

        Type(String element, float scaling, String antiElement, float scalingAnti, int minEnchantability, int levelCost) {
            this.minEnchantability = minEnchantability;
            this.levelCost = levelCost;
            this.elements = Collections.singletonList(element);
            this.scaling = scaling;
            this.antiElements = Collections.singletonList(antiElement);
            this.scalingAnti = scalingAnti;
        }

        Type(int minEnchantability, int levelCost, float scaling, String... elements) {
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
