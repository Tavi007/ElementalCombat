package Tavi007.ElementalCombat.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public abstract class Packet {

    public Packet(FriendlyByteBuf buf) {
    }

    protected Packet() {
    }

    public abstract void encode(FriendlyByteBuf buf);

    public abstract void handle(NetworkEvent.Context context);

}
