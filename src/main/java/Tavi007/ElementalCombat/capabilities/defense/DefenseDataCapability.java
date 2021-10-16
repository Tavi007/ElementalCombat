package Tavi007.ElementalCombat.capabilities.defense;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.SerializableCapabilityProvider;
import Tavi007.ElementalCombat.util.ElementalCombatNBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class DefenseDataCapability {

	@CapabilityInject(DefenseData.class)
	public static final Capability<DefenseData> ELEMENTAL_DEFENSE_CAPABILITY = null;

	/**
	 * The default {@link Direction} to use for this capability.
	 */
	public static final Direction DEFAULT_FACING = null;

	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(ElementalCombat.MOD_ID, "elemental_defense");

	public static void register() {
		CapabilityManager.INSTANCE.register(DefenseData.class, new Capability.IStorage<DefenseData>() {

			@Override
			public INBT writeNBT(final Capability<DefenseData> capability, final DefenseData instance, final Direction side) {
				CompoundNBT nbt = new CompoundNBT();
				ElementalCombatNBTHelper.writeDefenseDataToNBT(nbt, instance);
				return nbt;
			}

			@Override
			public void readNBT(final Capability<DefenseData> capability, final DefenseData instance, final Direction side, final INBT nbt) {
				DefenseData data = ElementalCombatNBTHelper.readDefenseDataFromNBT((CompoundNBT)nbt);
				instance.set(data);
			}

		}, () -> new DefenseData());
	}

	public static ICapabilityProvider createProvider(final DefenseData def) {
		return new SerializableCapabilityProvider<>(ELEMENTAL_DEFENSE_CAPABILITY, DEFAULT_FACING, def);
	}


	/**
	 * Event handler for the {@link IElementalDefense} capability.
	 */
	@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID)
	private static class EventHandler {

		/**
		 * Attach the {@link IElementalDefense} capability to all living entities.
		 *
		 * @param event The event
		 */
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {
			final DefenseData defData = new DefenseData();
			event.addCapability(ID, createProvider(defData));
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
			final DefenseData defData = new DefenseData();
			event.addCapability(ID, createProvider(defData));
		}
	}
}