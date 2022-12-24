package Tavi007.ElementalCombat.init;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.enchantments.ElementalResistanceEnchantment;
import Tavi007.ElementalCombat.enchantments.ElementalWeaponEnchantment;
import Tavi007.ElementalCombat.enchantments.StyleResistanceEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentList {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ElementalCombat.MOD_ID);

    public static final RegistryObject<Enchantment> ICE_PROTECTION = ENCHANTMENTS.register("ice_protection",
        () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.ICE));
    public static final RegistryObject<Enchantment> WATER_PROTECTION = ENCHANTMENTS.register("water_protection",
        () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.WATER));
    public static final RegistryObject<Enchantment> THUNDER_PROTECTION = ENCHANTMENTS.register("thunder_protection",
        () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.THUNDER));
    public static final RegistryObject<Enchantment> DARKNESS_PROTECTION = ENCHANTMENTS.register("darkness_protection",
        () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.DARKNESS));
    public static final RegistryObject<Enchantment> LIGHT_PROTECTION = ENCHANTMENTS.register("light_protection",
        () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.LIGHT));

    public static final RegistryObject<Enchantment> ELEMENT_PROTECTION = ENCHANTMENTS.register("element_protection",
        () -> new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.ELEMENT));

    public static final RegistryObject<Enchantment> MAGIC_PROTECTION = ENCHANTMENTS.register("magic_protection",
        () -> new StyleResistanceEnchantment(StyleResistanceEnchantment.Type.MAGIC));

    public static final RegistryObject<Enchantment> ICE_ASPECT = ENCHANTMENTS.register("ice_aspect",
        () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.ICE));
    public static final RegistryObject<Enchantment> WATER_ASPECT = ENCHANTMENTS.register("water_aspect",
        () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.WATER));
    public static final RegistryObject<Enchantment> THUNDER_ASPECT = ENCHANTMENTS.register("thunder_aspect",
        () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.THUNDER));
    public static final RegistryObject<Enchantment> DARKNESS_ASPECT = ENCHANTMENTS.register("darkness_aspect",
        () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.DARKNESS));
    public static final RegistryObject<Enchantment> LIGHT_ASPECT = ENCHANTMENTS.register("light_aspect",
        () -> new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.LIGHT));

    @SubscribeEvent
    public static void registerEnchantments(Register<Enchantment> event) {
        // overwriting of vanilla enchantments
        event.getRegistry()
            .register(
                new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.FIRE).setRegistryName(Enchantments.FIRE_PROTECTION.getRegistryName()));
        event.getRegistry()
            .register(new StyleResistanceEnchantment(StyleResistanceEnchantment.Type.PROJECTILE)
                .setRegistryName(Enchantments.PROJECTILE_PROTECTION.getRegistryName()));
        event.getRegistry()
            .register(
                new StyleResistanceEnchantment(StyleResistanceEnchantment.Type.EXPLOSION).setRegistryName(Enchantments.BLAST_PROTECTION.getRegistryName()));
    }
}
