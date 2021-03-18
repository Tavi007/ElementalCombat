package Tavi007.ElementalCombat.network;

import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;

public class ServerPlayerSupplier implements Supplier<ServerPlayerEntity> {

	ServerPlayerEntity entity;
	
	public ServerPlayerSupplier(ServerPlayerEntity entity) {
		this.entity = entity;
	}
	
	@Override
	public ServerPlayerEntity get() {
		return entity;
	}

}
