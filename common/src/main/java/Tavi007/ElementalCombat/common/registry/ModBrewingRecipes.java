package Tavi007.ElementalCombat.common.registry;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModBrewingRecipes {

    private static final List<BrewingRecipeData> recipes = new ArrayList<>();

    private static final BrewingRecipeData FIRE_HARMING_POTION = register(new BrewingRecipeData(Potions.HARMING, ModItems.ESSENCE_FIRE, ModPotions.FIRE_HARMING_POTION));
    private static final BrewingRecipeData ICE_HARMING_POTION = register(new BrewingRecipeData(Potions.HARMING, ModItems.ESSENCE_ICE, ModPotions.ICE_HARMING_POTION));
    private static final BrewingRecipeData THUNDER_HARMING_POTION = register(new BrewingRecipeData(Potions.HARMING, ModItems.ESSENCE_THUNDER, ModPotions.THUNDER_HARMING_POTION));
    private static final BrewingRecipeData WATER_HARMING_POTION = register(new BrewingRecipeData(Potions.HARMING, ModItems.ESSENCE_WATER, ModPotions.WATER_HARMING_POTION));
    private static final BrewingRecipeData DARKNESS_HARMING_POTION = register(new BrewingRecipeData(Potions.HARMING, ModItems.ESSENCE_DARKNESS, ModPotions.DARKNESS_HARMING_POTION));
    private static final BrewingRecipeData LIGHT_HARMING_POTION = register(new BrewingRecipeData(Potions.HARMING, ModItems.ESSENCE_LIGHT, ModPotions.LIGHT_HARMING_POTION));
    private static final BrewingRecipeData FLORA_HARMING_POTION = register(new BrewingRecipeData(Potions.HARMING, ModItems.ESSENCE_FLORA, ModPotions.FLORA_HARMING_POTION));
    private static final BrewingRecipeData WIND_HARMING_POTION = register(new BrewingRecipeData(Potions.HARMING, ModItems.ESSENCE_WIND, ModPotions.WIND_HARMING_POTION));
    private static final BrewingRecipeData EARTH_HARMING_POTION = register(new BrewingRecipeData(Potions.HARMING, ModItems.ESSENCE_EARTH, ModPotions.EARTH_HARMING_POTION));

    private static final BrewingRecipeData STRONG_FIRE_HARMING_POTION = register(new BrewingRecipeData(Potions.STRONG_HARMING, ModItems.ESSENCE_FIRE, ModPotions.STRONG_FIRE_HARMING_POTION));
    private static final BrewingRecipeData STRONG_ICE_HARMING_POTION = register(new BrewingRecipeData(Potions.STRONG_HARMING, ModItems.ESSENCE_ICE, ModPotions.STRONG_ICE_HARMING_POTION));
    private static final BrewingRecipeData STRONG_THUNDER_HARMING_POTION = register(new BrewingRecipeData(Potions.STRONG_HARMING, ModItems.ESSENCE_THUNDER, ModPotions.STRONG_THUNDER_HARMING_POTION));
    private static final BrewingRecipeData STRONG_WATER_HARMING_POTION = register(new BrewingRecipeData(Potions.STRONG_HARMING, ModItems.ESSENCE_WATER, ModPotions.STRONG_WATER_HARMING_POTION));
    private static final BrewingRecipeData STRONG_DARKNESS_HARMING_POTION = register(new BrewingRecipeData(Potions.STRONG_HARMING, ModItems.ESSENCE_DARKNESS, ModPotions.STRONG_DARKNESS_HARMING_POTION));
    private static final BrewingRecipeData STRONG_LIGHT_HARMING_POTION = register(new BrewingRecipeData(Potions.STRONG_HARMING, ModItems.ESSENCE_LIGHT, ModPotions.STRONG_LIGHT_HARMING_POTION));
    private static final BrewingRecipeData STRONG_FLORA_HARMING_POTION = register(new BrewingRecipeData(Potions.STRONG_HARMING, ModItems.ESSENCE_FLORA, ModPotions.STRONG_FLORA_HARMING_POTION));
    private static final BrewingRecipeData STRONG_WIND_HARMING_POTION = register(new BrewingRecipeData(Potions.STRONG_HARMING, ModItems.ESSENCE_WIND, ModPotions.STRONG_WIND_HARMING_POTION));
    private static final BrewingRecipeData STRONG_EARTH_HARMING_POTION = register(new BrewingRecipeData(Potions.STRONG_HARMING, ModItems.ESSENCE_EARTH, ModPotions.STRONG_EARTH_HARMING_POTION));

    private static final BrewingRecipeData STRONG_FIRE_HARMING_POTION_2 = register(new BrewingRecipeData(ModPotions.FIRE_HARMING_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_FIRE_HARMING_POTION));
    private static final BrewingRecipeData STRONG_ICE_HARMING_POTION_2 = register(new BrewingRecipeData(ModPotions.ICE_HARMING_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_ICE_HARMING_POTION));
    private static final BrewingRecipeData STRONG_THUNDER_HARMING_POTION_2 = register(new BrewingRecipeData(ModPotions.THUNDER_HARMING_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_THUNDER_HARMING_POTION));
    private static final BrewingRecipeData STRONG_WATER_HARMING_POTION_2 = register(new BrewingRecipeData(ModPotions.WATER_HARMING_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_WATER_HARMING_POTION));
    private static final BrewingRecipeData STRONG_DARKNESS_HARMING_POTION_2 = register(new BrewingRecipeData(ModPotions.DARKNESS_HARMING_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_DARKNESS_HARMING_POTION));
    private static final BrewingRecipeData STRONG_LIGHT_HARMING_POTION_2 = register(new BrewingRecipeData(ModPotions.LIGHT_HARMING_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_LIGHT_HARMING_POTION));
    private static final BrewingRecipeData STRONG_FLORA_HARMING_POTION_2 = register(new BrewingRecipeData(ModPotions.FLORA_HARMING_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_FLORA_HARMING_POTION));
    private static final BrewingRecipeData STRONG_WIND_HARMING_POTION_2 = register(new BrewingRecipeData(ModPotions.WIND_HARMING_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_WIND_HARMING_POTION));
    private static final BrewingRecipeData STRONG_EARTH_HARMING_POTION_2 = register(new BrewingRecipeData(ModPotions.EARTH_HARMING_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_EARTH_HARMING_POTION));

    private static final BrewingRecipeData FIRE_RESISTANCE_POTION = register(new BrewingRecipeData(Potions.AWKWARD, ModItems.ESSENCE_FIRE, Potions.FIRE_RESISTANCE));
    private static final BrewingRecipeData ICE_RESISTANCE_POTION = register(new BrewingRecipeData(Potions.AWKWARD, ModItems.ESSENCE_ICE, ModPotions.ICE_RESISTANCE_POTION));
    private static final BrewingRecipeData THUNDER_RESISTANCE_POTION = register(new BrewingRecipeData(Potions.AWKWARD, ModItems.ESSENCE_THUNDER, ModPotions.THUNDER_RESISTANCE_POTION));
    private static final BrewingRecipeData WATER_RESISTANCE_POTION = register(new BrewingRecipeData(Potions.AWKWARD, ModItems.ESSENCE_WATER, ModPotions.WATER_RESISTANCE_POTION));
    private static final BrewingRecipeData DARKNESS_RESISTANCE_POTION = register(new BrewingRecipeData(Potions.AWKWARD, ModItems.ESSENCE_DARKNESS, ModPotions.DARKNESS_RESISTANCE_POTION));
    private static final BrewingRecipeData LIGHT_RESISTANCE_POTION = register(new BrewingRecipeData(Potions.AWKWARD, ModItems.ESSENCE_LIGHT, ModPotions.LIGHT_RESISTANCE_POTION));
    private static final BrewingRecipeData FLORA_RESISTANCE_POTION = register(new BrewingRecipeData(Potions.AWKWARD, ModItems.ESSENCE_FLORA, ModPotions.FLORA_RESISTANCE_POTION));
    private static final BrewingRecipeData WIND_RESISTANCE_POTION = register(new BrewingRecipeData(Potions.AWKWARD, ModItems.ESSENCE_WIND, ModPotions.WIND_RESISTANCE_POTION));
    private static final BrewingRecipeData EARTH_RESISTANCE_POTION = register(new BrewingRecipeData(Potions.AWKWARD, ModItems.ESSENCE_EARTH, ModPotions.EARTH_RESISTANCE_POTION));

    private static final BrewingRecipeData FIRE_RESISTANCE_POTION_STRONG = register(new BrewingRecipeData(Potions.FIRE_RESISTANCE, Items.GLOWSTONE_DUST, ModPotions.FIRE_RESISTANCE_POTION_STRONG));
    private static final BrewingRecipeData ICE_RESISTANCE_POTION_STRONG = register(new BrewingRecipeData(ModPotions.ICE_RESISTANCE_POTION, Items.GLOWSTONE_DUST, ModPotions.ICE_RESISTANCE_POTION_STRONG));
    private static final BrewingRecipeData THUNDER_RESISTANCE_POTION_STRONG = register(new BrewingRecipeData(ModPotions.THUNDER_RESISTANCE_POTION, Items.GLOWSTONE_DUST, ModPotions.THUNDER_RESISTANCE_POTION_STRONG));
    private static final BrewingRecipeData WATER_RESISTANCE_POTION_STRONG = register(new BrewingRecipeData(ModPotions.WATER_RESISTANCE_POTION, Items.GLOWSTONE_DUST, ModPotions.WATER_RESISTANCE_POTION_STRONG));
    private static final BrewingRecipeData DARKNESS_RESISTANCE_POTION_STRONG = register(new BrewingRecipeData(ModPotions.DARKNESS_RESISTANCE_POTION, Items.GLOWSTONE_DUST, ModPotions.DARKNESS_RESISTANCE_POTION_STRONG));
    private static final BrewingRecipeData LIGHT_RESISTANCE_POTION_STRONG = register(new BrewingRecipeData(ModPotions.LIGHT_RESISTANCE_POTION, Items.GLOWSTONE_DUST, ModPotions.LIGHT_RESISTANCE_POTION_STRONG));
    private static final BrewingRecipeData FLORA_RESISTANCE_POTION_STRONG = register(new BrewingRecipeData(ModPotions.FLORA_RESISTANCE_POTION, Items.GLOWSTONE_DUST, ModPotions.FLORA_RESISTANCE_POTION_STRONG));
    private static final BrewingRecipeData WIND_RESISTANCE_POTION_STRONG = register(new BrewingRecipeData(ModPotions.WIND_RESISTANCE_POTION, Items.GLOWSTONE_DUST, ModPotions.WIND_RESISTANCE_POTION_STRONG));
    private static final BrewingRecipeData EARTH_RESISTANCE_POTION_STRONG = register(new BrewingRecipeData(ModPotions.EARTH_RESISTANCE_POTION, Items.GLOWSTONE_DUST, ModPotions.EARTH_RESISTANCE_POTION_STRONG));

    private static final BrewingRecipeData ICE_RESISTANCE_POTION_LONG = register(new BrewingRecipeData(ModPotions.ICE_RESISTANCE_POTION, Items.REDSTONE, ModPotions.ICE_RESISTANCE_POTION_LONG));
    private static final BrewingRecipeData THUNDER_RESISTANCE_POTION_LONG = register(new BrewingRecipeData(ModPotions.THUNDER_RESISTANCE_POTION, Items.REDSTONE, ModPotions.THUNDER_RESISTANCE_POTION_LONG));
    private static final BrewingRecipeData WATER_RESISTANCE_POTION_LONG = register(new BrewingRecipeData(ModPotions.WATER_RESISTANCE_POTION, Items.REDSTONE, ModPotions.WATER_RESISTANCE_POTION_LONG));
    private static final BrewingRecipeData DARKNESS_RESISTANCE_POTION_LONG = register(new BrewingRecipeData(ModPotions.DARKNESS_RESISTANCE_POTION, Items.REDSTONE, ModPotions.DARKNESS_RESISTANCE_POTION_LONG));
    private static final BrewingRecipeData LIGHT_RESISTANCE_POTION_LONG = register(new BrewingRecipeData(ModPotions.LIGHT_RESISTANCE_POTION, Items.REDSTONE, ModPotions.LIGHT_RESISTANCE_POTION_LONG));
    private static final BrewingRecipeData FLORA_RESISTANCE_POTION_LONG = register(new BrewingRecipeData(ModPotions.FLORA_RESISTANCE_POTION, Items.REDSTONE, ModPotions.FLORA_RESISTANCE_POTION_LONG));
    private static final BrewingRecipeData WIND_RESISTANCE_POTION_LONG = register(new BrewingRecipeData(ModPotions.WIND_RESISTANCE_POTION, Items.REDSTONE, ModPotions.WIND_RESISTANCE_POTION_LONG));
    private static final BrewingRecipeData EARTH_RESISTANCE_POTION_LONG = register(new BrewingRecipeData(ModPotions.EARTH_RESISTANCE_POTION, Items.REDSTONE, ModPotions.EARTH_RESISTANCE_POTION_LONG));


    private static BrewingRecipeData register(BrewingRecipeData recipe) {
        recipes.add(recipe);
        return recipe;
    }

    public static void register(Consumer<BrewingRecipeData> registerConsumer) {
        recipes.forEach(recipe -> registerConsumer.accept(recipe));
    }

}
