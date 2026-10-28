package Tavi007.ElementalCombat.common.api;

import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseProperty;
import Tavi007.ElementalCombat.server.network.NetworkHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DefenseAPI {

    //////////////////
    // LivingEntity //
    //////////////////

    /**
     * Get a copy of the fully summed {@link DefenseProperty} of the {@link LivingEntity}.
     * Use this to get the defense properties, which will be used in combat.
     *
     * @param entity A LivingEntity.
     */
    public static DefenseProperty getSummedProperty(LivingEntity entity) {
        DefenseConfig config = CapabilitiesAccessors.getDefenseConfig(entity);
        return config.getSummedProperty(entity, entity.getMainHandItem(), entity.level());
    }

    /**
     * Get acopy of the {@link DefenseLayer} of the {@link LivingEntity} at the {@link ResourceLocation}.
     * Use {@link DefenseAPI#put(LivingEntity, DefenseLayer, ResourceLocation)} to apply changes.
     *
     * @param entity   A LivingEntity.
     * @param location The ResourceLocation.
     */
    public static DefenseLayer get(LivingEntity entity, ResourceLocation location) {
        return new DefenseLayer(CapabilitiesAccessors.getDefenseConfig(entity).get(location));
    }

    /**
     * Set the {@link DefenseLayer} of the {@link LivingEntity} at the {@link ResourceLocation}. This method will also update client side.
     *
     * @param entity   A LivingEntity.
     * @param location The ResourceLocation.
     * @param layer    The attack layer to be set.
     */
    public static void put(LivingEntity entity, DefenseLayer layer, ResourceLocation location) {
        CapabilitiesAccessors.getDefenseConfig(entity).put(location, layer);
        if (!entity.level().isClientSide) {
            NetworkHelper.syncDefenseLayerMessageForClients(entity, layer, location);
        }
    }

    /**
     * Delete the {@link DefenseLayer} of the {@link LivingEntity} at the {@link ResourceLocation}. This method will also update client side.
     *
     * @param entity   A LivingEntity.
     * @param location The ResourceLocation.
     */
    public static void remove(LivingEntity entity, ResourceLocation location) {
        CapabilitiesAccessors.getDefenseConfig(entity).remove(location);
        if (!entity.level().isClientSide) {
            NetworkHelper.syncDefenseLayerMessageForClients(entity, null, location);
        }
    }

    ///////////////
    // Itemstack //
    ///////////////

    /**
     * Get a copy of the fully merged {@link DefenseLayer} of the {@link ItemStack}.
     * Use this to get the attack properties, which will be used in combat.
     *
     * @param stack A ItemStack.
     */
    public static DefenseProperty getSummedProperty(ItemStack stack, Level level) {
        DefenseConfig config = CapabilitiesAccessors.getDefenseConfig(stack);
        return config.getSummedProperty(null, stack, level);
    }

    /**
     * Get a copy of the {@link DefenseLayer} of the {@link ItemStack} at the {@link ResourceLocation}.
     * Use {@link DefenseAPI#put(ItemStack, DefenseLayer, ResourceLocation)} to apply changes.
     *
     * @param stack    A ItemStack.
     * @param location The ResourceLocation.
     */
    public static DefenseLayer get(ItemStack stack, ResourceLocation location) {
        return new DefenseLayer(CapabilitiesAccessors.getDefenseConfig(stack).get(location));
    }

    /**
     * Set the {@link DefenseLayer} of the {@link ItemStack} at the {@link ResourceLocation}. This method will also update client side.
     *
     * @param stack    A ItemStack.
     * @param location The ResourceLocation.
     * @param layer    The attack layer to be set.
     */
    public static void put(ItemStack stack, DefenseLayer layer, ResourceLocation location) {
        CapabilitiesAccessors.getDefenseConfig(stack).put(location, layer);
    }

    /**
     * Delete the {@link DefenseLayer} of the {@link ItemStack} at the {@link ResourceLocation}. This method will also update clients.
     *
     * @param stack    A ItemStack.
     * @param location The ResourceLocation.
     */
    public static void remove(ItemStack stack, ResourceLocation location) {
        CapabilitiesAccessors.getDefenseConfig(stack).remove(location);
    }
}
