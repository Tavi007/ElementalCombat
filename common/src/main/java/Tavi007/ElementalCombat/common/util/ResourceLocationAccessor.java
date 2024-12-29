package Tavi007.ElementalCombat.common.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Function;

public class ResourceLocationAccessor {

    private static Function<Entity, ResourceLocation> entityAccessor;
    private static Function<ItemStack, ResourceLocation> itemAccessor;
    private static Function<Biome, ResourceLocation> biomeAccessor;
    private static Function<Enchantment, ResourceLocation> enchantmentAccessor;
    private static Function<DamageSource, ResourceLocation> damageSourceAccessor;

    public static void setEntityAccessor(Function<Entity, ResourceLocation> accessor) {
        entityAccessor = accessor;
    }

    public static ResourceLocation getResourceLocation(Entity entity) {
        return entityAccessor.apply(entity);
    }

    public static void setItemAccessor(Function<ItemStack, ResourceLocation> accessor) {
        itemAccessor = accessor;
    }

    public static ResourceLocation getResourceLocation(ItemStack stack) {
        return itemAccessor.apply(stack);
    }

    public static void setBiomeAccessor(Function<Biome, ResourceLocation> accessor) {
        biomeAccessor = accessor;
    }

    public static ResourceLocation getResourceLocation(Biome biome) {
        return biomeAccessor.apply(biome);
    }

    public static void setEnchantmentAccessor(Function<Enchantment, ResourceLocation> accessor) {
        enchantmentAccessor = accessor;
    }

    public static ResourceLocation getResourceLocation(Enchantment enchantment) {
        return enchantmentAccessor.apply(enchantment);
    }

    public static void setDamageSourceAccessor(Function<DamageSource, ResourceLocation> accessor) {
        damageSourceAccessor = accessor;
    }

    public static ResourceLocation getResourceLocation(DamageSource damageSource) {
        return damageSourceAccessor.apply(damageSource);
    }
}
