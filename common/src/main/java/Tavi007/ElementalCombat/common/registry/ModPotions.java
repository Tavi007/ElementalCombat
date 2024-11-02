package Tavi007.ElementalCombat.common.registry;

import Tavi007.ElementalCombat.common.Constants;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ModPotions {
    private static final Map<String, Potion> registry = new HashMap<>();

    public static final Potion FIRE_RESISTANCE_POTION_STRONG = register(Constants.STRONG_FIRE_RESISTANCE,
            new Potion(Constants.STRONG_FIRE_RESISTANCE, new MobEffectInstance(ModMobEffects.FIRE_RESISTANCE, 3600, 1, false, true, true)));
    public static final Potion ICE_RESISTANCE_POTION = register(Constants.ICE_RESISTANCE,
            new Potion(Constants.ICE_RESISTANCE, new MobEffectInstance(ModMobEffects.ICE_RESISTANCE, 3600, 0, false, true, true)));
    public static final Potion ICE_RESISTANCE_POTION_LONG = register(Constants.LONG_ICE_RESISTANCE,
            new Potion(Constants.LONG_ICE_RESISTANCE, new MobEffectInstance(ModMobEffects.ICE_RESISTANCE, 9600, 0, false, true, true)));
    public static final Potion ICE_RESISTANCE_POTION_STRONG = register(Constants.STRONG_ICE_RESISTANCE,
            new Potion(Constants.STRONG_ICE_RESISTANCE, new MobEffectInstance(ModMobEffects.ICE_RESISTANCE, 3600, 1, false, true, true)));
    public static final Potion THUNDER_RESISTANCE_POTION = register(Constants.THUNDER_RESISTANCE,
            new Potion(Constants.THUNDER_RESISTANCE, new MobEffectInstance(ModMobEffects.THUNDER_RESISTANCE, 3600, 0, false, true, true)));
    public static final Potion THUNDER_RESISTANCE_POTION_LONG = register(Constants.LONG_THUNDER_RESISTANCE,
            new Potion(Constants.LONG_THUNDER_RESISTANCE, new MobEffectInstance(ModMobEffects.THUNDER_RESISTANCE, 9600, 0, false, true, true)));
    public static final Potion THUNDER_RESISTANCE_POTION_STRONG = register(Constants.STRONG_THUNDER_RESISTANCE,
            new Potion(Constants.STRONG_THUNDER_RESISTANCE, new MobEffectInstance(ModMobEffects.THUNDER_RESISTANCE, 3600, 1, false, true, true)));
    public static final Potion WATER_RESISTANCE_POTION = register(Constants.WATER_RESISTANCE,
            new Potion(Constants.WATER_RESISTANCE, new MobEffectInstance(ModMobEffects.WATER_RESISTANCE, 3600, 0, false, true, true)));
    public static final Potion WATER_RESISTANCE_POTION_LONG = register(Constants.LONG_WATER_RESISTANCE,
            new Potion(Constants.LONG_WATER_RESISTANCE, new MobEffectInstance(ModMobEffects.WATER_RESISTANCE, 9600, 0, false, true, true)));
    public static final Potion WATER_RESISTANCE_POTION_STRONG = register(Constants.STRONG_WATER_RESISTANCE,
            new Potion(Constants.STRONG_WATER_RESISTANCE, new MobEffectInstance(ModMobEffects.WATER_RESISTANCE, 3600, 1, false, true, true)));
    public static final Potion DARKNESS_RESISTANCE_POTION = register(Constants.DARKNESS_RESISTANCE,
            new Potion(Constants.DARKNESS_RESISTANCE, new MobEffectInstance(ModMobEffects.DARKNESS_RESISTANCE, 3600, 0, false, true, true)));
    public static final Potion DARKNESS_RESISTANCE_POTION_LONG = register(Constants.LONG_DARKNESS_RESISTANCE,
            new Potion(Constants.LONG_DARKNESS_RESISTANCE, new MobEffectInstance(ModMobEffects.DARKNESS_RESISTANCE, 9600, 0, false, true, true)));
    public static final Potion DARKNESS_RESISTANCE_POTION_STRONG = register(Constants.STRONG_DARKNESS_RESISTANCE,
            new Potion(Constants.STRONG_DARKNESS_RESISTANCE, new MobEffectInstance(ModMobEffects.DARKNESS_RESISTANCE, 3600, 1, false, true, true)));
    public static final Potion LIGHT_RESISTANCE_POTION = register(Constants.LIGHT_RESISTANCE,
            new Potion(Constants.LIGHT_RESISTANCE, new MobEffectInstance(ModMobEffects.LIGHT_RESISTANCE, 3600, 0, false, true, true)));
    public static final Potion LIGHT_RESISTANCE_POTION_LONG = register(Constants.LONG_LIGHT_RESISTANCE,
            new Potion(Constants.LONG_LIGHT_RESISTANCE, new MobEffectInstance(ModMobEffects.LIGHT_RESISTANCE, 9600, 0, false, true, true)));
    public static final Potion LIGHT_RESISTANCE_POTION_STRONG = register(Constants.STRONG_LIGHT_RESISTANCE,
            new Potion(Constants.STRONG_LIGHT_RESISTANCE, new MobEffectInstance(ModMobEffects.LIGHT_RESISTANCE, 3600, 1, false, true, true)));
    public static final Potion EARTH_RESISTANCE_POTION = register(Constants.EARTH_RESISTANCE,
            new Potion(Constants.EARTH_RESISTANCE, new MobEffectInstance(ModMobEffects.EARTH_RESISTANCE, 3600, 0, false, true, true)));
    public static final Potion EARTH_RESISTANCE_POTION_LONG = register(Constants.LONG_EARTH_RESISTANCE,
            new Potion(Constants.LONG_EARTH_RESISTANCE, new MobEffectInstance(ModMobEffects.EARTH_RESISTANCE, 9600, 0, false, true, true)));
    public static final Potion EARTH_RESISTANCE_POTION_STRONG = register(Constants.STRONG_EARTH_RESISTANCE,
            new Potion(Constants.STRONG_EARTH_RESISTANCE, new MobEffectInstance(ModMobEffects.EARTH_RESISTANCE, 3600, 1, false, true, true)));
    public static final Potion WIND_RESISTANCE_POTION = register(Constants.WIND_RESISTANCE,
            new Potion(Constants.WIND_RESISTANCE, new MobEffectInstance(ModMobEffects.WIND_RESISTANCE, 3600, 0, false, true, true)));
    public static final Potion WIND_RESISTANCE_POTION_LONG = register(Constants.LONG_WIND_RESISTANCE,
            new Potion(Constants.LONG_WIND_RESISTANCE, new MobEffectInstance(ModMobEffects.WIND_RESISTANCE, 9600, 0, false, true, true)));
    public static final Potion WIND_RESISTANCE_POTION_STRONG = register(Constants.STRONG_WIND_RESISTANCE,
            new Potion(Constants.STRONG_WIND_RESISTANCE, new MobEffectInstance(ModMobEffects.WIND_RESISTANCE, 3600, 1, false, true, true)));
    public static final Potion FLORA_RESISTANCE_POTION = register(Constants.FLORA_RESISTANCE,
            new Potion(Constants.FLORA_RESISTANCE, new MobEffectInstance(ModMobEffects.FLORA_RESISTANCE, 3600, 0, false, true, true)));
    public static final Potion FLORA_RESISTANCE_POTION_LONG = register(Constants.LONG_FLORA_RESISTANCE,
            new Potion(Constants.LONG_FLORA_RESISTANCE, new MobEffectInstance(ModMobEffects.FLORA_RESISTANCE, 9600, 0, false, true, true)));
    public static final Potion FLORA_RESISTANCE_POTION_STRONG = register(Constants.STRONG_FLORA_RESISTANCE,
            new Potion(Constants.STRONG_FLORA_RESISTANCE, new MobEffectInstance(ModMobEffects.FLORA_RESISTANCE, 3600, 1, false, true, true)));

    // Harming
    public static final Potion FIRE_HARMING_POTION = register(Constants.FIRE_HARMING,
            new Potion(Constants.FIRE_HARMING, new MobEffectInstance(ModMobEffects.FIRE_HARMING, 1)));
    public static final Potion STRONG_FIRE_HARMING_POTION = register(Constants.STRONG_FIRE_HARMING,
            new Potion(Constants.STRONG_FIRE_HARMING, new MobEffectInstance(ModMobEffects.FIRE_HARMING, 1, 1)));
    public static final Potion ICE_HARMING_POTION = register(Constants.ICE_HARMING,
            new Potion(Constants.ICE_HARMING, new MobEffectInstance(ModMobEffects.ICE_HARMING, 1)));
    public static final Potion STRONG_ICE_HARMING_POTION = register(Constants.STRONG_ICE_HARMING,
            new Potion(Constants.STRONG_ICE_HARMING, new MobEffectInstance(ModMobEffects.ICE_HARMING, 1, 1)));
    public static final Potion THUNDER_HARMING_POTION = register(Constants.THUNDER_HARMING,
            new Potion(Constants.THUNDER_HARMING, new MobEffectInstance(ModMobEffects.THUNDER_HARMING, 1)));
    public static final Potion STRONG_THUNDER_HARMING_POTION = register(Constants.STRONG_THUNDER_HARMING,
            new Potion(Constants.STRONG_THUNDER_HARMING, new MobEffectInstance(ModMobEffects.THUNDER_HARMING, 1, 1)));
    public static final Potion WATER_HARMING_POTION = register(Constants.WATER_HARMING,
            new Potion(Constants.WATER_HARMING, new MobEffectInstance(ModMobEffects.WATER_HARMING, 1)));
    public static final Potion STRONG_WATER_HARMING_POTION = register(Constants.STRONG_WATER_HARMING,
            new Potion(Constants.STRONG_WATER_HARMING, new MobEffectInstance(ModMobEffects.WATER_HARMING, 1, 1)));
    public static final Potion DARKNESS_HARMING_POTION = register(Constants.DARKNESS_HARMING,
            new Potion(Constants.DARKNESS_HARMING, new MobEffectInstance(ModMobEffects.DARKNESS_HARMING, 1)));
    public static final Potion STRONG_DARKNESS_HARMING_POTION = register(Constants.STRONG_DARKNESS_HARMING,
            new Potion(Constants.STRONG_DARKNESS_HARMING, new MobEffectInstance(ModMobEffects.DARKNESS_HARMING, 1, 1)));
    public static final Potion LIGHT_HARMING_POTION = register(Constants.LIGHT_HARMING,
            new Potion(Constants.LIGHT_HARMING, new MobEffectInstance(ModMobEffects.LIGHT_HARMING, 1)));
    public static final Potion STRONG_LIGHT_HARMING_POTION = register(Constants.STRONG_LIGHT_HARMING,
            new Potion(Constants.STRONG_LIGHT_HARMING, new MobEffectInstance(ModMobEffects.LIGHT_HARMING, 1, 1)));
    public static final Potion EARTH_HARMING_POTION = register(Constants.EARTH_HARMING,
            new Potion(Constants.EARTH_HARMING, new MobEffectInstance(ModMobEffects.EARTH_HARMING, 1)));
    public static final Potion STRONG_EARTH_HARMING_POTION = register(Constants.STRONG_EARTH_HARMING,
            new Potion(Constants.STRONG_EARTH_HARMING, new MobEffectInstance(ModMobEffects.EARTH_HARMING, 1, 1)));
    public static final Potion WIND_HARMING_POTION = register(Constants.WIND_HARMING,
            new Potion(Constants.WIND_HARMING, new MobEffectInstance(ModMobEffects.WIND_HARMING, 1)));
    public static final Potion STRONG_WIND_HARMING_POTION = register(Constants.STRONG_WIND_HARMING,
            new Potion(Constants.STRONG_WIND_HARMING, new MobEffectInstance(ModMobEffects.WIND_HARMING, 1, 1)));
    public static final Potion FLORA_HARMING_POTION = register(Constants.FLORA_HARMING,
            new Potion(Constants.FLORA_HARMING, new MobEffectInstance(ModMobEffects.FLORA_HARMING, 1)));
    public static final Potion STRONG_FLORA_HARMING_POTION = register(Constants.STRONG_FLORA_HARMING,
            new Potion(Constants.STRONG_FLORA_HARMING, new MobEffectInstance(ModMobEffects.FLORA_HARMING, 1, 1)));

    private static Potion register(String name, Potion potion) {
        registry.put(name, potion);
        return potion;
    }

    public static void register(BiConsumer<String, Potion> registerConsumer) {
        registry.forEach((name, potion) -> {
            registerConsumer.accept(name, potion);
        });
    }
}
