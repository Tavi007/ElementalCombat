package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.ElementalCombat;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class AttackDataCapability {

    public static final Capability<AttackData> ELEMENTAL_ATTACK_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    /**
     * The default {@link Direction} to use for this capability.
     */
    public static final Direction DEFAULT_FACING = null;

    /**
     * The ID of this capability.
     */
    public static final ResourceLocation ID = new ResourceLocation(ElementalCombat.MOD_ID, "attack_data");

    public static void register(final RegisterCapabilitiesEvent event) {
        event.register(AttackData.class);
    }

    public static ICapabilityProvider createProvider(final AttackData atck) {
        return new SerializableCapabilityProvider<>(ELEMENTAL_ATTACK_CAPABILITY, DEFAULT_FACING, atck);
    }

    /**
     * Event handler for the {@link IElementalAttack} capability.
     */
    @Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID)
    private static class EventHandler {

        /**
         * Attach the {@link IElementalAttack} capability to all living entities.
         *
         * @param event The event
         */
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {
            final AttackData atck = new AttackData();
            event.addCapability(ID, createProvider(atck));
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
            final AttackData atck = new AttackData();
            event.addCapability(ID, createProvider(atck));
        }
    }
}
