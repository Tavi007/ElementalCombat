package Tavi007.ElementalCombat.capabilities.immersion;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.SerializableCapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ImmersionDataCapability {

    @CapabilityInject(ImmersionData.class)
    public static final Capability<ImmersionData> IMMERSION_DATA_CAPABILITY = null;

    /**
     * The default {@link Direction} to use for this capability.
     */
    public static final Direction DEFAULT_FACING = null;

    /**
     * The ID of this capability.
     */
    public static final ResourceLocation ID = new ResourceLocation(ElementalCombat.MOD_ID, "hurt_overlay");

    public static void register() {
        CapabilityManager.INSTANCE.register(ImmersionData.class, new Capability.IStorage<ImmersionData>() {

            @Override
            public INBT writeNBT(final Capability<ImmersionData> capability, final ImmersionData instance, final Direction side) {

                // fill nbt with data
                CompoundNBT nbt = new CompoundNBT();
                nbt.put("hurt_time", IntNBT.valueOf(instance.getHurtTime()));
                nbt.put("disable_flag", ByteNBT.valueOf(instance.disableFlag));
                return nbt;
            }

            @Override
            public void readNBT(final Capability<ImmersionData> capability, final ImmersionData instance, final Direction side, final INBT nbt) {
                IntNBT timeNBT = (IntNBT) ((CompoundNBT) nbt).get("hurt_time");
                ByteNBT redNBT = (ByteNBT) ((CompoundNBT) nbt).get("disable_flag");

                instance.setHurtTime(timeNBT.getInt());
                if (redNBT.equals(ByteNBT.ONE)) {
                    instance.disableFlag = true;
                } else {
                    instance.disableFlag = false;
                }

            }
        }, () -> new ImmersionData());
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
