package Tavi007.ElementalCombat.init;

import java.util.Optional;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackDataCapability;
import Tavi007.ElementalCombat.capabilities.defense.DefenseDataCapability;
import Tavi007.ElementalCombat.capabilities.immersion.ImmersionDataCapability;
import Tavi007.ElementalCombat.network.BasePropertiesMessage;
import Tavi007.ElementalCombat.network.CreateEmitterMessage;
import Tavi007.ElementalCombat.network.DisableDamageRenderMessage;
import Tavi007.ElementalCombat.network.EntityAttackLayerMessage;
import Tavi007.ElementalCombat.network.EntityDefenseLayerMessage;
import Tavi007.ElementalCombat.network.PackageHandlerOnClient;
import Tavi007.ElementalCombat.network.PackageHandlerOnServer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;

public class StartupCommon {

    private static final byte ENTITY_ATTACKDATA_MESSAGE_TO_CLIENT_ID = 1;
    private static final byte ENTITY_DEFENSELAYER_MESSAGE_TO_CLIENT_ID = 2;
    private static final byte DISABLERENDER_MESSAGE_TO_CLIENT_ID = 3;
    private static final byte CREATEEMITTER_MESSAGE_TO_CLIENT_ID = 4;
    private static final byte JSONSYNC_MESSAGE_TO_CLIENT_ID = 5;
    public static final String MESSAGE_PROTOCOL_VERSION = "1.0";

    @SubscribeEvent
    public static void onCommonSetup(RegisterCapabilitiesEvent event) {
        // capabilities
        AttackDataCapability.register(event);
        DefenseDataCapability.register(event);
        ImmersionDataCapability.register(event);
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        // networking
        ElementalCombat.simpleChannel = NetworkRegistry.newSimpleChannel(ElementalCombat.simpleChannelRL,
            () -> MESSAGE_PROTOCOL_VERSION,
            PackageHandlerOnClient::isThisProtocolAcceptedByClient,
            PackageHandlerOnServer::isThisProtocolAcceptedByServer);

        ElementalCombat.simpleChannel.registerMessage(ENTITY_ATTACKDATA_MESSAGE_TO_CLIENT_ID,
            EntityAttackLayerMessage.class,
            EntityAttackLayerMessage::encode,
            EntityAttackLayerMessage::decode,
            PackageHandlerOnClient::onMessageReceived,
            Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        ElementalCombat.simpleChannel.registerMessage(ENTITY_DEFENSELAYER_MESSAGE_TO_CLIENT_ID,
            EntityDefenseLayerMessage.class,
            EntityDefenseLayerMessage::encode,
            EntityDefenseLayerMessage::decode,
            PackageHandlerOnClient::onMessageReceived,
            Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        ElementalCombat.simpleChannel.registerMessage(DISABLERENDER_MESSAGE_TO_CLIENT_ID,
            DisableDamageRenderMessage.class,
            DisableDamageRenderMessage::encode,
            DisableDamageRenderMessage::decode,
            PackageHandlerOnClient::onMessageReceived,
            Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        ElementalCombat.simpleChannel.registerMessage(CREATEEMITTER_MESSAGE_TO_CLIENT_ID,
            CreateEmitterMessage.class,
            CreateEmitterMessage::encode,
            CreateEmitterMessage::decode,
            PackageHandlerOnClient::onMessageReceived,
            Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        ElementalCombat.simpleChannel.registerMessage(JSONSYNC_MESSAGE_TO_CLIENT_ID,
            BasePropertiesMessage.class,
            BasePropertiesMessage::encode,
            BasePropertiesMessage::decode,
            PackageHandlerOnClient::onMessageReceived,
            Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        ElementalCombat.LOGGER.info("setup method registered.");
    }

}
