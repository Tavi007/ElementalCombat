package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.potions.ElementalHarmingEffect;
import Tavi007.ElementalCombat.common.potions.ElementalResistanceEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.List;

public class PotionList {

    public static final DeferredRegister<MobEffect> POTION_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MOD_ID);
    public static final RegistryObject<MobEffect> FIRE_RESISTANCE_EFFECT = POTION_EFFECTS.register("fire_resistance",
            () -> new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 14981690, "fire", "ice"));
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
            () -> new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 11335061, List.of("flora"), Collections.emptyList()));
    public static final RegistryObject<MobEffect> WIND_RESISTANCE_EFFECT = POTION_EFFECTS.register("wind_resistance",
            () -> new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 9285299, "wind", "earth"));
    public static final RegistryObject<MobEffect> EARTH_RESISTANCE_EFFECT = POTION_EFFECTS.register("earth_resistance",
            () -> new ElementalResistanceEffect(MobEffectCategory.NEUTRAL, 5854011, "earth", "wind"));
    public static final RegistryObject<MobEffect> FIRE_HARMING_EFFECT = POTION_EFFECTS.register("fire_harming",
            () -> new ElementalHarmingEffect("fire", 14981690));
    public static final RegistryObject<MobEffect> ICE_HARMING_EFFECT = POTION_EFFECTS.register("ice_harming",
            () -> new ElementalHarmingEffect("ice", 9158640));
    public static final RegistryObject<MobEffect> THUNDER_HARMING_EFFECT = POTION_EFFECTS.register("thunder_harming",
            () -> new ElementalHarmingEffect("thunder", 14735695));
    public static final RegistryObject<MobEffect> WATER_HARMING_EFFECT = POTION_EFFECTS.register("water_harming",
            () -> new ElementalHarmingEffect("water", 2636728));
    public static final RegistryObject<MobEffect> DARKNESS_HARMING_EFFECT = POTION_EFFECTS.register("darkness_harming",
            () -> new ElementalHarmingEffect("darkness", 2821688));
    public static final RegistryObject<MobEffect> LIGHT_HARMING_EFFECT = POTION_EFFECTS.register("light_harming",
            () -> new ElementalHarmingEffect("light", 15856307));
    public static final RegistryObject<MobEffect> FLORA_HARMING_EFFECT = POTION_EFFECTS.register("flora_harming",
            () -> new ElementalHarmingEffect("flora", 11335061));
    public static final RegistryObject<MobEffect> WIND_HARMING_EFFECT = POTION_EFFECTS.register("wind_harming",
            () -> new ElementalHarmingEffect("wind", 9285299));
    public static final RegistryObject<MobEffect> EARTH_HARMING_EFFECT = POTION_EFFECTS.register("earth_harming",
            () -> new ElementalHarmingEffect("earth", 5854011));
    public static final DeferredRegister<Potion> POTION_TYPES = DeferredRegister.create(ForgeRegistries.POTIONS, Constants.MOD_ID);
    public static final RegistryObject<Potion> FIRE_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_fire_resistance",
            () -> new Potion("strong_fire_resistance", new MobEffectInstance(FIRE_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
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
    public static final RegistryObject<Potion> WIND_RESISTANCE_POTION = POTION_TYPES.register("wind_resistance",
            () -> new Potion("wind_resistance", new MobEffectInstance(WIND_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> WIND_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_wind_resistance",
            () -> new Potion("long_wind_resistance", new MobEffectInstance(WIND_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> WIND_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_wind_resistance",
            () -> new Potion("strong_wind_resistance", new MobEffectInstance(WIND_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> EARTH_RESISTANCE_POTION = POTION_TYPES.register("earth_resistance",
            () -> new Potion("earth_resistance", new MobEffectInstance(EARTH_RESISTANCE_EFFECT.get(), 3600, 0, false, true, true)));
    public static final RegistryObject<Potion> EARTH_RESISTANCE_POTION_LONG = POTION_TYPES.register("long_earth_resistance",
            () -> new Potion("long_earth_resistance", new MobEffectInstance(EARTH_RESISTANCE_EFFECT.get(), 9600, 0, false, true, true)));
    public static final RegistryObject<Potion> EARTH_RESISTANCE_POTION_STRONG = POTION_TYPES.register("strong_earth_resistance",
            () -> new Potion("strong_earth_resistance", new MobEffectInstance(EARTH_RESISTANCE_EFFECT.get(), 3600, 1, false, true, true)));
    public static final RegistryObject<Potion> FIRE_HARMING_POTION = POTION_TYPES.register("fire_harming",
            () -> new Potion("fire_harming", new MobEffectInstance(FIRE_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_FIRE_HARMING_POTION = POTION_TYPES.register("strong_fire_harming",
            () -> new Potion("strong_fire_harming", new MobEffectInstance(FIRE_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> ICE_HARMING_POTION = POTION_TYPES.register("ice_harming",
            () -> new Potion("ice_harming", new MobEffectInstance(ICE_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_ICE_HARMING_POTION = POTION_TYPES.register("strong_ice_harming",
            () -> new Potion("strong_ice_harming", new MobEffectInstance(ICE_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> THUNDER_HARMING_POTION = POTION_TYPES.register("thunder_harming",
            () -> new Potion("thunder_harming", new MobEffectInstance(THUNDER_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_THUNDER_HARMING_POTION = POTION_TYPES.register("strong_thunder_harming",
            () -> new Potion("strong_thunder_harming", new MobEffectInstance(THUNDER_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> WATER_HARMING_POTION = POTION_TYPES.register("water_harming",
            () -> new Potion("water_harming", new MobEffectInstance(WATER_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_WATER_HARMING_POTION = POTION_TYPES.register("strong_water_harming",
            () -> new Potion("strong_water_harming", new MobEffectInstance(WATER_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> DARKNESS_HARMING_POTION = POTION_TYPES.register("darkness_harming",
            () -> new Potion("darkness_harming", new MobEffectInstance(DARKNESS_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_DARKNESS_HARMING_POTION = POTION_TYPES.register("strong_darkness_harming",
            () -> new Potion("strong_darkness_harming", new MobEffectInstance(DARKNESS_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> LIGHT_HARMING_POTION = POTION_TYPES.register("light_harming",
            () -> new Potion("light_harming", new MobEffectInstance(LIGHT_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_LIGHT_HARMING_POTION = POTION_TYPES.register("strong_light_harming",
            () -> new Potion("strong_light_harming", new MobEffectInstance(LIGHT_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> FLORA_HARMING_POTION = POTION_TYPES.register("flora_harming",
            () -> new Potion("flora_harming", new MobEffectInstance(FLORA_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_FLORA_HARMING_POTION = POTION_TYPES.register("strong_flora_harming",
            () -> new Potion("strong_flora_harming", new MobEffectInstance(FLORA_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> WIND_HARMING_POTION = POTION_TYPES.register("wind_harming",
            () -> new Potion("wind_harming", new MobEffectInstance(WIND_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_WIND_HARMING_POTION = POTION_TYPES.register("strong_wind_harming",
            () -> new Potion("strong_wind_harming", new MobEffectInstance(WIND_HARMING_EFFECT.get(), 1, 1)));
    public static final RegistryObject<Potion> EARTH_HARMING_POTION = POTION_TYPES.register("earth_harming",
            () -> new Potion("earth_harming", new MobEffectInstance(EARTH_HARMING_EFFECT.get(), 1)));
    public static final RegistryObject<Potion> STRONG_EARTH_HARMING_POTION = POTION_TYPES.register("strong_earth_harming",
            () -> new Potion("strong_earth_harming", new MobEffectInstance(EARTH_HARMING_EFFECT.get(), 1, 1)));
}
