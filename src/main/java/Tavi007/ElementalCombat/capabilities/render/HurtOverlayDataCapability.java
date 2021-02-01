package Tavi007.ElementalCombat.capabilities.render;

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

public class HurtOverlayDataCapability {
	@CapabilityInject(HurtOverlayData.class)
	public static final Capability<HurtOverlayData> HURT_OVERLAY_CAPABILITY = null;

	/**
	 * The default {@link Direction} to use for this capability.
	 */
	public static final Direction DEFAULT_FACING = null;

	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(ElementalCombat.MOD_ID, "hurt_overlay");

	public static void register() {
		CapabilityManager.INSTANCE.register(HurtOverlayData.class, new Capability.IStorage<HurtOverlayData>() {

			@Override
			public INBT writeNBT(final Capability<HurtOverlayData> capability, final HurtOverlayData instance, final Direction side) {

				//fill nbt with data
				CompoundNBT nbt = new CompoundNBT();
				nbt.put("hurt_time", IntNBT.valueOf(instance.getHurtTime()));
				nbt.put("disable_red", ByteNBT.valueOf(instance.disableRedOverlay));
				return nbt;
			}

			@Override
			public void readNBT(final Capability<HurtOverlayData> capability, final HurtOverlayData instance, final Direction side, final INBT nbt) {
				IntNBT timeNBT = (IntNBT) ((CompoundNBT) nbt).get("hurt_time");
				ByteNBT redNBT = (ByteNBT) ((CompoundNBT) nbt).get("disable_red");
				
				instance.setHurtTime(timeNBT.getInt());
				if(redNBT.equals(ByteNBT.ONE)) {
					instance.disableRedOverlay = true;
				}
				else {
					instance.disableRedOverlay = false;
				}

			}
		}, () -> new HurtOverlayData());
	}

	public static ICapabilityProvider createProvider(final HurtOverlayData atck) {
		return new SerializableCapabilityProvider<>(HURT_OVERLAY_CAPABILITY, DEFAULT_FACING, atck);
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
		@SubscribeEvent
		public static void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof LivingEntity) {
				final HurtOverlayData HurtOverlayData = new HurtOverlayData();
				event.addCapability(ID, createProvider(HurtOverlayData));
			}
		}
	}
}
