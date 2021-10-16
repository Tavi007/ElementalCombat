package Tavi007.ElementalCombat.api;

import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import Tavi007.ElementalCombat.util.NetworkHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DefenseDataAPI {

	
	public static DefenseLayer getLayer(LivingEntity entity, ResourceLocation location) {
		return new DefenseLayer(DefenseDataHelper.get(entity).getLayer(location));
	}
	
	public static void putLayer(LivingEntity entity, DefenseLayer layer, ResourceLocation location) {
		DefenseDataHelper.get(entity).putLayer(location, layer);
		if(entity.isServerWorld()) {
			NetworkHelper.syncDefenseLayerMessageForClients(entity, layer, location);
		}
	}
	
	public static void deleteLayer(LivingEntity entity, ResourceLocation location) {
		DefenseLayer layer = new DefenseLayer();
		DefenseDataHelper.get(entity).putLayer(location, layer);
		if(entity.isServerWorld()) {
			NetworkHelper.syncDefenseLayerMessageForClients(entity, layer, location);
		}
	}

	public static DefenseLayer getLayer(ItemStack stack, ResourceLocation location) {
		return new DefenseLayer(DefenseDataHelper.get(stack).getLayer(location));
	}
	
	public static void putLayer(ItemStack stack, DefenseLayer layer, ResourceLocation location) {
		DefenseDataHelper.get(stack).putLayer(location, layer);
	}
	
	public static void deleteLayer(ItemStack stack, ResourceLocation location) {
		DefenseLayer layer = new DefenseLayer();
		DefenseDataHelper.get(stack).putLayer(location, layer);
	}
}
