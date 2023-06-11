package Tavi007.ElementalCombat.network;

import java.util.function.Function;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.network.clientbound.BasePropertiesPacket;
import Tavi007.ElementalCombat.network.clientbound.CreateEmitterPacket;
import Tavi007.ElementalCombat.network.clientbound.DisableDamageRenderPacket;
import Tavi007.ElementalCombat.network.clientbound.EntityAttackLayerPacket;
import Tavi007.ElementalCombat.network.clientbound.EntityDefenseLayerPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

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

    public static <MSG extends Packet> void register(Class<MSG> clazz, Function<FriendlyByteBuf, MSG> decoder) {
        CHANNEL.messageBuilder(clazz, NUM_PACKETS++)
            .encoder(Packet::encode)
            .decoder(decoder)
            .consumerMainThread((msg, ctx) -> msg.handle(ctx.get()))
            .add();
    }
}
