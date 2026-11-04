package Tavi007.ElementalCombat.common.api;

import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackBranch;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackTree;
import Tavi007.ElementalCombat.server.network.NetworkHelper;
import com.google.common.base.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Queue;

public class AttackAPI {

    ////////////
    // Entity //
    ////////////

    /**
     * Returns True if {@link Entity} has the {@link AttackConfig} capability attached to it.
     *
     * @param entity A LivingEntity.
     */
    public static boolean hasAttackCapability(Entity entity) {
        return entity instanceof LivingEntity || entity instanceof Projectile;
    }

    /**
     * Get a copy of the fully merged {@link AttackProperty} of the {@link LivingEntity}.
     * Use this to get the attack properties, which will be used in combat.
     *
     * @param entity A LivingEntity.
     */
    public static AttackProperty getSummedProperty(LivingEntity entity) {
        AttackConfig config = CapabilitiesAccessors.getAttackConfig(entity);
        return config.getSummedProperty(entity, entity.getMainHandItem(), entity.level());
    }

    /**
     * Get an {@link AttackBranch} of the {@link Entity} by traversing its {@link AttackTree} by using a {@link ResourceLocation} queue.
     * This returns an empty Optional, in case the queue could not be fully resolved.
     * Use {@link AttackAPI#put(Entity, AttackBranch, ResourceLocation)} to apply changes.
     *
     * @param entity    A LivingEntity.
     * @param locations The ResourceLocation queue.
     */
    public static Optional<AttackBranch> get(Entity entity, Queue<ResourceLocation> locations) {
        return CapabilitiesAccessors.getAttackConfig(entity).get(locations);
    }

    /**
     * Set the {@link AttackBranch} of the {@link LivingEntity} at the end of the {@link ResourceLocation} queue.
     * This method will also update client side.
     *
     * @param entity    A LivingEntity.
     * @param locations The ResourceLocation queue.
     * @param branch    The attack layer to be set.
     */
    public static void put(Entity entity, Queue<ResourceLocation> locations, AttackBranch branch) {
        CapabilitiesAccessors.getAttackConfig(entity).put(locations, branch);
        if (!entity.level().isClientSide) {
            NetworkHelper.syncAttackLayerMessageForClients(entity, layer, location);
        }
    }

    /**
     * Delete the {@link AttackLayer} of the {@link LivingEntity} at the {@link ResourceLocation}.
     * This method will also update client side.
     *
     * @param entity   A LivingEntity.
     * @param location The ResourceLocation.
     */
    public static void remove(Entity entity, ResourceLocation location) {
        CapabilitiesAccessors.getAttackConfig(entity).remove(location);
        if (!entity.level().isClientSide) {
            NetworkHelper.syncAttackLayerMessageForClients(entity, null, location);
        }
    }


    ///////////////
    // Itemstack //
    ///////////////

    /**
     * Get a copy of the fully merged {@link AttackProperty} of the {@link ItemStack}.
     * Use this to get the attack properties, which will be used in combat.
     *
     * @param stack A ItemStack.
     */
    public static AttackProperty getSummedProperty(ItemStack stack, Level level) {
        AttackConfig config = CapabilitiesAccessors.getAttackConfig(stack);
        return config.getSummedProperty(null, stack, level);
    }

    /**
     * Get the {@link AttackLayer} of the {@link ItemStack} at the {@link ResourceLocation}.
     * Use {@link AttackAPI#put(ItemStack, AttackLayer, ResourceLocation)} to apply changes.
     *
     * @param stack    An ItemStack.
     * @param location The ResourceLocation.
     */
    public static AttackLayer get(ItemStack stack, ResourceLocation location) {
        return new AttackLayer(CapabilitiesAccessors.getAttackConfig(stack).get(location));
    }

    /**
     * Set the {@link AttackLayer} of the {@link ItemStack} at the {@link ResourceLocation}.
     * This method will also update client side.
     *
     * @param stack    An ItemStack.
     * @param location The ResourceLocation.
     * @param layer    The attack layer to be set.
     */
    public static void put(ItemStack stack, AttackLayer layer, ResourceLocation location) {
        CapabilitiesAccessors.getAttackConfig(stack).put(location, layer);
//        if (!entity.level().isClientSide) {
//            NetworkHelper.syncAttackLayerMessageForClients(entity, layer, location);
//        }
    }

    /**
     * Delete the {@link AttackLayer} of the {@link ItemStack} at the {@link ResourceLocation}.
     * This method will also update client side.
     * Returns the removed AttackLayer
     *
     * @param stack    An ItemStack.
     * @param location The ResourceLocation.
     */
    public static AttackLayer remove(ItemStack stack, ResourceLocation location) {
        return CapabilitiesAccessors.getAttackConfig(stack).remove(location);
//        if (!entity.level().isClientSide) {
//            NetworkHelper.syncAttackLayerMessageForClients(entity, null, location);
//        }
    }

    /**
     * Set the {@link AttackLayer} of the {@link ItemStack} at the {@link ResourceLocation}.
     * This method will also update client side.
     * Returns the new AttackConfig
     *
     * @param stack    A ItemStack.
     * @param location The ResourceLocation.
     * @param layer    The attack layer to be set.
     * @param entity   The LivingEntity, which might be holding the ItemStack. Needed to update 'item'-layer of the entity.
     */
    public static AttackConfig put(ItemStack stack, AttackLayer layer, ResourceLocation location, @Nullable LivingEntity entity) {
        AttackConfig itemConfig = CapabilitiesAccessors.getAttackConfig(stack).put(location, layer);
        if (entity != null) {
            AttackProperty itemProperty = itemConfig.getSummedProperty(null, stack, entity.level());
            AttackLayer itemLayer = new AttackLayer(50, itemProperty);
            put(entity, itemLayer, new ResourceLocation("item"));
        }
        return itemConfig;
    }

    /**
     * Delete the {@link AttackLayer} of the {@link ItemStack} at the {@link ResourceLocation}.
     * This method will also update clients.
     * Returns the new AttackConfig
     *
     * @param stack    A ItemStack.
     * @param location The ResourceLocation.
     */
    public static AttackConfig remove(ItemStack stack, ResourceLocation location, @Nullable LivingEntity entity) {
        AttackConfig itemConfig = CapabilitiesAccessors.getAttackConfig(stack);
        itemConfig.remove(location);
        if (entity != null) {
            AttackProperty itemProperty = itemConfig.getSummedProperty(null, stack, entity.level());
            AttackLayer itemLayer = new AttackLayer(50, itemProperty);
            put(entity, itemLayer, new ResourceLocation("item"));
        }
        return itemConfig;
    }

    //////////////////
    // DamageSource //
    //////////////////

    /**
     * Get a copy of the fully merged {@link AttackProperty} of the {@link DamageSource}.
     * Use this to get the attack properties, which will be used in combat.
     *
     * @param source A DamageSource.
     */
    public static AttackProperty getSummedProperty(DamageSource source) {
        return CapabilitiesAccessors.getAttackConfig(source).getSummedProperty(null, null, null);
    }
}
