package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ImmersionDataCapability {

    public static final Capability<ImmersionDataSerializer> IMMERSION_DATA_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static final Direction DEFAULT_FACING = null;

    public static void register(final RegisterCapabilitiesEvent event) {
        event.register(ImmersionDataSerializer.class);
    }

    public static ICapabilityProvider createProvider(final ImmersionData immersionData) {
        return new SerializableCapabilityProvider<>(IMMERSION_DATA_CAPABILITY, DEFAULT_FACING, new ImmersionDataSerializer(immersionData));
    }
}
