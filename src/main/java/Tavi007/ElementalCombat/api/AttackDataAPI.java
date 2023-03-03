package Tavi007.ElementalCombat.api;

import javax.annotation.Nullable;

import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.util.AttackDataHelper;
import Tavi007.ElementalCombat.util.ElementalCombatNBTHelper;
import Tavi007.ElementalCombat.util.NetworkHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class AttackDataAPI {

    //////////////////
    // LivingEntity //
    //////////////////

    /**
     * Get a copy of the fully merged {@link AttackLayer} of the {@link LivingEntity}.
     * Use this to get the attack properties, which will be used in combat.
     * 
     * @param entity
     *            A LivingEntity.
     * @param location
     *            The ResourceLocation.
     */
    public static AttackLayer getFullDataAsLayer(LivingEntity entity) {
        return new AttackLayer(AttackDataHelper.get(entity).toLayer());
    }

    /**
     * Get acopy of the {@link AttackLayer} of the {@link LivingEntity} at the {@link ResourceLocation}.
     * Use {@link AttackDataAPI#putLayer(LivingEntity, AttackLayer, ResourceLocation)} to apply changes.
     * 
     * @param entity
     *            A LivingEntity.
     * @param location
     *            The ResourceLocation.
     */
    public static AttackLayer getLayer(LivingEntity entity, ResourceLocation location) {
        return new AttackLayer(AttackDataHelper.get(entity).getLayer(location));
    }

    /**
     * Set the {@link AttackLayer} of the {@link LivingEntity} at the {@link ResourceLocation}. This method will also update client side.
     * 
     * @param entity
     *            A LivingEntity.
     * @param location
     *            The ResourceLocation.
     * @param layer
     *            The attack layer to be set.
     */
    public static void putLayer(LivingEntity entity, AttackLayer layer, ResourceLocation location) {
        AttackDataHelper.get(entity).putLayer(location, layer);
        if (!entity.level.isClientSide) {
            NetworkHelper.syncAttackLayerMessageForClients(entity, layer, location);
        }
    }

    /**
     * Delete the {@link AttackLayer} of the {@link LivingEntity} at the {@link ResourceLocation}. This method will also update client side.
     * 
     * @param entity
     *            A LivingEntity.
     * @param location
     *            The ResourceLocation.
     */
    public static void deleteLayer(LivingEntity entity, ResourceLocation location) {
        AttackLayer layer = new AttackLayer();
        AttackDataHelper.get(entity).putLayer(location, layer);
        if (!entity.level.isClientSide) {
            NetworkHelper.syncAttackLayerMessageForClients(entity, layer, location);
        }
    }

    /**
     * Writes the attack data to the {@link CompoundNBT} of the {@link LivingEntity}.
     * 
     * @param entity
     *            A LivingEntity.
     * @param nbt
     *            The CompoundNBT.
     */
    public static void writeToNBT(CompoundNBT nbt, LivingEntity entity) {
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, AttackDataHelper.get(entity));
    }

    /**
     * Reads the attack data from the {@link CompoundNBT} and updates the {@link LivingEntity}.
     * 
     * @param entity
     *            A LivingEntity.
     * @param nbt
     *            The CompoundNBT.
     */
    public static void readFromNBT(CompoundNBT nbt, LivingEntity entity) {
        AttackDataHelper.get(entity).set(ElementalCombatNBTHelper.readAttackDataFromNBT(nbt));
    }

    //////////////////////
    // ProjectileEntity //
    //////////////////////

    /**
     * Get a copy of the fully merged {@link AttackLayer} of the {@link ProjectileEntity}.
     * Use this to get the attack properties, which will be used in combat.
     * 
     * @param entity
     *            A ProjectileEntity.
     * @param location
     *            The ResourceLocation.
     */
    public static AttackLayer getFullDataAsLayer(ProjectileEntity entity) {
        return new AttackLayer(AttackDataHelper.get(entity).toLayer());
    }

    /**
     * Get a copy of the {@link AttackLayer} of the {@link ProjectileEntity} at the {@link ResourceLocation}.
     * Use {@link AttackDataAPI#putLayer(ProjectileEntity, AttackLayer, ResourceLocation)} to apply changes.
     * 
     * @param entity
     *            A ProjectileEntity.
     * @param location
     *            The ResourceLocation.
     */
    public static AttackLayer getLayer(ProjectileEntity entity, ResourceLocation location) {
        return new AttackLayer(AttackDataHelper.get(entity).getLayer(location));
    }

    /**
     * Set the {@link AttackLayer} of the {@link ProjectileEntity} at the {@link ResourceLocation}.
     * 
     * @param entity
     *            A ProjectileEntity.
     * @param location
     *            The ResourceLocation.
     * @param layer
     *            The attack layer to be set.
     */
    public static void putLayer(ProjectileEntity entity, AttackLayer layer, ResourceLocation location) {
        AttackDataHelper.get(entity).putLayer(location, layer);
    }

    /**
     * Delete the {@link AttackLayer} of the {@link ProjectileEntity} at the {@link ResourceLocation}.
     * 
     * @param entity
     *            A LivProjectileEntityingEntity.
     * @param location
     *            The ResourceLocation.
     */
    public static void deleteLayer(ProjectileEntity entity, ResourceLocation location) {
        AttackDataHelper.get(entity).putLayer(location, new AttackLayer());
    }

    /**
     * Writes the attack data to the {@link CompoundNBT} of the {@link ProjectileEntity}.
     * 
     * @param entity
     *            A ProjectileEntity.
     * @param nbt
     *            The CompoundNBT.
     */
    public static void writeToNBT(CompoundNBT nbt, ProjectileEntity entity) {
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, AttackDataHelper.get(entity));
    }

    /**
     * Reads the attack data from the {@link CompoundNBT} and updates the {@link ProjectileEntity}.
     * 
     * @param entity
     *            A ProjectileEntity.
     * @param nbt
     *            The CompoundNBT.
     */
    public static void readFromNBT(CompoundNBT nbt, ProjectileEntity entity) {
        AttackDataHelper.get(entity).set(ElementalCombatNBTHelper.readAttackDataFromNBT(nbt));
    }

    ///////////////
    // Itemstack //
    ///////////////

    /**
     * Get a copy of the fully merged {@link AttackLayer} of the {@link ItemStack}.
     * Use this to get the attack properties, which will be used in combat.
     * 
     * @param stack
     *            A ItemStack.
     * @param location
     *            The ResourceLocation.
     */
    public static AttackLayer getFullDataAsLayer(ItemStack stack) {
        return new AttackLayer(AttackDataHelper.get(stack).toLayer());
    }

    /**
     * Get a copy of the {@link AttackLayer} of the {@link ItemStack} at the {@link ResourceLocation}.
     * Use {@link AttackDataAPI#putLayer(ItemStack, AttackLayer, ResourceLocation, LivingEntity)} to apply changes.
     * 
     * @param stack
     *            A ItemStack.
     * @param location
     *            The ResourceLocation.
     */
    public static AttackLayer getLayer(ItemStack stack, ResourceLocation location) {
        return new AttackLayer(AttackDataHelper.get(stack).getLayer(location));
    }

    /**
     * Set the {@link AttackLayer} of the {@link ItemStack} at the {@link ResourceLocation}. This method will also update client side.
     * 
     * @param stack
     *            A ItemStack.
     * @param location
     *            The ResourceLocation.
     * @param layer
     *            The attack layer to be set.
     * @param entity
     *            The LivingEntity, which might be holding the ItemStack. Needed to update 'item'-layer of the entity.
     */
    public static void putLayer(ItemStack stack, AttackLayer layer, ResourceLocation location, @Nullable LivingEntity entity) {
        AttackDataHelper.get(stack).putLayer(location, layer);
        if (entity != null) {
            AttackDataHelper.updateItemLayer(entity);
        }
    }

    /**
     * Delete the {@link AttackLayer} of the {@link ItemStack} at the {@link ResourceLocation}. This method will also update clients.
     * 
     * @param stack
     *            A ItemStack.
     * @param location
     *            The ResourceLocation.
     */
    public static void deleteLayer(ItemStack stack, ResourceLocation location, @Nullable LivingEntity entity) {
        AttackLayer layer = new AttackLayer();
        AttackDataHelper.get(stack).putLayer(location, layer);
        if (entity != null) {
            AttackDataHelper.updateItemLayer(entity);
        }
    }

    /**
     * Writes the attack data to the {@link CompoundNBT} of the {@link ItemStack}.
     * 
     * @param stack
     *            A ItemStack.
     * @param nbt
     *            The CompoundNBT.
     */
    public static void writeToNBT(CompoundNBT nbt, ItemStack stack) {
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, AttackDataHelper.get(stack));
    }

    /**
     * Reads the attack data from the {@link CompoundNBT} and updates the {@link ItemStack}.
     * 
     * @param stack
     *            A ItemStack.
     * @param nbt
     *            The CompoundNBT.
     */
    public static void readFromNBT(CompoundNBT nbt, ItemStack stack) {
        AttackDataHelper.get(stack).set(ElementalCombatNBTHelper.readAttackDataFromNBT(nbt));
    }
}
