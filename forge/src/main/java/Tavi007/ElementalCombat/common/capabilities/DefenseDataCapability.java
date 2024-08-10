package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.ElementalCombat;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class DefenseDataCapability {

    public static final Capability<DefenseData> ELEMENTAL_DEFENSE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    /**
     * The default {@link Direction} to use for this capability.
     */
    public static final Direction DEFAULT_FACING = null;

    /**
     * The ID of this capability.
     */
    public static final ResourceLocation ID = new ResourceLocation(ElementalCombat.MOD_ID, "elemental_defense");

    public static void register(final RegisterCapabilitiesEvent event) {
        event.register(DefenseData.class);
    }

    public static ICapabilityProvider createProvider(final DefenseData def) {
        return new SerializableCapabilityProvider<>(ELEMENTAL_DEFENSE_CAPABILITY, DEFAULT_FACING, def);
    }

    /**
     * Event handler for the {@link IElementalDefense} capability.
     */
    @Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID)
    private static class EventHandler {

        /**
         * Attach the {@link IElementalDefense} capability to all living entities.
         *
         * @param event The event
         */
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {
            final DefenseData defData = new DefenseData();
            event.addCapability(ID, createProvider(defData));
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
            final DefenseData defData = new DefenseData();
            event.addCapability(ID, createProvider(defData));
        }
    }
}
