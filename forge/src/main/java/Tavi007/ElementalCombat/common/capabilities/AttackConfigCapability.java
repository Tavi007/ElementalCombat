package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.*;

public class AttackConfigCapability {

    public static final Capability<AttackConfigSerializer> ELEMENTAL_ATTACK_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static final Direction DEFAULT_FACING = null;


    public static void register(final RegisterCapabilitiesEvent event) {
        event.register(AttackConfigSerializer.class);
    }

    public static ICapabilityProvider createProvider(final AttackConfig attackData) {
        return new SerializableCapabilityProvider<>(ELEMENTAL_ATTACK_CAPABILITY, DEFAULT_FACING, new AttackConfigSerializer(attackData));
    }
}
