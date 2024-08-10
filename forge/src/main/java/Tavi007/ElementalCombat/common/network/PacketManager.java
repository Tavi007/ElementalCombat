package Tavi007.ElementalCombat.common.network;

import Tavi007.ElementalCombat.common.ElementalCombat;
import Tavi007.ElementalCombat.common.network.clientbound.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;

public class PacketManager {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ElementalCombat.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    private static int NUM_PACKETS = 0;

    private PacketManager() {
    }

    public static void init() {
        register(BasePropertiesPacket.class, BasePropertiesPacket::new);
        register(CreateEmitterPacket.class, CreateEmitterPacket::new);
        register(DisableDamageRenderPacket.class, DisableDamageRenderPacket::new);
        register(EntityAttackLayerPacket.class, EntityAttackLayerPacket::new);
        register(EntityDefenseLayerPacket.class, EntityDefenseLayerPacket::new);

        ElementalCombat.LOGGER.info("Registered {} packets", NUM_PACKETS);
    }

    public static void sendToAllClients(Packet packet) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void sendToClient(Packet packet, Player player) {
        if (!player.level().isClientSide()) {
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), packet);
        } else {
            throw new IllegalArgumentException("Player must be a server player!");
        }
    }

    public static <MSG extends Packet> void register(Class<MSG> clazz, Function<FriendlyByteBuf, MSG> decoder) {
        CHANNEL.messageBuilder(clazz, NUM_PACKETS++)
                .encoder(Packet::encode)
                .decoder(decoder)
                .consumerMainThread((msg, ctx) -> msg.handle(ctx.get()))
                .add();
    }
}
