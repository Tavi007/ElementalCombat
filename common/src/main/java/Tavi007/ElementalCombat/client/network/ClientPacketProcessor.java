package Tavi007.ElementalCombat.client.network;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.network.AbstractPacket;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ClientPacketProcessor {

    public static boolean processPacket(AbstractPacket packet, Optional<Level> level) {
        Constants.LOG.debug("handling " + packet.getClass().getName() + " on client.");
        if (!packet.isValid()) {
            Constants.LOG.debug("invalid " + packet.getClass().getName() + " encountered.");
            return false;
        }

        try {
            packet.processPacket(level);
            return true;
        } catch (Exception e) {
            Constants.LOG.warn("failed to handle " + packet.getClass().getName());
            return false;
        }
    }
}
