package Tavi007.ElementalCombat.common.network.packages;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

import java.util.Optional;

public interface IPacket {

    boolean isValid();

    void encode(FriendlyByteBuf buf);

    void processPacket(Optional<Level> level);
}
