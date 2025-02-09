package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.ElementifyDamageSourceEvent;
import Tavi007.ElementalCombat.common.capabilities.*;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
import Tavi007.ElementalCombat.common.network.PacketManager;
import Tavi007.ElementalCombat.common.potions.SpecificPotionIngredient;
import Tavi007.ElementalCombat.common.registry.ModBrewingRecipes;
import Tavi007.ElementalCombat.common.util.ResourceLocationAccessor;
import Tavi007.ElementalCombat.server.events.CombatEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

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
            ImmersionData immersionData = entity.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null).orElse(new ImmersionDataSerializer()).getData();
            return immersionData;
        });

        CapabilitiesAccessors.setMobAttackDataAccessor((entity) -> {
            if (entity == null) {
                return new AttackData();
            }

            AttackData attackData = entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null)
                    .orElse(new AttackDataSerializer())
                    .getData();
            if (!attackData.isInitialized()) {
                attackData.initialize(entity);
            }
            return attackData;
        });

        CapabilitiesAccessors.setItemAttackDataAccessor((stack) -> {
            if (stack == null) {
                return new AttackData();
            }
            AttackData attackData = stack.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackDataSerializer()).getData();
            if (!attackData.isInitialized()) {
                attackData.initialize(stack);
            }
            if (!attackData.areEnchantmentChangesApplied()) {
                attackData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
            }
            return attackData;
        });

        CapabilitiesAccessors.setProjectileAttackDataAccessor((projectile) -> {
            if (projectile == null) {
                return new AttackData();
            }
            AttackData attackData = projectile.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackDataSerializer()).getData();
            if (!attackData.isInitialized()) {
                attackData.initialize(projectile);
            }
            return attackData;
        });

        CapabilitiesAccessors.setMobDefenseDataAccessor((entity) -> {
            if (entity == null) {
                return new DefenseData();
            }
            DefenseData defenseData = entity.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseDataSerializer()).getData();
            if (!defenseData.isInitialized()) {
                defenseData.initialize(entity);
            }
            return defenseData;
        });

        CapabilitiesAccessors.setItemDefenseDataAccessor((stack) -> {
            if (stack == null) {
                return new DefenseData();
            }
            DefenseData defenseData = stack.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseDataSerializer()).getData();
            if (!defenseData.isInitialized()) {
                defenseData.initialize(stack);
            }
            if (!defenseData.areEnchantmentChangesApplied()) {
                defenseData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
            }
            return defenseData;
        });
    }


    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        registerBrewingRecipes();
        registerNetworking();
        registerResourceLocationAccessor();
        registerApiEvents();
        Constants.LOG.info("setup method registered.");
    }

    private static void registerBrewingRecipes() {
        ModBrewingRecipes.register(brewingData -> {
            BrewingRecipeRegistry.addRecipe(
                    new SpecificPotionIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), brewingData.getFrom())),
                    Ingredient.of(new ItemStack(brewingData.getWith())),
                    PotionUtils.setPotion(new ItemStack(Items.POTION), brewingData.getTo()));
            BrewingRecipeRegistry.addRecipe(
                    new SpecificPotionIngredient(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), brewingData.getFrom())),
                    Ingredient.of(new ItemStack(brewingData.getWith())),
                    PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), brewingData.getTo()));
            BrewingRecipeRegistry.addRecipe(
                    new SpecificPotionIngredient(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), brewingData.getFrom())),
                    Ingredient.of(new ItemStack(brewingData.getWith())),
                    PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), brewingData.getTo()));
        });
    }

    private static void registerNetworking() {
        PacketManager.init();
    }

    private static void registerResourceLocationAccessor() {
        ResourceLocationAccessor.setEntityAccessor((entity) -> {
            return ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        });
        ResourceLocationAccessor.setItemAccessor((stack) -> {
            return ForgeRegistries.ITEMS.getKey(stack.getItem());
        });
        ResourceLocationAccessor.setBiomeAccessor(ForgeRegistries.BIOMES::getKey);
        ResourceLocationAccessor.setEnchantmentAccessor(ForgeRegistries.ENCHANTMENTS::getKey);
        ResourceLocationAccessor.setDamageSourceAccessor((damageSource) -> {
            return damageSource.typeHolder()
                .unwrapKey()
                .map(ResourceKey::location)
                .orElse(new ResourceLocation("empty"));
        });
    }

    private static void registerApiEvents() {
        CombatEvents.setElementifyDamageSourceFunction((damageSource, attackData) -> {
            MinecraftForge.EVENT_BUS.post(new ElementifyDamageSourceEvent(damageSource, attackData));
            return attackData;
        });
    }

}
