package Tavi007.ElementalCombat.server.network;

import Tavi007.ElementalCombat.common.network.*;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

// TODO: adjust such that packet can be sent to explicit player
public class ServerPacketSender {

    private static BiConsumer<CreateEmitterPacket, ServerPlayer> createEmitterPacketSender;
    private static Consumer<DisableDamageRenderPacket> disableDamageRenderPacketSender;
    private static Consumer<SyncronizeDatapackPacket> syncronizeDatapackPacketSender;
    private static Consumer<UpdateEntityAttackLayerPacket> updateEntityAttackLayerPacketSender;
    private static Consumer<UpdateEntityDefenseLayerPacket> updateEntityDefenseLayerPacketSender;

    public static void initSender(BiConsumer<CreateEmitterPacket, ServerPlayer> createEmitterPacketSender,
                                  Consumer<DisableDamageRenderPacket> disableDamageRenderPacketSender,
                                  Consumer<SyncronizeDatapackPacket> syncronizeDatapackPacketSender,
                                  Consumer<UpdateEntityAttackLayerPacket> updateEntityAttackLayerPacketSender,
                                  Consumer<UpdateEntityDefenseLayerPacket> updateEntityDefenseLayerPacketSender) {
        ServerPacketSender.createEmitterPacketSender = createEmitterPacketSender;
        ServerPacketSender.disableDamageRenderPacketSender = disableDamageRenderPacketSender;
        ServerPacketSender.syncronizeDatapackPacketSender = syncronizeDatapackPacketSender;
        ServerPacketSender.updateEntityAttackLayerPacketSender = updateEntityAttackLayerPacketSender;
        ServerPacketSender.updateEntityDefenseLayerPacketSender = updateEntityDefenseLayerPacketSender;
    }

    public static void sendPacket(CreateEmitterPacket packet, ServerPlayer player) {
        createEmitterPacketSender.accept(packet, player);
    }

    public static void sendPacket(DisableDamageRenderPacket packet) {
        disableDamageRenderPacketSender.accept(packet);
    }

    public static void sendPacket(SyncronizeDatapackPacket packet) {
        syncronizeDatapackPacketSender.accept(packet);
    }

    public static void sendPacket(UpdateEntityAttackLayerPacket packet) {
        updateEntityAttackLayerPacketSender.accept(packet);
    }

    public static void sendPacket(UpdateEntityDefenseLayerPacket packet) {
        updateEntityDefenseLayerPacketSender.accept(packet);
    }
}
