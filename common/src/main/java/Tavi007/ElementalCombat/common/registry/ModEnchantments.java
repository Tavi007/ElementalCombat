package Tavi007.ElementalCombat.common.registry;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.enchantments.ElementalResistanceEnchantment;
import Tavi007.ElementalCombat.common.enchantments.ElementalWeaponEnchantment;
import Tavi007.ElementalCombat.common.enchantments.StyleResistanceEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ModEnchantments {
    private static final Map<String, Enchantment> registry = new HashMap<>();
    public static final Enchantment ICE_PROTECTION = register(Constants.ICE_PROTECTION, new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.ICE));
    public static final Enchantment WATER_PROTECTION = register(Constants.WATER_PROTECTION, new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.WATER));
    public static final Enchantment THUNDER_PROTECTION = register(Constants.THUNDER_PROTECTION, new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.THUNDER));
    public static final Enchantment DARKNESS_PROTECTION = register(Constants.DARKNESS_PROTECTION, new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.DARKNESS));
    public static final Enchantment LIGHT_PROTECTION = register(Constants.LIGHT_PROTECTION, new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.LIGHT));
    public static final Enchantment EARTH_PROTECTION = register(Constants.EARTH_PROTECTION, new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.EARTH));
    public static final Enchantment WIND_PROTECTION = register(Constants.WIND_PROTECTION, new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.WIND));
    public static final Enchantment FLORA_PROTECTION = register(Constants.FLORA_PROTECTION, new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.FLORA));
    public static final Enchantment ELEMENT_PROTECTION = register(Constants.ELEMENT_PROTECTION, new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.ELEMENT));
    public static final Enchantment MAGIC_PROTECTION = register(Constants.MAGIC_PROTECTION, new StyleResistanceEnchantment(StyleResistanceEnchantment.Type.MAGIC));
    public static final Enchantment ICE_ASPECT = register(Constants.ICE_PROTECTION, new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.ICE));
    public static final Enchantment WATER_ASPECT = register(Constants.WATER_ASPECT, new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.WATER));
    public static final Enchantment THUNDER_ASPECT = register(Constants.THUNDER_ASPECT, new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.THUNDER));
    public static final Enchantment DARKNESS_ASPECT = register(Constants.DARKNESS_ASPECT, new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.DARKNESS));
    public static final Enchantment LIGHT_ASPECT = register(Constants.LIGHT_ASPECT, new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.LIGHT));
    public static final Enchantment EARTH_ASPECT = register(Constants.EARTH_ASPECT, new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.EARTH));
    public static final Enchantment WIND_ASPECT = register(Constants.WIND_ASPECT, new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.WIND));
    public static final Enchantment FLORA_ASPECT = register(Constants.FLORA_ASPECT, new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.FLORA));

    private static Enchantment register(String name, Enchantment enchantment) {
        registry.put(name, enchantment);
        return enchantment;
    }

    public static void register(BiConsumer<String, Enchantment> registerConsumer) {
        registry.forEach((name, enchantment) -> registerConsumer.accept(name, enchantment));
    }
}
