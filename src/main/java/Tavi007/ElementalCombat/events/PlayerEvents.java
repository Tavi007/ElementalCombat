package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.StartupClientOnly;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.config.ClientConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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
	public static void livingEquipmentChange(LivingEquipmentChangeEvent event)
	{
		//change defense properties
		LivingEntity entity = event.getEntityLiving();
		if(event.getSlot().getSlotType() == EquipmentSlotType.Group.ARMOR)
		{
			// get Data
			DefenseData defDataItemFrom = ElementalCombatAPI.getDefenseData(event.getFrom());
			DefenseData defDataItemTo = ElementalCombatAPI.getDefenseData(event.getTo());

			// compute Change
			DefenseData newData = new DefenseData();
			newData.substract(defDataItemFrom);
			newData.add(defDataItemTo);

			// apply change
			ElementalCombatAPI.addDefenseData(entity, newData);
		}
	}

	// since elementifyLivingEquipmentChange is fired, whenever a player logs in,
	// I need to 'remove' armor-stats from the player, when logging out. 
	@SubscribeEvent
	public static void playerLoggedOut(PlayerLoggedOutEvent event) {
		PlayerEntity entity = event.getPlayer();
		if (entity != null) {
			DefenseData defCapEntity = ElementalCombatAPI.getDefenseData(entity);
			entity.getArmorInventoryList().forEach(item -> {
				if (!item.isEmpty()) {
					DefenseData defCapItem = ElementalCombatAPI.getDefenseData(item);
					defCapEntity.substract(defCapItem);
				}
			});
		}
	}
	
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event) {
		if(StartupClientOnly.TOGGLE_HUD.isKeyDown()){
			ClientConfig.toogleHUD();
		}
	}
}