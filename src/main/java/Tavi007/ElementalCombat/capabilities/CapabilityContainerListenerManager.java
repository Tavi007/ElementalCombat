package Tavi007.ElementalCombat.capabilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import Tavi007.ElementalCombat.ElementalCombat;

public class CapabilityContainerListenerManager {
	/**
	 * The {@link CapabilityContainerListener} factories.
	 */
	private static final Set<Function<ServerPlayerEntity, CapabilityContainerListener<?>>> containerListenerFactories = new HashSet<>();

	/**
	 * Register a factory for a {@link CapabilityContainerListener}.
	 *
	 * @param factory The factory
	 */
	public static void registerListenerFactory(final Function<ServerPlayerEntity, CapabilityContainerListener<?>> factory) {
		containerListenerFactories.add(factory);
	}

	@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID)
	@SuppressWarnings("unused")
	private static class EventHandler {

		/**
		 * Add the listeners to a {@link Container}.
		 *
		 * @param player    The player
		 * @param container The Container
		 */
		private static void addListeners(final ServerPlayerEntity player, final Container container) {
			containerListenerFactories.forEach(
					factory -> container.addListener(factory.apply(player))
			);
		}

		/**
		 * Add the listeners to {@link PlayerEntity#container} when an {@link ServerPlayerEntity} logs in.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void playerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getPlayer() instanceof ServerPlayerEntity) {
				final ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
				addListeners(player, player.container);
			}
		}

		/**
		 * Add the listeners to {@link PlayerEntity#container} when an {@link ServerPlayerEntity} is cloned.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void playerClone(final PlayerEvent.Clone event) {
			if (event.getPlayer() instanceof ServerPlayerEntity) {
				final ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
				addListeners(player, player.container);
			}
		}

		/**
		 * Add the listeners to a {@link Container} when it's opened by an {@link ServerPlayerEntity}.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void containerOpen(final PlayerContainerEvent.Open event) {
			if (event.getPlayer() instanceof ServerPlayerEntity) {
				final ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
				addListeners(player, event.getContainer());
			}
		}
	}
}
