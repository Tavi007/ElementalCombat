package Tavi007.ElementalCombat.client.network;

import Tavi007.ElementalCombat.common.network.AbstractPacket;
import net.minecraft.world.level.Level;

public class ClientPacketProcessor {

    public static void processPacket(AbstractPacket packet, Level level) {
        if (!packet.isValid()) {
            //TODO: add logging
            return;
        }

        packet.processPacket(level);
    }
}
