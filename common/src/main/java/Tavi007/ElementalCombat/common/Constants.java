package Tavi007.ElementalCombat.common;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    public static final String MOD_ID = "elementalcombat";
    public static final String MOD_NAME = "Elemental Combat";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final Minecraft INSTANCE = Minecraft.getInstance();

    // Capabilities
    public static final ResourceLocation ATTACK_DATA_CAPABILITY = new ResourceLocation(MOD_ID, "attack");
    public static final ResourceLocation DEFENSE_DATA_CAPABILITY = new ResourceLocation(MOD_ID, "defense");
    public static final ResourceLocation IMMERSION_DATA_CAPABILITY = new ResourceLocation(MOD_ID, "immersion");

    // Elements
    public static final String FIRE = "fire";
    public static final String ICE = "ice";
    public static final String WATER = "water";
    public static final String THUNDER = "thunder";
    public static final String DARKNESS = "darkness";
    public static final String LIGHT = "light";
    public static final String EARTH = "earth";
    public static final String WIND = "wind";
    public static final String FLORA = "flora";


    // Blocks
    public static final String ESSENCE_BLOCK_FIRE = "essence_block_fire";
    public static final String ESSENCE_BLOCK_ICE = "essence_block_ice";
    public static final String ESSENCE_BLOCK_WATER = "essence_block_water";
    public static final String ESSENCE_BLOCK_THUNDER = "essence_block_thunder";
    public static final String ESSENCE_BLOCK_DARKNESS = "essence_block_darkness";
    public static final String ESSENCE_BLOCK_LIGHT = "essence_block_light";
    public static final String ESSENCE_BLOCK_EARTH = "essence_block_earth";
    public static final String ESSENCE_BLOCK_WIND = "essence_block_wind";
    public static final String ESSENCE_BLOCK_FLORA = "essence_block_flora";

    // Items
    public static final String ESSENCE_FIRE = "essence_fire";
    public static final String ESSENCE_ICE = "essence_ice";
    public static final String ESSENCE_WATER = "essence_water";
    public static final String ESSENCE_THUNDER = "essence_thunder";
    public static final String ESSENCE_DARKNESS = "essence_darkness";
    public static final String ESSENCE_LIGHT = "essence_light";
    public static final String ESSENCE_EARTH = "essence_earth";
    public static final String ESSENCE_WIND = "essence_wind";
    public static final String ESSENCE_FLORA = "essence_flora";
    public static final String LENS = "combat_data_lens";

    // Particles
    public static final String CRIT_ELEMENT = "crit_element";
    public static final String CRIT_STYLE = "crit_style";
    public static final String RESIST_ELEMENT = "resist_element";
    public static final String RESIST_STYLE = "resist_style";
    public static final String ABSORB = "absorb";

    // Enchantments
    public static final String ICE_PROTECTION = "ice_protection";
    public static final String WATER_PROTECTION = "water_protection";
    public static final String THUNDER_PROTECTION = "thunder_protection";
    public static final String DARKNESS_PROTECTION = "darkness_protection";
    public static final String LIGHT_PROTECTION = "light_protection";
    public static final String EARTH_PROTECTION = "earth_protection";
    public static final String WIND_PROTECTION = "wind_protection";
    public static final String FLORA_PROTECTION = "flora_protection";
    public static final String ELEMENT_PROTECTION = "element_protection";
    public static final String MAGIC_PROTECTION = "magic_protection";
    public static final String ICE_ASPECT = "ice_aspect";
    public static final String WATER_ASPECT = "water_aspect";
    public static final String THUNDER_ASPECT = "thunder_aspect";
    public static final String DARKNESS_ASPECT = "darkness_aspect";
    public static final String LIGHT_ASPECT = "light_aspect";
    public static final String EARTH_ASPECT = "earth_aspect";
    public static final String WIND_ASPECT = "wind_aspect";
    public static final String FLORA_ASPECT = "flora_aspect";

    // MobEffects and Potions
    public static final String FIRE_RESISTANCE = "fire_resistance";
    public static final String ICE_RESISTANCE = "ice_resistance";
    public static final String WATER_RESISTANCE = "water_resistance";
    public static final String THUNDER_RESISTANCE = "thunder_resistance";
    public static final String DARKNESS_RESISTANCE = "darkness_resistance";
    public static final String LIGHT_RESISTANCE = "light_resistance";
    public static final String EARTH_RESISTANCE = "earth_resistance";
    public static final String WIND_RESISTANCE = "wind_resistance";
    public static final String FLORA_RESISTANCE = "flora_resistance";
    public static final String FIRE_HARMING = "fire_harming";
    public static final String ICE_HARMING = "ice_harming";
    public static final String WATER_HARMING = "water_harming";
    public static final String THUNDER_HARMING = "thunder_harming";
    public static final String DARKNESS_HARMING = "darkness_harming";
    public static final String LIGHT_HARMING = "light_harming";
    public static final String EARTH_HARMING = "earth_harming";
    public static final String WIND_HARMING = "wind_harming";
    public static final String FLORA_HARMING = "flora_harming";

    // Strong Potions
    public static final String STRONG_FIRE_RESISTANCE = "strong_fire_resistance";
    public static final String STRONG_ICE_RESISTANCE = "strong_ice_resistance";
    public static final String STRONG_WATER_RESISTANCE = "strong_water_resistance";
    public static final String STRONG_THUNDER_RESISTANCE = "strong_thunder_resistance";
    public static final String STRONG_DARKNESS_RESISTANCE = "strong_darkness_resistance";
    public static final String STRONG_LIGHT_RESISTANCE = "strong_light_resistance";
    public static final String STRONG_EARTH_RESISTANCE = "strong_earth_resistance";
    public static final String STRONG_WIND_RESISTANCE = "strong_wind_resistance";
    public static final String STRONG_FLORA_RESISTANCE = "strong_flora_resistance";
    public static final String STRONG_FIRE_HARMING = "strong_fire_harming";
    public static final String STRONG_ICE_HARMING = "strong_ice_harming";
    public static final String STRONG_WATER_HARMING = "strong_water_harming";
    public static final String STRONG_THUNDER_HARMING = "strong_thunder_harming";
    public static final String STRONG_DARKNESS_HARMING = "strong_darkness_harming";
    public static final String STRONG_LIGHT_HARMING = "strong_light_harming";
    public static final String STRONG_EARTH_HARMING = "strong_earth_harming";
    public static final String STRONG_WIND_HARMING = "strong_wind_harming";
    public static final String STRONG_FLORA_HARMING = "strong_flora_harming";


    // Long Potions
    public static final String LONG_FIRE_RESISTANCE = "long_fire_resistance";
    public static final String LONG_ICE_RESISTANCE = "long_ice_resistance";
    public static final String LONG_WATER_RESISTANCE = "long_water_resistance";
    public static final String LONG_THUNDER_RESISTANCE = "long_thunder_resistance";
    public static final String LONG_DARKNESS_RESISTANCE = "long_darkness_resistance";
    public static final String LONG_LIGHT_RESISTANCE = "long_light_resistance";
    public static final String LONG_EARTH_RESISTANCE = "long_earth_resistance";
    public static final String LONG_WIND_RESISTANCE = "long_wind_resistance";
    public static final String LONG_FLORA_RESISTANCE = "long_flora_resistance";
}