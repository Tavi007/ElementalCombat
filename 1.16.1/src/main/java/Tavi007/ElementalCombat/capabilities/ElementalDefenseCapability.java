package Tavi007.ElementalCombat.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ElementalDefenseCapability {

    @CapabilityInject(ElementalDefense.class)
    public static Capability<ElementalDefense> CAPABILITY_ELEMENTAL_DEFENSE = null;
    
    public static void regsiter() {
    	CapabilityManager.INSTANCE.register(ElementalDefense.class, new ElementalDefenseStorage(), ElementalDefense::new);
    }
}
