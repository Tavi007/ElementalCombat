package Tavi007.ElementalCombat.common.registry;

import Tavi007.ElementalCombat.common.data.capabilities.ConditionPredicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.Map;


public class ConditionRegistry {

    private static final Map<String, ConditionPredicate> register = new HashMap<String, ConditionPredicate>();

    public static final ConditionPredicate ALWAYS_TRUE = ConditionRegistry.register("base", (entity, stack, level, arguments) -> {
        return true;
    });

    // Weather
    public static final ConditionPredicate RAINING = ConditionRegistry.register("is_raining", (entity, stack, level, arguments) -> {
        return level != null && level.isRaining();
    });
    public static final ConditionPredicate THUNDERING = ConditionRegistry.register("is_thundering", (entity, stack, level, arguments) -> {
        return level != null && level.isThundering();
    });
    public static final ConditionPredicate DAY = ConditionRegistry.register("is_day", (entity, stack, level, arguments) -> {
        return level != null && level.isDay();
    });
    public static final ConditionPredicate NIGHT = ConditionRegistry.register("is_night", (entity, stack, level, arguments) -> {
        return level != null && level.isNight();
    });

    // Entity State
    public static final ConditionPredicate FREEZING = ConditionRegistry.register("is_freezing", (entity, stack, level, arguments) -> {
        return entity != null && entity.isFreezing();
    });
    public static final ConditionPredicate IN_WATER = ConditionRegistry.register("is_in_water", (entity, stack, level, arguments) -> {
        return entity != null && entity.isInWater();
    });
    public static final ConditionPredicate IN_LAVA = ConditionRegistry.register("is_in_lava", (entity, stack, level, arguments) -> {
        return entity != null && entity.isInLava();
    });
    public static final ConditionPredicate ON_FIRE = ConditionRegistry.register("is_on_fire", (entity, stack, level, arguments) -> {
        return entity != null && entity.isOnFire();
    });
    public static final ConditionPredicate BLOCKING = ConditionRegistry.register("is_blocking", (entity, stack, level, arguments) -> {
        if (entity instanceof LivingEntity mob) {
            return mob.isBlocking();
        }
        return false;
    });
    // TODO: nbt check
    // TODO: tag check

    // Entity Actions
    public static final ConditionPredicate SWIMMING = ConditionRegistry.register("is_swimming", (entity, stack, level, arguments) -> {
        return entity != null && entity.isSwimming();
    });
    public static final ConditionPredicate CROUCHING = ConditionRegistry.register("is_crouching", (entity, stack, level, arguments) -> {
        return entity != null && entity.isCrouching();
    });
    public static final ConditionPredicate SPRINTING = ConditionRegistry.register("is_sprinting", (entity, stack, level, arguments) -> {
        return entity != null && entity.isSprinting();
    });
    public static final ConditionPredicate DESCENDING = ConditionRegistry.register("is_descending", (entity, stack, level, arguments) -> {
        return entity != null && entity.isDescending();
    });


    // Biome
    // TODO: Temperature check with custom value
    public static final ConditionPredicate IN_HOT_BIOME = ConditionRegistry.register("is_in_hot_biome", (entity, stack, level, arguments) -> {
        if (entity != null && level != null) {
            Biome biome = level.getBiome(entity.blockPosition()).value();
            return biome.getBaseTemperature() <= 0.2f;
        }
        return false;
    });
    // TODO: tag check


    // ItemStack State
    // TODO: tag check

    public static ConditionPredicate register(
            String id,
            ConditionPredicate check
    ) {
        ConditionRegistry.register.put(id, check);
        return check;
    }

    public static ConditionPredicate get(String id) {
        return ConditionRegistry.register.getOrDefault(id, ConditionRegistry.ALWAYS_TRUE);
    }
}
