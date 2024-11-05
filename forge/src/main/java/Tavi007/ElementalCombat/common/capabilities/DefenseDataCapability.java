package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;

public class DefenseDataCapability {

    public static final Capability<DefenseDataSerializer> ELEMENTAL_DEFENSE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static final Direction DEFAULT_FACING = null;


    public static void register(final RegisterCapabilitiesEvent event) {
        event.register(DefenseDataSerializer.class);
    }

    public static ICapabilityProvider createProvider(final DefenseData defenseData) {
        return new SerializableCapabilityProvider<>(ELEMENTAL_DEFENSE_CAPABILITY, DEFAULT_FACING, new DefenseDataSerializer(defenseData));
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID)
    private static class EventHandler {

        public static void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {
            final DefenseData defenseData = new DefenseData();
            Constants.LOG.info("defenseData entity");
            event.addCapability(Constants.DEFENSE_DATA_CAPABILITY, createProvider(defenseData));
        }

        public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
            final DefenseData defenseData = new DefenseData();
            Constants.LOG.info("defenseData stack");
            event.addCapability(Constants.DEFENSE_DATA_CAPABILITY, createProvider(defenseData));
        }
    }
}
