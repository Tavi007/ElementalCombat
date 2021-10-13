package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.api.NetworkAPI;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.api.defense.DefenseLayer;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.init.StartupClientOnly;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents
{
	@SubscribeEvent
	public static void livingEquipmentChange(LivingEquipmentChangeEvent event) {
		LivingEntity entity = event.getEntityLiving();
		//change defense properties
		switch(event.getSlot().getSlotType())
		{
		case ARMOR:
			DefenseLayer defenseLayer = new DefenseLayer();
			entity.getArmorInventoryList().forEach( stack -> {
				DefenseData data = DefenseDataAPI.get(stack);
				defenseLayer.addLayer(data.toLayer());
			});
			DefenseDataAPI.putLayer(entity, defenseLayer, new ResourceLocation("armor"));
		case HAND:
			AttackDataAPI.updateItemLayer(entity);
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
	public static void playerLoggedIn(PlayerLoggedInEvent event) {
		NetworkAPI.syncJsonMessageForClients(event.getPlayer());
	}

	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event) {
		if(StartupClientOnly.TOGGLE_HUD.isKeyDown()) {
			ClientConfig.toogleHUD();
		}
	}
}
