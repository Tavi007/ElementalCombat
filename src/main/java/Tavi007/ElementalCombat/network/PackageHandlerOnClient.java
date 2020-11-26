package Tavi007.ElementalCombat.network;

import java.util.Optional;
import java.util.function.Supplier;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.StartupCommon;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;

public class PackageHandlerOnClient {

	public static void onMessageReceived(final CombatDataMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
		ctx.setPacketHandled(true);

		if (sideReceived != LogicalSide.CLIENT) {
			ElementalCombat.LOGGER.warn("CombatDataMessage received on wrong side: " + ctx.getDirection().getReceptionSide());
			return;
		}
		if (!message.isMessageValid()) {
			ElementalCombat.LOGGER.warn("CombatDataMessage was invalid " + message.toString());
			return;
		}

		Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
		if (!clientWorld.isPresent()) {
			ElementalCombat.LOGGER.warn("CombatDataMessage context could not provide a ClientWorld.");
			return;
		}
		ctx.enqueueWork(() -> processMessage(clientWorld.get(), message));
	}

	private static void processMessage(ClientWorld clientWorld, CombatDataMessage message)
	{
		AttackData atckData = message.getAttackData();
		DefenseData defData = message.getDefenseData();

		if(message instanceof EntityMessage) {
			LivingEntity player = clientWorld.getPlayerByUuid(((EntityMessage) message).getId());
			ElementalCombatAPI.getAttackData((LivingEntity) player).set(atckData);
			if (message.isAdd()) {
				ElementalCombatAPI.getDefenseData((LivingEntity) player).add(defData);
			}
			else {
				ElementalCombatAPI.getDefenseData((LivingEntity) player).set(defData);
			}
		}
		else if(message instanceof ItemMessage) {
			ItemMessage itemMessage = (ItemMessage) message;
			@SuppressWarnings("resource")
			final PlayerEntity player = Minecraft.getInstance().player;
			final Container container;
			if (itemMessage.getWindowId() == 0) {
				container = player.container;
			} else if (itemMessage.getWindowId() == player.openContainer.windowId) {
				container = player.openContainer;
			} else {
				return;
			}

			final ItemStack stack = container.getSlot(itemMessage.getSlotNumber()).getStack();
			ElementalCombatAPI.getAttackData(stack).set(atckData);
			if (message.isAdd()) {
				ElementalCombatAPI.getDefenseData(stack).add(defData);
			}
			else {
				ElementalCombatAPI.getDefenseData(stack).set(defData);
			}
		}
	}


	public static boolean isThisProtocolAcceptedByClient(String protocolVersion) {
		return StartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
	}
}
