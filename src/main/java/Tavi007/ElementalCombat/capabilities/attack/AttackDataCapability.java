package Tavi007.ElementalCombat.capabilities.attack;

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

public class AttackDataCapability {

	@CapabilityInject(AttackData.class)
	public static final Capability<AttackData> ELEMENTAL_ATTACK_CAPABILITY = null;

	/**
	 * The default {@link Direction} to use for this capability.
	 */
	public static final Direction DEFAULT_FACING = null;

	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(ElementalCombat.MOD_ID, "attack_data");

	public static void register() {
		CapabilityManager.INSTANCE.register(AttackData.class, new Capability.IStorage<AttackData>() {

			@Override
			public INBT writeNBT(final Capability<AttackData> capability, final AttackData instance, final Direction side) {
				CompoundNBT nbt = new CompoundNBT();
				ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, instance);
				return nbt;
			}

			@Override
			public void readNBT(final Capability<AttackData> capability, final AttackData instance, final Direction side, final INBT nbt) {
				AttackData data = ElementalCombatNBTHelper.readAttackDataFromNBT((CompoundNBT)nbt);
				instance.set(data);
			}
		}, () -> new AttackData());
	}

	public static ICapabilityProvider createProvider(final AttackData atck) {
		return new SerializableCapabilityProvider<>(ELEMENTAL_ATTACK_CAPABILITY, DEFAULT_FACING, atck);
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
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {
			final AttackData atck = new AttackData();
			event.addCapability(ID, createProvider(atck));
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
			final AttackData atck = new AttackData();
			event.addCapability(ID, createProvider(atck));
		}
	}
}