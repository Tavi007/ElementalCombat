package Tavi007.ElementalCombat.init;

import java.util.Arrays;
import java.util.Collections;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.potions.ElementalResistanceEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionList {

    public static final DeferredRegister<MobEffect> POTION_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ElementalCombat.MOD_ID);
    public static final MobEffect FIRE_RESISTANCE_EFFECT = new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 14981690, "fire", "ice");
    public static final RegistryObject<MobEffect> ICE_RESISTANCE_EFFECT = POTION_EFFECTS.register("ice_resistance",
        () -> new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 9158640, "ice", "fire"));
    public static final RegistryObject<MobEffect> THUNDER_RESISTANCE_EFFECT = POTION_EFFECTS.register("thunder_resistance",
        () -> new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 14735695, "thunder", "water"));
    public static final RegistryObject<MobEffect> WATER_RESISTANCE_EFFECT = POTION_EFFECTS.register("water_resistance",
        () -> new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 2636728, "water", "thunder"));
    public static final RegistryObject<MobEffect> DARKNESS_RESISTANCE_EFFECT = POTION_EFFECTS.register("darkness_resistance",
        () -> new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 2821688, "darkness", "light"));
    public static final RegistryObject<MobEffect> LIGHT_RESISTANCE_EFFECT = POTION_EFFECTS.register("light_resistance",
        () -> new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 15856307, "light", "darkness"));
    public static final RegistryObject<MobEffect> FLORA_RESISTANCE_EFFECT = POTION_EFFECTS.register("flora_resistance",
        () -> new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 11335061, Arrays.asList("flora"), Collections.emptyList()));
    public static final RegistryObject<MobEffect> WIND_RESISTANCE_EFFECT = POTION_EFFECTS.register("wind_resistance",
        () -> new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 9285299, "wind", "earth"));
    public static final RegistryObject<MobEffect> EARTH_RESISTANCE_EFFECT = POTION_EFFECTS.register("earth_resistance",
        () -> new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 5854011, "earth", "wind"));

    public static final DeferredRegister<Potion> POTION_TYPES = DeferredRegister.create(ForgeRegistries.POTIONS, ElementalCombat.MOD_ID);
    public static final RegistryObject<Potion> FIRE_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_fire_resistance",
        () -> new Potion("strong_fire_resistance", new MobEffectInstance(FIRE_RESISTANCE_EFFECT, 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> ICE_RESISTANCE_POTION = POTION_TYPES.register("ice_resistance",
        () -> new Potion("ice_resistance", new MobEffectInstance(ICE_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> ICE_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_ice_resistance",
        () -> new Potion("long_ice_resistance", new MobEffectInstance(ICE_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> ICE_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_ice_resistance",
        () -> new Potion("strong_ice_resistance", new MobEffectInstance(ICE_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> THUNDER_RESISTANCE_POTION = POTION_TYPES.register("thunder_resistance",
        () -> new Potion("thunder_resistance", new MobEffectInstance(THUNDER_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> THUNDER_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_thunder_resistance",
        () -> new Potion("long_thunder_resistance", new MobEffectInstance(THUNDER_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> THUNDER_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_thunder_resistance",
        () -> new Potion("strong_thunder_resistance", new MobEffectInstance(THUNDER_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> WATER_RESISTANCE_POTION = POTION_TYPES.register("water_resistance",
        () -> new Potion("water_resistance", new MobEffectInstance(WATER_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> WATER_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_water_resistance",
        () -> new Potion("long_water_resistance", new MobEffectInstance(WATER_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> WATER_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_water_resistance",
        () -> new Potion("strong_water_resistance", new MobEffectInstance(WATER_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> DARKNESS_RESISTANCE_POTION = POTION_TYPES.register("darkness_resistance",
        () -> new Potion("darkness_resistance", new MobEffectInstance(DARKNESS_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> DARKNESS_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_darkness_resistance",
        () -> new Potion("long_darkness_resistance", new MobEffectInstance(DARKNESS_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> DARKNESS_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_darkness_resistance",
        () -> new Potion("strong_darkness_resistance", new MobEffectInstance(DARKNESS_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> LIGHT_RESISTANCE_POTION = POTION_TYPES.register("light_resistance",
        () -> new Potion("light_resistance", new MobEffectInstance(LIGHT_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> LIGHT_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_light_resistance",
        () -> new Potion("long_light_resistance", new MobEffectInstance(LIGHT_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> LIGHT_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_light_resistance",
        () -> new Potion("strong_light_resistance", new MobEffectInstance(LIGHT_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> FLORA_RESISTANCE_POTION = POTION_TYPES.register("flora_resistance",
        () -> new Potion("flora_resistance", new MobEffectInstance(FLORA_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> FLORA_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_flora_resistance",
        () -> new Potion("long_flora_resistance", new MobEffectInstance(FLORA_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> FLORA_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_flora_resistance",
        () -> new Potion("strong_flora_resistance", new MobEffectInstance(FLORA_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> EARTH_RESISTANCE_POTION = POTION_TYPES.register("earth_resistance",
        () -> new Potion("earth_resistance", new MobEffectInstance(EARTH_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> EARTH_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_earth_resistance",
        () -> new Potion("long_earth_resistance", new MobEffectInstance(EARTH_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> EARTH_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_earth_resistance",
        () -> new Potion("strong_earth_resistance", new MobEffectInstance(EARTH_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> WIND_RESISTANCE_POTION = POTION_TYPES.register("wind_resistance",
        () -> new Potion("wind_resistance", new MobEffectInstance(WIND_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> WIND_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_wind_resistance",
        () -> new Potion("long_wind_resistance", new MobEffectInstance(WIND_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> WIND_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_wind_resistance",
        () -> new Potion("strong_wind_resistance", new MobEffectInstance(WIND_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));

    @SubscribeEvent
    public static void registerMobEffects(Register<MobEffect> event) {
        ElementalCombat.LOGGER.info("Expected override");
        event.getRegistry()
            .register(FIRE_RESISTANCE_EFFECT.setRegistryName(MobEffects.FIRE_RESISTANCE.getRegistryName()));

    }

    @SubscribeEvent
    public static void registerPotions(Register<Potion> event) {
        ElementalCombat.LOGGER.info("Expected overrides");
        event.getRegistry()
            .register(
                new Potion("fire_resistance", new MobEffectInstance(FIRE_RESISTANCE_EFFECT, 3600, 0, false, true, true))
                    .setRegistryName(Potions.FIRE_RESISTANCE.getRegistryName()));
        event.getRegistry()
            .register(
                new Potion("long_fire_resistance", new MobEffectInstance(FIRE_RESISTANCE_EFFECT, 9600, 0, false, true, true))
                    .setRegistryName(Potions.LONG_FIRE_RESISTANCE.getRegistryName()));
    }
}
