package Tavi007.ElementalCombat.init;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.enchantments.ElementalResistanceEnchantment;
import Tavi007.ElementalCombat.common.enchantments.ElementalWeaponEnchantment;
import Tavi007.ElementalCombat.common.enchantments.StyleResistanceEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentList {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ElementalCombat.MOD_ID);

    public static final RegistryObject<Enchantment> ICE_PROTECTION = ENCHANTMENTS.register(Constants.ICE_PROTECTION,
            () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.ICE));
    public static final RegistryObject<Enchantment> WATER_PROTECTION = ENCHANTMENTS.register(Constants.WATER_PROTECTION,
            () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.WATER));
    public static final RegistryObject<Enchantment> THUNDER_PROTECTION = ENCHANTMENTS.register(Constants.THUNDER_PROTECTION,
            () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.THUNDER));
    public static final RegistryObject<Enchantment> DARKNESS_PROTECTION = ENCHANTMENTS.register(Constants.DARKNESS_PROTECTION,
            () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.DARKNESS));
    public static final RegistryObject<Enchantment> LIGHT_PROTECTION = ENCHANTMENTS.register(Constants.LIGHT_PROTECTION,
            () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.LIGHT));
    public static final RegistryObject<Enchantment> EARTH_PROTECTION = ENCHANTMENTS.register(Constants.EARTH_PROTECTION,
            () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.EARTH));
    public static final RegistryObject<Enchantment> WIND_PROTECTION = ENCHANTMENTS.register(Constants.WIND_PROTECTION,
            () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.WIND));
    public static final RegistryObject<Enchantment> FLORA_PROTECTION = ENCHANTMENTS.register(Constants.FLORA_PROTECTION,
            () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.FLORA));

    public static final RegistryObject<Enchantment> ELEMENT_PROTECTION = ENCHANTMENTS.register(Constants.ELEMENT_PROTECTION,
            () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.ELEMENT));

    public static final RegistryObject<Enchantment> MAGIC_PROTECTION = ENCHANTMENTS.register(Constants.MAGIC_PROTECTION,
            () -> new StyleResistanceEnchantment(StyleResistanceEnchantment.Type.MAGIC));

    public static final RegistryObject<Enchantment> ICE_ASPECT = ENCHANTMENTS.register(Constants.ICE_PROTECTION,
            () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.ICE));
    public static final RegistryObject<Enchantment> WATER_ASPECT = ENCHANTMENTS.register(Constants.WATER_ASPECT,
            () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.WATER));
    public static final RegistryObject<Enchantment> THUNDER_ASPECT = ENCHANTMENTS.register(Constants.THUNDER_ASPECT,
            () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.THUNDER));
    public static final RegistryObject<Enchantment> DARKNESS_ASPECT = ENCHANTMENTS.register(Constants.DARKNESS_ASPECT,
            () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.DARKNESS));
    public static final RegistryObject<Enchantment> LIGHT_ASPECT = ENCHANTMENTS.register(Constants.LIGHT_ASPECT,
            () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.LIGHT));
    public static final RegistryObject<Enchantment> EARTH_ASPECT = ENCHANTMENTS.register(Constants.EARTH_ASPECT,
            () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.EARTH));
    public static final RegistryObject<Enchantment> WIND_ASPECT = ENCHANTMENTS.register(Constants.WIND_ASPECT,
            () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.WIND));
    public static final RegistryObject<Enchantment> FLORA_ASPECT = ENCHANTMENTS.register(Constants.FLORA_ASPECT,
            () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.FLORA));
}
