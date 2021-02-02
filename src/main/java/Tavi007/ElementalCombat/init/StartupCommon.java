package Tavi007.ElementalCombat.init;

import Tavi007.ElementalCombat.capabilities.defense.DefenseDataCapability;
import Tavi007.ElementalCombat.capabilities.render.HurtRenderDataCapability;
import Tavi007.ElementalCombat.network.DisableDamageRenderMessage;
import Tavi007.ElementalCombat.network.EntityMessage;
import Tavi007.ElementalCombat.network.PackageHandlerOnClient;
import Tavi007.ElementalCombat.network.PackageHandlerOnServer;

import java.util.Optional;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackDataCapability;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;

public class StartupCommon {
	private static final byte ENTITYDATA_MESSAGE_TO_CLIENT_ID = 1; 
	private static final byte DISABLERENDER_MESSAGE_TO_CLIENT_ID = 2; 
	public static final String MESSAGE_PROTOCOL_VERSION = "1.0"; 
	
	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event){
		//capabilities
		AttackDataCapability.register();
		DefenseDataCapability.register();
		HurtRenderDataCapability.register();

		//networking
		ElementalCombat.simpleChannel = NetworkRegistry.newSimpleChannel(ElementalCombat.simpleChannelRL, () -> MESSAGE_PROTOCOL_VERSION,
				PackageHandlerOnClient::isThisProtocolAcceptedByClient,
				PackageHandlerOnServer::isThisProtocolAcceptedByServer);
		
		ElementalCombat.simpleChannel.registerMessage(ENTITYDATA_MESSAGE_TO_CLIENT_ID, EntityMessage.class,
				EntityMessage::encode, EntityMessage::decode,
				PackageHandlerOnClient::onCombatMessageReceived,
	            Optional.of(NetworkDirection.PLAY_TO_CLIENT));

		ElementalCombat.simpleChannel.registerMessage(DISABLERENDER_MESSAGE_TO_CLIENT_ID, DisableDamageRenderMessage.class,
				DisableDamageRenderMessage::encode, DisableDamageRenderMessage::decode,
				PackageHandlerOnClient::onDisableDamageRenderMessageReceived,
	            Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		
		ElementalCombat.LOGGER.info("setup method registered.");
	}

}
