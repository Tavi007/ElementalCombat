package Tavi007.ElementalCombat.common.network;

import net.minecraft.network.FriendlyByteBuf;

public abstract class AbstractPacket {

    protected AbstractPacket() {
    }

    public AbstractPacket(FriendlyByteBuf buf) {
    }


    public abstract void encode(FriendlyByteBuf buf);


}
