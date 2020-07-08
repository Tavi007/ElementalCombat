package Tavi007.ElementalCombat.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ElementalAttackCapability {

    @CapabilityInject(ElementalAttack.class)
    public static Capability<ElementalAttack> CAPABILITY_ELEMENTAL_ATTACK = null;
    
    public static void regsiter() {
    	CapabilityManager.INSTANCE.register(ElementalAttack.class, new ElementalAttackStorage(), ElementalAttack::new);
    }
}
