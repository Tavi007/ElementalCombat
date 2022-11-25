package Tavi007.ElementalCombat.capabilities.immersion;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.SerializableCapabilityProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ImmersionDataCapability {

    public static final Capability<ImmersionData> IMMERSION_DATA_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    /**
     * The default {@link Direction} to use for this capability.
     */
    public static final Direction DEFAULT_FACING = null;

    /**
     * The ID of this capability.
     */
    public static final ResourceLocation ID = new ResourceLocation(ElementalCombat.MOD_ID, "hurt_overlay");

    public static void register(final RegisterCapabilitiesEvent event) {
        event.register(ImmersionData.class);
    }

    public static ICapabilityProvider createProvider(final ImmersionData atck) {
        return new SerializableCapabilityProvider<>(IMMERSION_DATA_CAPABILITY, DEFAULT_FACING, atck);
    }

    /**
     * Event handler for the {@link IElementalAttack} capability.
     */
    @Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID)
    private static class EventHandler {

        /**
         * Attach the {@link IElementalAttack} capability to all living entities.
         *
         * @param event
         *            The event
         */
        @SubscribeEvent
        public static void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof LivingEntity) {
                final ImmersionData ImmersionData = new ImmersionData();
                event.addCapability(ID, createProvider(ImmersionData));
            }
        }
    }
}
