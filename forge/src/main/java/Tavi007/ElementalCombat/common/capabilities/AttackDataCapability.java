package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import net.minecraft.core.Direction;
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

    public static final Direction DEFAULT_FACING = null;


    public static void register(final RegisterCapabilitiesEvent event) {
        event.register(AttackData.class);
    }

    public static ICapabilityProvider createProvider(final AttackData attackData) {
        return new SerializableCapabilityProvider<>(ELEMENTAL_ATTACK_CAPABILITY, DEFAULT_FACING, attackData);
    }


    @Mod.EventBusSubscriber(modid = Constants.MOD_ID)
    private static class EventHandler {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {
            final AttackData attackData = new AttackData();
            event.addCapability(Constants.ATTACK_DATA_CAPABILITY, createProvider(attackData));
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
            final AttackData attackData = new AttackData();
            event.addCapability(Constants.ATTACK_DATA_CAPABILITY, createProvider(attackData));
        }
    }
}
