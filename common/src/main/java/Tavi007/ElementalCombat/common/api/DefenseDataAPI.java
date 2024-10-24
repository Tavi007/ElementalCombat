package Tavi007.ElementalCombat.common.api;

import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.util.ElementalCombatNBTHelper;
import Tavi007.ElementalCombat.server.network.NetworkHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DefenseDataAPI {

    //////////////////
    // LivingEntity //
    //////////////////

    /**
     * Get a copy of the fully merged {@link DefenseLayer} of the {@link LivingEntity}.
     * Use this to get the defense properties, which will be used in combat.
     *
     * @param entity A LivingEntity.
     */
    public static DefenseLayer getFullDataAsLayer(LivingEntity entity) {
        return new DefenseLayer(CapabilitiesAccessors.getDefenseData(entity).toLayer());
    }

    /**
     * Get acopy of the {@link DefenseLayer} of the {@link LivingEntity} at the {@link ResourceLocation}.
     * Use {@link DefenseDataAPI#putLayer(LivingEntity, DefenseLayer, ResourceLocation)} to apply changes.
     *
     * @param entity   A LivingEntity.
     * @param location The ResourceLocation.
     */
    public static DefenseLayer getLayer(LivingEntity entity, ResourceLocation location) {
        return new DefenseLayer(CapabilitiesAccessors.getDefenseData(entity).getLayer(location));
    }

    /**
     * Set the {@link DefenseLayer} of the {@link LivingEntity} at the {@link ResourceLocation}. This method will also update client side.
     *
     * @param entity   A LivingEntity.
     * @param location The ResourceLocation.
     * @param layer    The attack layer to be set.
     */
    public static void putLayer(LivingEntity entity, DefenseLayer layer, ResourceLocation location) {
        CapabilitiesAccessors.getDefenseData(entity).putLayer(location, layer);
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
    public static void deleteLayer(LivingEntity entity, ResourceLocation location) {
        DefenseLayer layer = new DefenseLayer();
        CapabilitiesAccessors.getDefenseData(entity).putLayer(location, layer);
        if (!entity.level().isClientSide) {
            NetworkHelper.syncDefenseLayerMessageForClients(entity, layer, location);
        }
    }

    /**
     * Writes the defense data to the {@link CompoundTag} of the {@link LivingEntity}.
     *
     * @param entity A LivingEntity.
     * @param nbt    The CompoundTag.
     */
    public static void writeToNBT(CompoundTag nbt, LivingEntity entity) {
        ElementalCombatNBTHelper.writeDefenseDataToNBT(nbt, CapabilitiesAccessors.getDefenseData(entity));
    }

    /**
     * Reads the defense data from the {@link CompoundTag} and updates the {@link LivingEntity}.
     *
     * @param entity A LivingEntity.
     * @param nbt    The CompoundTag.
     */
    public static void readFromNBT(CompoundTag nbt, LivingEntity entity) {
        CapabilitiesAccessors.getDefenseData(entity).set(ElementalCombatNBTHelper.readDefenseDataFromNBT(nbt));
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
    public static DefenseLayer getFullDataAsLayer(ItemStack stack) {
        return new DefenseLayer(CapabilitiesAccessors.getDefenseData(stack).toLayer());
    }

    /**
     * Get a copy of the {@link DefenseLayer} of the {@link ItemStack} at the {@link ResourceLocation}.
     * Use {@link DefenseDataAPI#putLayer(ItemStack, DefenseLayer, ResourceLocation)} to apply changes.
     *
     * @param stack    A ItemStack.
     * @param location The ResourceLocation.
     */
    public static DefenseLayer getLayer(ItemStack stack, ResourceLocation location) {
        return new DefenseLayer(CapabilitiesAccessors.getDefenseData(stack).getLayer(location));
    }

    /**
     * Set the {@link DefenseLayer} of the {@link ItemStack} at the {@link ResourceLocation}. This method will also update client side.
     *
     * @param stack    A ItemStack.
     * @param location The ResourceLocation.
     * @param layer    The attack layer to be set.
     */
    public static void putLayer(ItemStack stack, DefenseLayer layer, ResourceLocation location) {
        CapabilitiesAccessors.getDefenseData(stack).putLayer(location, layer);
    }

    /**
     * Delete the {@link DefenseLayer} of the {@link ItemStack} at the {@link ResourceLocation}. This method will also update clients.
     *
     * @param stack    A ItemStack.
     * @param location The ResourceLocation.
     */
    public static void deleteLayer(ItemStack stack, ResourceLocation location) {
        DefenseLayer layer = new DefenseLayer();
        CapabilitiesAccessors.getDefenseData(stack).putLayer(location, layer);
    }

    /**
     * Writes the defense data to the {@link CompoundTag} of the {@link ItemStack}.
     *
     * @param stack A ItemStack.
     * @param nbt   The CompoundTag.
     */
    public static void writeToNBT(CompoundTag nbt, ItemStack stack) {
        ElementalCombatNBTHelper.writeDefenseDataToNBT(nbt, CapabilitiesAccessors.getDefenseData(stack));
    }

    /**
     * Reads the defense data from the {@link CompoundTag} and updates the {@link ItemStack}.
     *
     * @param stack A ItemStack.
     * @param nbt   The CompoundTag.
     */
    public static void readFromNBT(CompoundTag nbt, ItemStack stack) {
        CapabilitiesAccessors.getDefenseData(stack).set(ElementalCombatNBTHelper.readDefenseDataFromNBT(nbt));
    }
}
