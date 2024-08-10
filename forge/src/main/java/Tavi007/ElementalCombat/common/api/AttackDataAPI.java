package Tavi007.ElementalCombat.common.api;

import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.data.DatapackDataAccessor;
import Tavi007.ElementalCombat.common.util.ElementalCombatNBTHelper;
import Tavi007.ElementalCombat.common.util.NetworkHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class AttackDataAPI {

    //////////////////
    // LivingEntity //
    //////////////////

    /**
     * Get a copy of the fully merged {@link AttackLayer} of the {@link LivingEntity}.
     * Use this to get the attack properties, which will be used in combat.
     *
     * @param entity A LivingEntity.
     */
    public static AttackLayer getFullDataAsLayer(LivingEntity entity) {
        return new AttackLayer(DatapackDataAccessor.get(entity).toLayer());
    }

    /**
     * Get acopy of the {@link AttackLayer} of the {@link LivingEntity} at the {@link ResourceLocation}.
     * Use {@link AttackDataAPI#putLayer(LivingEntity, AttackLayer, ResourceLocation)} to apply changes.
     *
     * @param entity   A LivingEntity.
     * @param location The ResourceLocation.
     */
    public static AttackLayer getLayer(LivingEntity entity, ResourceLocation location) {
        return new AttackLayer(DatapackDataAccessor.get(entity).getLayer(location));
    }

    /**
     * Set the {@link AttackLayer} of the {@link LivingEntity} at the {@link ResourceLocation}. This method will also update client side.
     *
     * @param entity   A LivingEntity.
     * @param location The ResourceLocation.
     * @param layer    The attack layer to be set.
     */
    public static void putLayer(LivingEntity entity, AttackLayer layer, ResourceLocation location) {
        DatapackDataAccessor.get(entity).putLayer(location, layer);
        if (!entity.level().isClientSide) {
            NetworkHelper.syncAttackLayerMessageForClients(entity, layer, location);
        }
    }

    /**
     * Delete the {@link AttackLayer} of the {@link LivingEntity} at the {@link ResourceLocation}. This method will also update client side.
     *
     * @param entity   A LivingEntity.
     * @param location The ResourceLocation.
     */
    public static void deleteLayer(LivingEntity entity, ResourceLocation location) {
        AttackLayer layer = new AttackLayer();
        DatapackDataAccessor.get(entity).putLayer(location, layer);
        if (!entity.level().isClientSide) {
            NetworkHelper.syncAttackLayerMessageForClients(entity, layer, location);
        }
    }

    /**
     * Writes the attack data to the {@link CompoundTag} of the {@link LivingEntity}.
     *
     * @param entity A LivingEntity.
     * @param nbt    The CompoundTag.
     */
    public static void writeToNBT(CompoundTag nbt, LivingEntity entity) {
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, DatapackDataAccessor.get(entity));
    }

    /**
     * Reads the attack data from the {@link CompoundTag} and updates the {@link LivingEntity}.
     *
     * @param entity A LivingEntity.
     * @param nbt    The CompoundTag.
     */
    public static void readFromNBT(CompoundTag nbt, LivingEntity entity) {
        DatapackDataAccessor.get(entity).set(ElementalCombatNBTHelper.readAttackDataFromNBT(nbt));
    }

    //////////////////////
    // Projectile //
    //////////////////////

    /**
     * Get a copy of the fully merged {@link AttackLayer} of the {@link Projectile}.
     * Use this to get the attack properties, which will be used in combat.
     *
     * @param entity A Projectile.
     */
    public static AttackLayer getFullDataAsLayer(Projectile entity) {
        return new AttackLayer(DatapackDataAccessor.get(entity).toLayer());
    }

    /**
     * Get a copy of the {@link AttackLayer} of the {@link Projectile} at the {@link ResourceLocation}.
     * Use {@link AttackDataAPI#putLayer(Projectile, AttackLayer, ResourceLocation)} to apply changes.
     *
     * @param entity   A Projectile.
     * @param location The ResourceLocation.
     */
    public static AttackLayer getLayer(Projectile entity, ResourceLocation location) {
        return new AttackLayer(DatapackDataAccessor.get(entity).getLayer(location));
    }

    /**
     * Set the {@link AttackLayer} of the {@link Projectile} at the {@link ResourceLocation}.
     *
     * @param entity   A Projectile.
     * @param location The ResourceLocation.
     * @param layer    The attack layer to be set.
     */
    public static void putLayer(Projectile entity, AttackLayer layer, ResourceLocation location) {
        DatapackDataAccessor.get(entity).putLayer(location, layer);
    }

    /**
     * Delete the {@link AttackLayer} of the {@link Projectile} at the {@link ResourceLocation}.
     *
     * @param entity   A LivProjectileingEntity.
     * @param location The ResourceLocation.
     */
    public static void deleteLayer(Projectile entity, ResourceLocation location) {
        DatapackDataAccessor.get(entity).putLayer(location, new AttackLayer());
    }

    /**
     * Writes the attack data to the {@link CompoundTag} of the {@link Projectile}.
     *
     * @param entity A Projectile.
     * @param nbt    The CompoundTag.
     */
    public static void writeToNBT(CompoundTag nbt, Projectile entity) {
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, DatapackDataAccessor.get(entity));
    }

    /**
     * Reads the attack data from the {@link CompoundTag} and updates the {@link Projectile}.
     *
     * @param entity A Projectile.
     * @param nbt    The CompoundTag.
     */
    public static void readFromNBT(CompoundTag nbt, Projectile entity) {
        DatapackDataAccessor.get(entity).set(ElementalCombatNBTHelper.readAttackDataFromNBT(nbt));
    }

    ///////////////
    // Itemstack //
    ///////////////

    /**
     * Get a copy of the fully merged {@link AttackLayer} of the {@link ItemStack}.
     * Use this to get the attack properties, which will be used in combat.
     *
     * @param stack A ItemStack.
     */
    public static AttackLayer getFullDataAsLayer(ItemStack stack) {
        return new AttackLayer(DatapackDataAccessor.get(stack).toLayer());
    }

    /**
     * Get a copy of the {@link AttackLayer} of the {@link ItemStack} at the {@link ResourceLocation}.
     * Use {@link AttackDataAPI#putLayer(ItemStack, AttackLayer, ResourceLocation, LivingEntity)} to apply changes.
     *
     * @param stack    A ItemStack.
     * @param location The ResourceLocation.
     */
    public static AttackLayer getLayer(ItemStack stack, ResourceLocation location) {
        return new AttackLayer(DatapackDataAccessor.get(stack).getLayer(location));
    }

    /**
     * Set the {@link AttackLayer} of the {@link ItemStack} at the {@link ResourceLocation}. This method will also update client side.
     *
     * @param stack    A ItemStack.
     * @param location The ResourceLocation.
     * @param layer    The attack layer to be set.
     * @param entity   The LivingEntity, which might be holding the ItemStack. Needed to update 'item'-layer of the entity.
     */
    public static void putLayer(ItemStack stack, AttackLayer layer, ResourceLocation location, @Nullable LivingEntity entity) {
        DatapackDataAccessor.get(stack).putLayer(location, layer);
        if (entity != null) {
            DatapackDataAccessor.updateItemLayer(entity);
        }
    }

    /**
     * Delete the {@link AttackLayer} of the {@link ItemStack} at the {@link ResourceLocation}. This method will also update clients.
     *
     * @param stack    A ItemStack.
     * @param location The ResourceLocation.
     */
    public static void deleteLayer(ItemStack stack, ResourceLocation location, @Nullable LivingEntity entity) {
        AttackLayer layer = new AttackLayer();
        DatapackDataAccessor.get(stack).putLayer(location, layer);
        if (entity != null) {
            DatapackDataAccessor.updateItemLayer(entity);
        }
    }

    /**
     * Writes the attack data to the {@link CompoundTag} of the {@link ItemStack}.
     *
     * @param stack A ItemStack.
     * @param nbt   The CompoundTag.
     */
    public static void writeToNBT(CompoundTag nbt, ItemStack stack) {
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, DatapackDataAccessor.get(stack));
    }

    /**
     * Reads the attack data from the {@link CompoundTag} and updates the {@link ItemStack}.
     *
     * @param stack A ItemStack.
     * @param nbt   The CompoundTag.
     */
    public static void readFromNBT(CompoundTag nbt, ItemStack stack) {
        DatapackDataAccessor.get(stack).set(ElementalCombatNBTHelper.readAttackDataFromNBT(nbt));
    }

    //////////////////
    // DamageSource //
    //////////////////

    /**
     * Get a copy of the fully merged {@link AttackLayer} of the {@link DamageSource}.
     * Use this to get the attack properties, which will be used in combat.
     *
     * @param source A DamageSource.
     */
    public static AttackLayer getFullDataAsLayer(DamageSource source) {
        return new AttackLayer(DatapackDataAccessor.get(source).toLayer());
    }
}
