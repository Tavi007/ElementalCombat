package Tavi007.ElementalCombat.init;

import java.util.Arrays;
import java.util.Collections;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.potions.ElementalHarmingEffect;
import Tavi007.ElementalCombat.potions.ElementalResistanceEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionList {

    public static final DeferredRegister<Effect> POTION_EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, ElementalCombat.MOD_ID);
    public static final RegistryObject<Effect> FIRE_RESISTANCE_EFFECT = POTION_EFFECTS.register("fire_resistance",
        () -> new ElementalResistanceEffect(EffectType.NEUTRAL, 14981690, "fire", "ice"));
    public static final RegistryObject<Effect> ICE_RESISTANCE_EFFECT = POTION_EFFECTS.register("ice_resistance",
        () -> new ElementalResistanceEffect(EffectType.NEUTRAL, 9158640, "ice", "fire"));
    public static final RegistryObject<Effect> THUNDER_RESISTANCE_EFFECT = POTION_EFFECTS.register("thunder_resistance",
        () -> new ElementalResistanceEffect(EffectType.NEUTRAL, 14735695, "thunder", "water"));
    public static final RegistryObject<Effect> WATER_RESISTANCE_EFFECT = POTION_EFFECTS.register("water_resistance",
        () -> new ElementalResistanceEffect(EffectType.NEUTRAL, 2636728, "water", "thunder"));
    public static final RegistryObject<Effect> DARKNESS_RESISTANCE_EFFECT = POTION_EFFECTS.register("darkness_resistance",
        () -> new ElementalResistanceEffect(EffectType.NEUTRAL, 2821688, "darkness", "light"));
    public static final RegistryObject<Effect> LIGHT_RESISTANCE_EFFECT = POTION_EFFECTS.register("light_resistance",
        () -> new ElementalResistanceEffect(EffectType.NEUTRAL, 15856307, "light", "darkness"));
    public static final RegistryObject<Effect> FLORA_RESISTANCE_EFFECT = POTION_EFFECTS.register("flora_resistance",
        () -> new ElementalResistanceEffect(EffectType.NEUTRAL, 11335061, Arrays.asList("flora"), Collections.emptyList()));
    public static final RegistryObject<Effect> WIND_RESISTANCE_EFFECT = POTION_EFFECTS.register("wind_resistance",
        () -> new ElementalResistanceEffect(EffectType.NEUTRAL, 9285299, "wind", "earth"));
    public static final RegistryObject<Effect> EARTH_RESISTANCE_EFFECT = POTION_EFFECTS.register("earth_resistance",
        () -> new ElementalResistanceEffect(EffectType.NEUTRAL, 5854011, "earth", "wind"));

    public static final RegistryObject<Effect> FIRE_HARMING_EFFECT = POTION_EFFECTS.register("fire_harming",
        () -> new ElementalHarmingEffect("fire", 5854011));
    public static final RegistryObject<Effect> ICE_HARMING_EFFECT = POTION_EFFECTS.register("ice_harming",
        () -> new ElementalHarmingEffect("ice", 5854011));
    public static final RegistryObject<Effect> THUNDER_HARMING_EFFECT = POTION_EFFECTS.register("thunder_harming",
        () -> new ElementalHarmingEffect("thunder", 5854011));
    public static final RegistryObject<Effect> WATER_HARMING_EFFECT = POTION_EFFECTS.register("water_harming",
        () -> new ElementalHarmingEffect("water", 5854011));
    public static final RegistryObject<Effect> DARKNESS_HARMING_EFFECT = POTION_EFFECTS.register("darkness_harming",
        () -> new ElementalHarmingEffect("darkness", 5854011));
    public static final RegistryObject<Effect> LIGHT_HARMING_EFFECT = POTION_EFFECTS.register("light_harming",
        () -> new ElementalHarmingEffect("light", 5854011));
    public static final RegistryObject<Effect> FLORA_HARMING_EFFECT = POTION_EFFECTS.register("flora_harming",
        () -> new ElementalHarmingEffect("flora", 5854011));
    public static final RegistryObject<Effect> WIND_HARMING_EFFECT = POTION_EFFECTS.register("wind_harming",
        () -> new ElementalHarmingEffect("wind", 5854011));
    public static final RegistryObject<Effect> EARTH_HARMING_EFFECT = POTION_EFFECTS.register("earth_harming",
        () -> new ElementalHarmingEffect("earth", 5854011));

    public static final DeferredRegister<Potion> POTION_TYPES = DeferredRegister.create(ForgeRegistries.POTION_TYPES, ElementalCombat.MOD_ID);
    public static final RegistryObject<Potion> FIRE_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_fire_resistance",
        () -> new Potion("strong_fire_resistance", new EffectInstance(FIRE_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> ICE_RESISTANCE_POTION = POTION_TYPES.register("ice_resistance",
        () -> new Potion("ice_resistance", new EffectInstance(ICE_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> ICE_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_ice_resistance",
        () -> new Potion("long_ice_resistance", new EffectInstance(ICE_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> ICE_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_ice_resistance",
        () -> new Potion("strong_ice_resistance", new EffectInstance(ICE_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> THUNDER_RESISTANCE_POTION = POTION_TYPES.register("thunder_resistance",
        () -> new Potion("thunder_resistance", new EffectInstance(THUNDER_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> THUNDER_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_thunder_resistance",
        () -> new Potion("long_thunder_resistance", new EffectInstance(THUNDER_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> THUNDER_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_thunder_resistance",
        () -> new Potion("strong_thunder_resistance", new EffectInstance(THUNDER_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> WATER_RESISTANCE_POTION = POTION_TYPES.register("water_resistance",
        () -> new Potion("water_resistance", new EffectInstance(WATER_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> WATER_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_water_resistance",
        () -> new Potion("long_water_resistance", new EffectInstance(WATER_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> WATER_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_water_resistance",
        () -> new Potion("strong_water_resistance", new EffectInstance(WATER_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> DARKNESS_RESISTANCE_POTION = POTION_TYPES.register("darkness_resistance",
        () -> new Potion("darkness_resistance", new EffectInstance(DARKNESS_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> DARKNESS_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_darkness_resistance",
        () -> new Potion("long_darkness_resistance", new EffectInstance(DARKNESS_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> DARKNESS_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_darkness_resistance",
        () -> new Potion("strong_darkness_resistance", new EffectInstance(DARKNESS_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> LIGHT_RESISTANCE_POTION = POTION_TYPES.register("light_resistance",
        () -> new Potion("light_resistance", new EffectInstance(LIGHT_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> LIGHT_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_light_resistance",
        () -> new Potion("long_light_resistance", new EffectInstance(LIGHT_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> LIGHT_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_light_resistance",
        () -> new Potion("strong_light_resistance", new EffectInstance(LIGHT_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> FLORA_RESISTANCE_POTION = POTION_TYPES.register("flora_resistance",
        () -> new Potion("flora_resistance", new EffectInstance(FLORA_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> FLORA_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_flora_resistance",
        () -> new Potion("long_flora_resistance", new EffectInstance(FLORA_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> FLORA_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_flora_resistance",
        () -> new Potion("strong_flora_resistance", new EffectInstance(FLORA_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> EARTH_RESISTANCE_POTION = POTION_TYPES.register("earth_resistance",
        () -> new Potion("earth_resistance", new EffectInstance(EARTH_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> EARTH_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_earth_resistance",
        () -> new Potion("long_earth_resistance", new EffectInstance(EARTH_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> EARTH_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_earth_resistance",
        () -> new Potion("strong_earth_resistance", new EffectInstance(EARTH_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> WIND_RESISTANCE_POTION = POTION_TYPES.register("wind_resistance",
        () -> new Potion("wind_resistance", new EffectInstance(WIND_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> WIND_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_wind_resistance",
        () -> new Potion("long_wind_resistance", new EffectInstance(WIND_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> WIND_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_wind_resistance",
        () -> new Potion("strong_wind_resistance", new EffectInstance(WIND_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));

    public static final RegistryObject<Potion> FIRE_HARMING_POTION = POTION_TYPES.register("fire_harming",
        () -> new Potion("fire_harming", new EffectInstance(FIRE_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_FIRE_HARMING_POTION = POTION_TYPES.register("strong_fire_harming",
        () -> new Potion("strong_fire_harming", new EffectInstance(FIRE_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> ICE_HARMING_POTION = POTION_TYPES.register("ice_harming",
        () -> new Potion("ice_harming", new EffectInstance(ICE_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_ICE_HARMING_POTION = POTION_TYPES.register("strong_ice_harming",
        () -> new Potion("strong_ice_harming", new EffectInstance(ICE_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> THUNDER_HARMING_POTION = POTION_TYPES.register("thunder_harming",
        () -> new Potion("thunder_harming", new EffectInstance(THUNDER_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_THUNDER_HARMING_POTION = POTION_TYPES.register("strong_thunder_harming",
        () -> new Potion("strong_thunder_harming", new EffectInstance(THUNDER_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> WATER_HARMING_POTION = POTION_TYPES.register("water_harming",
        () -> new Potion("water_harming", new EffectInstance(WATER_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_WATER_HARMING_POTION = POTION_TYPES.register("strong_water_harming",
        () -> new Potion("strong_water_harming", new EffectInstance(WATER_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> DARKNESS_HARMING_POTION = POTION_TYPES.register("darkness_harming",
        () -> new Potion("darkness_harming", new EffectInstance(DARKNESS_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_DARKNESS_HARMING_POTION = POTION_TYPES.register("strong_darkness_harming",
        () -> new Potion("strong_darkness_harming", new EffectInstance(DARKNESS_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> LIGHT_HARMING_POTION = POTION_TYPES.register("light_harming",
        () -> new Potion("light_harming", new EffectInstance(LIGHT_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_LIGHT_HARMING_POTION = POTION_TYPES.register("strong_light_harming",
        () -> new Potion("strong_light_harming", new EffectInstance(LIGHT_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> FLORA_HARMING_POTION = POTION_TYPES.register("flora_harming",
        () -> new Potion("flora_harming", new EffectInstance(FLORA_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_FLORA_HARMING_POTION = POTION_TYPES.register("strong_flora_harming",
        () -> new Potion("strong_flora_harming", new EffectInstance(FLORA_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> EARTH_HARMING_POTION = POTION_TYPES.register("earth_harming",
        () -> new Potion("earth_harming", new EffectInstance(EARTH_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_EARTH_HARMING_POTION = POTION_TYPES.register("strong_earth_harming",
        () -> new Potion("strong_earth_harming", new EffectInstance(EARTH_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> WIND_HARMING_POTION = POTION_TYPES.register("wind_harming",
        () -> new Potion("wind_harming", new EffectInstance(WIND_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_WIND_HARMING_POTION = POTION_TYPES.register("strong_wind_harming",
        () -> new Potion("strong_wind_harming", new EffectInstance(WIND_HARMING_EFFECT.get(), 1, 1)));
}
