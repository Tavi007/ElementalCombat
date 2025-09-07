package Tavi007.ElementalCombat.common.registry;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Function4;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public class ConditionRegistry {

    private static final Map<String, Function4<Entity, ItemStack, Level, JsonObject, Boolean>> register = new HashMap<String, Function4<Entity, ItemStack, Level, JsonObject, Boolean>>();

    static {
        // Base
        ConditionRegistry.register("base", (entity, stack, level, arguments) -> {
            return true;
        });

        // Weather
        ConditionRegistry.register("is_raining", (entity, stack, level, arguments) -> {
            return level != null && level.isRaining();
        });
        ConditionRegistry.register("is_thundering", (entity, stack, level, arguments) -> {
            return level != null && level.isThundering();
        });
        ConditionRegistry.register("is_day", (entity, stack, level, arguments) -> {
            return level != null && level.isDay();
        });
        ConditionRegistry.register("is_night", (entity, stack, level, arguments) -> {
            return level != null && level.isNight();
        });

        // Entity State
        ConditionRegistry.register("is_freezing", (entity, stack, level, arguments) -> {
            return entity != null && entity.isFreezing();
        });
        ConditionRegistry.register("is_in_water", (entity, stack, level, arguments) -> {
            return entity != null && entity.isInWater();
        });
        ConditionRegistry.register("is_in_lava", (entity, stack, level, arguments) -> {
            return entity != null && entity.isInLava();
        });
        ConditionRegistry.register("is_on_fire", (entity, stack, level, arguments) -> {
            return entity != null && entity.isOnFire();
        });
        ConditionRegistry.register("is_blocking", (entity, stack, level, arguments) -> {
            if (entity instanceof LivingEntity mob) {
                return mob.isBlocking();
            }
            return false;
        });
        // TODO: nbt check
        // TODO: tag check

        // Entity Actions
        ConditionRegistry.register("is_swimming", (entity, stack, level, arguments) -> {
            return entity != null && entity.isSwimming();
        });
        ConditionRegistry.register("is_crouching", (entity, stack, level, arguments) -> {
            return entity != null && entity.isCrouching();
        });
        ConditionRegistry.register("is_sprinting", (entity, stack, level, arguments) -> {
            return entity != null && entity.isSprinting();
        });
        ConditionRegistry.register("is_descending", (entity, stack, level, arguments) -> {
            return entity != null && entity.isDescending();
        });


        // Biome
        // TODO: Temperature check with custom value
        ConditionRegistry.register("is_in_hot_biome", (entity, stack, level, arguments) -> {
            if (entity != null && level != null) {
                Biome biome = level.getBiome(entity.blockPosition()).value();
                return biome.getBaseTemperature() <= 0.2f;
            }
            return false;
        });
        // TODO: tag check


        // ItemStack State
        // TODO: tag check

    }

    public static void register(String id, Function4<Entity, ItemStack, Level, JsonObject, Boolean> check) {
        ConditionRegistry.register.put(id, check);
    }

    public static Function4<Entity, ItemStack, Level, JsonObject, Boolean> get(String id) {
        return ConditionRegistry.register.get(id);
    }
}
