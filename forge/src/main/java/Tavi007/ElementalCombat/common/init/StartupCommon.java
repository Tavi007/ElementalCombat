package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.capabilities.AttackDataCapability;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.capabilities.DefenseDataCapability;
import Tavi007.ElementalCombat.common.capabilities.ImmersionDataCapability;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
import Tavi007.ElementalCombat.common.network.PacketManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class StartupCommon {

    @SubscribeEvent
    public static void onRegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
        // capabilities
        ImmersionDataCapability.register(event);
        AttackDataCapability.register(event);
        DefenseDataCapability.register(event);

        CapabilitiesAccessors.setMobImmersionDataAccessor((entity) -> {
            if (entity == null) {
                return new ImmersionData();
            }
            ImmersionData immersionData = entity.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null).orElse(new ImmersionData());
            return immersionData;
        });

        CapabilitiesAccessors.setMobAttackDataAccessor((entity) -> {
            if (entity == null) {
                return new AttackData();
            }
            AttackData attackData = entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
//            if (!attackData.isInitialized()) {
//                attackData.initialize(entity);
//            }
            return attackData;
        });

        CapabilitiesAccessors.setItemAttackDataAccessor((stack) -> {
            if (stack == null) {
                return new AttackData();
            }
            AttackData attackData = stack.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
//            if (!attackData.isInitialized()) {
//                attackData.initialize(stack);
//            }
//            if (!attackData.areEnchantmentChangesApplied()) {
//                attackData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
//            }
            return attackData;
        });

        CapabilitiesAccessors.setProjectileAttackDataAccessor((projectile) -> {
            if (projectile == null) {
                return new AttackData();
            }
            AttackData attackData = projectile.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
//            if (!attackData.isInitialized()) {
//                attackData.initialize(stack);
//            }
//            if (!attackData.areEnchantmentChangesApplied()) {
//                attackData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
//            }
            return attackData;
        });

        CapabilitiesAccessors.setMobDefenseDataAccessor((entity) -> {
            if (entity == null) {
                return new DefenseData();
            }
            DefenseData defenseData = entity.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
//            if (!defenseData.isInitialized()) {
//                defenseData.initialize(entity);
//            }
            return defenseData;
        });

        CapabilitiesAccessors.setItemDefenseDataAccessor((stack) -> {
            if (stack == null) {
                return new DefenseData();
            }
            DefenseData defenseData = stack.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
//            if (!defenseData.isInitialized()) {
//                defenseData.initialize(stack);
//            }
//            if (!defenseData.areEnchantmentChangesApplied()) {
//                defenseData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
//            }
            return defenseData;
        });
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        registerBrewingRecipes();
        registerNetworking();
        Constants.LOG.info("setup method registered.");
    }

    private static void registerBrewingRecipes() {
        register(Potions.HARMING, ItemList.ESSENCE_FIRE.get(), PotionList.FIRE_HARMING_POTION.get());
        register(Potions.HARMING, ItemList.ESSENCE_ICE.get(), PotionList.ICE_HARMING_POTION.get());
        register(Potions.HARMING, ItemList.ESSENCE_THUNDER.get(), PotionList.THUNDER_HARMING_POTION.get());
        register(Potions.HARMING, ItemList.ESSENCE_WATER.get(), PotionList.WATER_HARMING_POTION.get());
        register(Potions.HARMING, ItemList.ESSENCE_DARKNESS.get(), PotionList.DARKNESS_HARMING_POTION.get());
        register(Potions.HARMING, ItemList.ESSENCE_LIGHT.get(), PotionList.LIGHT_HARMING_POTION.get());
        register(Potions.HARMING, ItemList.ESSENCE_FLORA.get(), PotionList.FLORA_HARMING_POTION.get());
        register(Potions.HARMING, ItemList.ESSENCE_WIND.get(), PotionList.WIND_HARMING_POTION.get());
        register(Potions.HARMING, ItemList.ESSENCE_EARTH.get(), PotionList.EARTH_HARMING_POTION.get());

        register(Potions.STRONG_HARMING, ItemList.ESSENCE_FIRE.get(), PotionList.STRONG_FIRE_HARMING_POTION.get());
        register(Potions.STRONG_HARMING, ItemList.ESSENCE_ICE.get(), PotionList.STRONG_ICE_HARMING_POTION.get());
        register(Potions.STRONG_HARMING, ItemList.ESSENCE_THUNDER.get(), PotionList.STRONG_THUNDER_HARMING_POTION.get());
        register(Potions.STRONG_HARMING, ItemList.ESSENCE_WATER.get(), PotionList.STRONG_WATER_HARMING_POTION.get());
        register(Potions.STRONG_HARMING, ItemList.ESSENCE_DARKNESS.get(), PotionList.STRONG_DARKNESS_HARMING_POTION.get());
        register(Potions.STRONG_HARMING, ItemList.ESSENCE_LIGHT.get(), PotionList.STRONG_LIGHT_HARMING_POTION.get());
        register(Potions.STRONG_HARMING, ItemList.ESSENCE_FLORA.get(), PotionList.STRONG_FLORA_HARMING_POTION.get());
        register(Potions.STRONG_HARMING, ItemList.ESSENCE_WIND.get(), PotionList.STRONG_WIND_HARMING_POTION.get());
        register(Potions.STRONG_HARMING, ItemList.ESSENCE_EARTH.get(), PotionList.STRONG_EARTH_HARMING_POTION.get());

        register(PotionList.FIRE_HARMING_POTION.get(), Items.GLOWSTONE_DUST, PotionList.STRONG_FIRE_HARMING_POTION.get());
        register(PotionList.ICE_HARMING_POTION.get(), Items.GLOWSTONE_DUST, PotionList.STRONG_ICE_HARMING_POTION.get());
        register(PotionList.THUNDER_HARMING_POTION.get(), Items.GLOWSTONE_DUST, PotionList.STRONG_THUNDER_HARMING_POTION.get());
        register(PotionList.WATER_HARMING_POTION.get(), Items.GLOWSTONE_DUST, PotionList.STRONG_WATER_HARMING_POTION.get());
        register(PotionList.DARKNESS_HARMING_POTION.get(), Items.GLOWSTONE_DUST, PotionList.STRONG_DARKNESS_HARMING_POTION.get());
        register(PotionList.LIGHT_HARMING_POTION.get(), Items.GLOWSTONE_DUST, PotionList.STRONG_LIGHT_HARMING_POTION.get());
        register(PotionList.FLORA_HARMING_POTION.get(), Items.GLOWSTONE_DUST, PotionList.STRONG_FLORA_HARMING_POTION.get());
        register(PotionList.WIND_HARMING_POTION.get(), Items.GLOWSTONE_DUST, PotionList.STRONG_WIND_HARMING_POTION.get());
        register(PotionList.EARTH_HARMING_POTION.get(), Items.GLOWSTONE_DUST, PotionList.STRONG_EARTH_HARMING_POTION.get());

        register(Potions.AWKWARD, ItemList.ESSENCE_FIRE.get(), Potions.FIRE_RESISTANCE);
        register(Potions.AWKWARD, ItemList.ESSENCE_ICE.get(), PotionList.ICE_RESISTANCE_POTION.get());
        register(Potions.AWKWARD, ItemList.ESSENCE_THUNDER.get(), PotionList.THUNDER_RESISTANCE_POTION.get());
        register(Potions.AWKWARD, ItemList.ESSENCE_WATER.get(), PotionList.WATER_RESISTANCE_POTION.get());
        register(Potions.AWKWARD, ItemList.ESSENCE_DARKNESS.get(), PotionList.DARKNESS_RESISTANCE_POTION.get());
        register(Potions.AWKWARD, ItemList.ESSENCE_LIGHT.get(), PotionList.LIGHT_RESISTANCE_POTION.get());
        register(Potions.AWKWARD, ItemList.ESSENCE_FLORA.get(), PotionList.FLORA_RESISTANCE_POTION.get());
        register(Potions.AWKWARD, ItemList.ESSENCE_WIND.get(), PotionList.WIND_RESISTANCE_POTION.get());
        register(Potions.AWKWARD, ItemList.ESSENCE_EARTH.get(), PotionList.EARTH_RESISTANCE_POTION.get());

        register(Potions.FIRE_RESISTANCE, Items.GLOWSTONE_DUST, PotionList.FIRE_RESISTANCE_POTION_STRONG.get());
        register(PotionList.ICE_RESISTANCE_POTION.get(), Items.GLOWSTONE_DUST, PotionList.ICE_RESISTANCE_POTION_STRONG.get());
        register(PotionList.THUNDER_RESISTANCE_POTION.get(), Items.GLOWSTONE_DUST, PotionList.THUNDER_RESISTANCE_POTION_STRONG.get());
        register(PotionList.WATER_RESISTANCE_POTION.get(), Items.GLOWSTONE_DUST, PotionList.WATER_RESISTANCE_POTION_STRONG.get());
        register(PotionList.DARKNESS_RESISTANCE_POTION.get(), Items.GLOWSTONE_DUST, PotionList.DARKNESS_RESISTANCE_POTION_STRONG.get());
        register(PotionList.LIGHT_RESISTANCE_POTION.get(), Items.GLOWSTONE_DUST, PotionList.LIGHT_RESISTANCE_POTION_STRONG.get());
        register(PotionList.FLORA_RESISTANCE_POTION.get(), Items.GLOWSTONE_DUST, PotionList.FLORA_RESISTANCE_POTION_STRONG.get());
        register(PotionList.WIND_RESISTANCE_POTION.get(), Items.GLOWSTONE_DUST, PotionList.WIND_RESISTANCE_POTION_STRONG.get());
        register(PotionList.EARTH_RESISTANCE_POTION.get(), Items.GLOWSTONE_DUST, PotionList.EARTH_RESISTANCE_POTION_STRONG.get());

        register(Potions.FIRE_RESISTANCE, Items.REDSTONE, Potions.LONG_FIRE_RESISTANCE);
        register(PotionList.ICE_RESISTANCE_POTION.get(), Items.REDSTONE, PotionList.ICE_RESISTANCE_POTION_LONG.get());
        register(PotionList.THUNDER_RESISTANCE_POTION.get(), Items.REDSTONE, PotionList.THUNDER_RESISTANCE_POTION_LONG.get());
        register(PotionList.WATER_RESISTANCE_POTION.get(), Items.REDSTONE, PotionList.WATER_RESISTANCE_POTION_LONG.get());
        register(PotionList.DARKNESS_RESISTANCE_POTION.get(), Items.REDSTONE, PotionList.DARKNESS_RESISTANCE_POTION_LONG.get());
        register(PotionList.LIGHT_RESISTANCE_POTION.get(), Items.REDSTONE, PotionList.LIGHT_RESISTANCE_POTION_LONG.get());
        register(PotionList.FLORA_RESISTANCE_POTION.get(), Items.REDSTONE, PotionList.FLORA_RESISTANCE_POTION_LONG.get());
        register(PotionList.WIND_RESISTANCE_POTION.get(), Items.REDSTONE, PotionList.WIND_RESISTANCE_POTION_LONG.get());
        register(PotionList.EARTH_RESISTANCE_POTION.get(), Items.REDSTONE, PotionList.EARTH_RESISTANCE_POTION_LONG.get());
    }

    private static void register(Potion from, Item with, Potion to) {
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), from)),
                Ingredient.of(new ItemStack(with)),
                PotionUtils.setPotion(new ItemStack(Items.POTION), to));
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), from)),
                Ingredient.of(new ItemStack(with)),
                PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), to));
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), from)),
                Ingredient.of(new ItemStack(with)),
                PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), to));
    }

    private static void registerNetworking() {
        PacketManager.init();
    }

}
