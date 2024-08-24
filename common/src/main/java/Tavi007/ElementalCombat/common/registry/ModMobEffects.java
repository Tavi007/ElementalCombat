package Tavi007.ElementalCombat.common.registry;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.potions.ElementalHarmingEffect;
import Tavi007.ElementalCombat.common.potions.ElementalResistanceEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ModMobEffects {

    private static final Map<String, MobEffect> registry = new HashMap<>();


    // Resistance
    public static final MobEffect FIRE_RESISTANCE = register(Constants.FIRE_RESISTANCE,
            new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 14981690, Constants.FIRE, Constants.ICE));
    public static final MobEffect ICE_RESISTANCE = register(Constants.ICE_RESISTANCE,
            new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 9158640, Constants.ICE, Constants.FIRE));
    public static final MobEffect WATER_RESISTANCE = register(Constants.WATER_RESISTANCE,
            new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 2636728, Constants.WATER, Constants.THUNDER));
    public static final MobEffect THUNDER_RESISTANCE = register(Constants.THUNDER_RESISTANCE,
            new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 14735695, Constants.THUNDER, Constants.WATER));
    public static final MobEffect DARKNESS_RESISTANCE = register(Constants.DARKNESS_RESISTANCE,
            new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 2821688, Constants.DARKNESS, Constants.LIGHT));
    public static final MobEffect LIGHT_RESISTANCE = register(Constants.LIGHT_RESISTANCE,
            new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 15856307, Constants.LIGHT, Constants.DARKNESS));
    public static final MobEffect EARTH_RESISTANCE = register(Constants.EARTH_RESISTANCE,
            new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 5854011, Constants.EARTH, Constants.WIND));
    public static final MobEffect WIND_RESISTANCE = register(Constants.WIND_RESISTANCE,
            new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 9285299, Constants.WIND, Constants.EARTH));
    public static final MobEffect FLORA_RESISTANCE = register(Constants.FLORA_RESISTANCE,
            new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 11335061, List.of(Constants.FLORA), Collections.emptyList()));

    // Harming
    public static final MobEffect FIRE_HARMING = register(Constants.FIRE_HARMING, new ElementalHarmingEffect(Constants.FIRE, 14981690));
    public static final MobEffect ICE_HARMING = register(Constants.ICE_HARMING, new ElementalHarmingEffect(Constants.ICE, 9158640));
    public static final MobEffect WATER_HARMING = register(Constants.WATER_HARMING, new ElementalHarmingEffect(Constants.WATER, 2636728));
    public static final MobEffect THUNDER_HARMING = register(Constants.THUNDER_HARMING, new ElementalHarmingEffect(Constants.THUNDER, 14735695));
    public static final MobEffect DARKNESS_HARMING = register(Constants.DARKNESS_HARMING, new ElementalHarmingEffect(Constants.DARKNESS, 2821688));
    public static final MobEffect LIGHT_HARMING = register(Constants.LIGHT_HARMING, new ElementalHarmingEffect(Constants.LIGHT, 15856307));
    public static final MobEffect EARTH_HARMING = register(Constants.EARTH_HARMING, new ElementalHarmingEffect(Constants.EARTH, 5854011));
    public static final MobEffect WIND_HARMING = register(Constants.WIND_HARMING, new ElementalHarmingEffect(Constants.WIND, 9285299));
    public static final MobEffect FLORA_HARMING = register(Constants.FLORA_HARMING, new ElementalHarmingEffect(Constants.FLORA, 11335061));

    private static MobEffect register(String name, MobEffect effect) {
        registry.put(name, effect);
        return effect;
    }

    public static void register(BiConsumer<String, MobEffect> registerConsumer) {
        registry.forEach((name, effect) -> registerConsumer.accept(name, effect));
    }

}
