package Tavi007.ElementalCombat.network;

import java.util.function.Supplier;

import net.minecraft.server.level.ServerPlayer;

public class ServerPlayerSupplier implements Supplier<ServerPlayer> {

    ServerPlayer entity;

    public ServerPlayerSupplier(ServerPlayer entity) {
        this.entity = entity;
    }

    @Override
    public ServerPlayer get() {
        return entity;
    }

}
