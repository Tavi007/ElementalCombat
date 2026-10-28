package Tavi007.ElementalCombat.common.network;

import Tavi007.ElementalCombat.client.network.ClientPacketProcessor;
import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.network.packages.*;
import Tavi007.ElementalCombat.server.network.ServerPacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

public class PacketManager {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Constants.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    private static int NUM_PACKETS = 0;

    private PacketManager() {
    }

    public static void init() {
        register(SynchronizeDatapackPacket.class, SynchronizeDatapackPacket::read);
        register(CreateEmitterPacket.class, CreateEmitterPacket::read);
        register(DisableDamageRenderPacket.class, DisableDamageRenderPacket::read);
        register(UpdateEntityAttackLayerPacket.class, UpdateEntityAttackLayerPacket::read);
        register(UpdateEntityDefenseLayerPacket.class, UpdateEntityDefenseLayerPacket::read);

        ServerPacketSender.initSender(
                PacketManager::sendToClient,
                PacketManager::sendToAllClients,
                PacketManager::sendToClient,
                PacketManager::sendToAllClients,
                PacketManager::sendToAllClients);
        Constants.LOG.info("Registered {} packets", NUM_PACKETS);
    }

    public static void sendToAllClients(IPacket packet) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void sendToClient(IPacket packet, @NotNull Player player) {
        if (!player.level().isClientSide()) {
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), packet);
        } else {
            throw new IllegalArgumentException("Player must be a server player!");
        }
    }

    public static <MSG extends IPacket> void register(Class<MSG> clazz, Function<FriendlyByteBuf, MSG> decoder) {
        CHANNEL.messageBuilder(clazz, NUM_PACKETS++)
                .encoder(MSG::encode)
                .decoder(decoder)
                .consumerMainThread((msg, ctx) -> {
                    NetworkEvent.Context context = ctx.get();
                    context.enqueueWork(() -> {
                        LogicalSide sideReceived = context.getDirection().getReceptionSide();
                        Optional<Level> level = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
                        context.setPacketHandled(ClientPacketProcessor.processPacket(msg, level));
                    });
                })
                .add();
    }
}
