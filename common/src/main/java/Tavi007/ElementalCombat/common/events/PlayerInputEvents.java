package Tavi007.ElementalCombat.common.events;

import Tavi007.ElementalCombat.server.network.NetworkHelper;
import net.minecraft.world.entity.player.Player;

public class PlayerInputEvents {

    public static void playerLoggedIn(Player player) {
        NetworkHelper.syncJsonMessageForClients(player);
    }

}
