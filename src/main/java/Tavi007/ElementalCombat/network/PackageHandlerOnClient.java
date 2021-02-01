package Tavi007.ElementalCombat.network;

import java.util.Optional;
import java.util.function.Supplier;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.render.HurtOverlayData;
import Tavi007.ElementalCombat.capabilities.render.HurtOverlayDataCapability;
import Tavi007.ElementalCombat.init.StartupCommon;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;

public class PackageHandlerOnClient {

	public static void onCombatMessageReceived(final CombatDataMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
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

	public static void onDisableRedMessageReceived(final DisableRedMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
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
	}
	
	private static void processMessage(ClientWorld clientWorld, DisableRedMessage message)
	{
		Entity entity = clientWorld.getEntityByID((message.getId()));
		if(entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			HurtOverlayData data = (HurtOverlayData) livingEntity.getCapability(HurtOverlayDataCapability.HURT_OVERLAY_CAPABILITY, null).orElse(new HurtOverlayData());
			data.disableRedOverlay = true;
		}
	}

	public static boolean isThisProtocolAcceptedByClient(String protocolVersion) {
		return StartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
	}
}
