package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.registry.ModMobEffects;
import Tavi007.ElementalCombat.common.registry.ModPotions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiConsumer;

public class PotionList {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Constants.MOD_ID);

    private static void registerMobEffects() {
        BiConsumer<String, MobEffect> registerConsumer = (name, effect) -> {
            MOB_EFFECTS.register(name, () -> effect);
        };
        ModMobEffects.register(registerConsumer);
    }

    private static void registerPotions() {
        BiConsumer<String, Potion> registerConsumer = (name, potion) -> {
            POTIONS.register(name, () -> potion);
        };
        ModPotions.register(registerConsumer);
    }

}
