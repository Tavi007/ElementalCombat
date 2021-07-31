package Tavi007.ElementalCombat.network;

import java.util.Optional;
import java.util.function.Supplier;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseLayer;
import Tavi007.ElementalCombat.capabilities.immersion.ImmersionData;
import Tavi007.ElementalCombat.capabilities.immersion.ImmersionDataCapability;
import Tavi007.ElementalCombat.init.StartupCommon;
import Tavi007.ElementalCombat.init.ParticleList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;

public class PackageHandlerOnClient {

	public static void onMessageReceived(final MessageToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
		ctx.setPacketHandled(true);

		if (sideReceived != LogicalSide.CLIENT) {
			ElementalCombat.LOGGER.warn(message.getClass().getName() + " received on wrong side: " + ctx.getDirection().getReceptionSide());
			return;
		}
		if (!message.isMessageValid()) {
			ElementalCombat.LOGGER.warn(message.getClass().getName() + " was invalid " + message.toString());
			return;
		}

		Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
		if (!clientWorld.isPresent()) {
			ElementalCombat.LOGGER.warn(message.getClass().getName() + " context could not provide a ClientWorld.");
			return;
		}
		
		if(message instanceof CreateEmitterMessage) {
			ctx.enqueueWork(() -> processMessage(clientWorld.get(), (CreateEmitterMessage) message));
		}
		else if(message instanceof DisableDamageRenderMessage) {
			ctx.enqueueWork(() -> processMessage(clientWorld.get(), (DisableDamageRenderMessage) message));
		}
		else if(message instanceof EntityAttackDataMessage) {
			ctx.enqueueWork(() -> processMessage(clientWorld.get(), (EntityAttackDataMessage) message));
		}
		else if(message instanceof EntityDefenseLayerMessage) {
			ctx.enqueueWork(() -> processMessage(clientWorld.get(), (EntityDefenseLayerMessage) message));
		}
		else if(message instanceof BasePropertiesMessage) {
			ctx.enqueueWork(() -> ElementalCombat.COMBAT_PROPERTIES_MANGER.set((BasePropertiesMessage) message));
		}
	}

	private static void processMessage(ClientWorld clientWorld, EntityAttackDataMessage message) {
		AttackData atckData = message.getAttackData();
		Entity entity = clientWorld.getEntityByID(message.getId());
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			AttackDataAPI.get(livingEntity).set(atckData);
		}
	}

	private static void processMessage(ClientWorld clientWorld, EntityDefenseLayerMessage message) {
		DefenseLayer defLayer = message.getDefenseLayer();
		Entity entity = clientWorld.getEntityByID(message.getId());
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			DefenseDataAPI.get(livingEntity).putLayer(defLayer, message.getLocation());;
		}
	}
	
	private static void processMessage(ClientWorld clientWorld, DisableDamageRenderMessage message) {
		Entity entity = clientWorld.getEntityByID((message.getId()));
		if(entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			ImmersionData data = (ImmersionData) livingEntity.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null).orElse(new ImmersionData());
			data.disableFlag = true;
		}
	}

	@SuppressWarnings("resource")
	private static void processMessage(ClientWorld clientWorld, CreateEmitterMessage message) {
		ParticleManager particles = Minecraft.getInstance().particles;
		Entity entity = clientWorld.getEntityByID(message.getEntityId());
		switch (message.getParticleName()) {
		case "critical_element":
			particles.addParticleEmitter(entity, ParticleList.CRIT_ELEMENT.get());
			break;
		case "resistent_element":
			particles.addParticleEmitter(entity, ParticleList.RESIST_ELEMENT.get());
			break;
		case "absorb":
			particles.addParticleEmitter(entity, ParticleList.ABSORB.get());
			break;
		case "critical_style":
			particles.addParticleEmitter(entity, ParticleList.CRIT_STYLE.get());
			break;
		case "resistent_style":
			particles.addParticleEmitter(entity, ParticleList.RESIST_STYLE.get());
			break;
		}
	}
	public static boolean isThisProtocolAcceptedByClient(String protocolVersion) {
		return StartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
	}
}
