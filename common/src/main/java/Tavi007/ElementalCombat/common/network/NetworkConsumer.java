package Tavi007.ElementalCombat.common.network;

import Tavi007.ElementalCombat.common.network.packets.AbstractPacket;
import net.minecraft.world.level.Level;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NetworkConsumer {


    Consumer<AbstractPacket> sendPacket;

    BiConsumer<AbstractPacket, Level> handlePacket;

}
