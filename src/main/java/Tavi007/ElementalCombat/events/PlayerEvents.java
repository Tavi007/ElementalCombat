package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.api.defense.DefenseLayer;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.init.StartupClientOnly;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents
{
	@SubscribeEvent
	public static void livingEquipmentChange(LivingEquipmentChangeEvent event) {
		//change defense properties
		if(event.getSlot().getSlotType() == EquipmentSlotType.Group.ARMOR)
		{
			DefenseLayer layer = new DefenseLayer();
			LivingEntity entity = event.getEntityLiving();
			entity.getArmorInventoryList().forEach( stack -> {
				DefenseData data = DefenseDataAPI.get(stack);
				layer.addLayer(data.toLayer());
			});
			//DefenseDataAPI.get(event.getEntityLiving()).putLayer(layer, new ResourceLocation("minecraft", "armor"));
			DefenseDataAPI.putLayer(event.getEntityLiving(), layer, new ResourceLocation("minecraft", "armor"));
		}
	}

	// every armor piece (curios and vanilla) will be re-applied on log in
	// same goes for any auras (WIP)
	// this is why, all this stuff need to be removed on log out.
	@SubscribeEvent
	public static void playerLoggedOut(PlayerLoggedOutEvent event) {
		PlayerEntity entity = event.getPlayer();
		if (entity != null) {
			DefenseData defCapEntity = DefenseDataAPI.get(entity);
			defCapEntity.clear();
		}
	}
	
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event) {
		if(StartupClientOnly.TOGGLE_HUD.isKeyDown()) {
			ClientConfig.toogleHUD();
		}
	}
}
