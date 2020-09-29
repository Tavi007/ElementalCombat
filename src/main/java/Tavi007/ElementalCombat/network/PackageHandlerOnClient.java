package Tavi007.ElementalCombat.network;

import java.util.Optional;
import java.util.function.Supplier;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.StartupCommon;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;

public class PackageHandlerOnClient {

	public static void onMessageReceived(final DefenseDataMessageToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
		ctx.setPacketHandled(true);

		if (sideReceived != LogicalSide.CLIENT) {
			ElementalCombat.LOGGER.warn("DefenseDataMessageToClient received on wrong side:" + ctx.getDirection().getReceptionSide());
			return;
		}
		if (!message.isMessageValid()) {
			ElementalCombat.LOGGER.warn("DefenseDataMessageToClient was invalid" + message.toString());
			return;
		}
		// we know for sure that this handler is only used on the client side, so it is ok to assume
		//  that the ctx handler is a client, and that Minecraft exists.
		// Packets received on the server side must be handled differently!  See MessageHandlerOnServer

		Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
		if (!clientWorld.isPresent()) {
			ElementalCombat.LOGGER.warn("DefenseDataMessageToClient context could not provide a ClientWorld.");
			return;
		}

		// This code creates a new task which will be executed by the client during the next tick
		//  In this case, the task is to call messageHandlerOnClient.processMessage(worldclient, message)
		ctx.enqueueWork(() -> processMessage(clientWorld.get(), message));
	}



	private static void processMessage(ClientWorld worldClient, DefenseDataMessageToClient message)
	{
		PlayerEntity player = worldClient.getPlayerByUuid(message.getUUID());
		DefenseData messageData = message.getDefenseData();
		DefenseData playerData = ElementalCombatAPI.getDefenseData(player);
		playerData.add(messageData);
	}


	public static boolean isThisProtocolAcceptedByClient(String protocolVersion) {
		return StartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
	}
}
