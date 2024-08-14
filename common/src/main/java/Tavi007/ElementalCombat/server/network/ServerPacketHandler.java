package Tavi007.ElementalCombat.server.network;

import Tavi007.ElementalCombat.common.network.*;

import java.util.function.Consumer;

public class ServerPacketHandler {

    Consumer<CreateEmitterPacket> createEmitterPacketSender;
    Consumer<DisableDamageRenderPacket> disableDamageRenderPacketSender;
    Consumer<SyncronizeDatapackPacket> syncronizeDatapackPacketSender;
    Consumer<UpdateEntityAttackLayerPacket> updateEntityAttackLayerPacketSender;
    Consumer<UpdateEntityDefenseLayerPacket> updateEntityDefenseLayerPacketSender;
}
