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

	public static void onMessageReceived(final DefenseDataMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
		ctx.setPacketHandled(true);

		if (sideReceived != LogicalSide.CLIENT) {
			ElementalCombat.LOGGER.warn("DefenseDataMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
			return;
		}
		if (!message.isMessageValid()) {
			ElementalCombat.LOGGER.warn("DefenseDataMessage was invalid" + message.toString());
			return;
		}

		Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
		if (!clientWorld.isPresent()) {
			ElementalCombat.LOGGER.warn("DefenseDataMessage context could not provide a ClientWorld.");
			return;
		}
		ctx.enqueueWork(() -> processMessage(clientWorld.get(), message));
	}

	private static void processMessage(ClientWorld worldClient, DefenseDataMessage message)
	{
		PlayerEntity player = worldClient.getPlayerByUuid(message.getUUID());
		DefenseData messageData = message.getDefenseData();
		DefenseData playerData = ElementalCombatAPI.getDefenseData(player);
		if (message.isAdd()) {
			playerData.add(messageData);
		}
		else {
			playerData.set(messageData);
		}
	}


	public static boolean isThisProtocolAcceptedByClient(String protocolVersion) {
		return StartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
	}
}
