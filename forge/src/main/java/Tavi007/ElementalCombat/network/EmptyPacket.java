package Tavi007.ElementalCombat.network;

import net.minecraft.network.FriendlyByteBuf;

public abstract class EmptyPacket extends Packet {

    public EmptyPacket(FriendlyByteBuf buf) {
    }

    public EmptyPacket() {
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
    }

}
