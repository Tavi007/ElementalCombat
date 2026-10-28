package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.*;

public class DefenseConfigCapability {

    public static final Capability<DefenseConfigSerializer> ELEMENTAL_DEFENSE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static final Direction DEFAULT_FACING = null;


    public static void register(final RegisterCapabilitiesEvent event) {
        event.register(DefenseConfigSerializer.class);
    }

    public static ICapabilityProvider createProvider(final DefenseConfig defenseData) {
        return new SerializableCapabilityProvider<>(ELEMENTAL_DEFENSE_CAPABILITY, DEFAULT_FACING, new DefenseConfigSerializer(defenseData));
    }
}
