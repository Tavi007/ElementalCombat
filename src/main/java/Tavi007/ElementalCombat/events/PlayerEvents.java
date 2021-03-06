package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.api.DefaultPropertiesAPI;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.init.StartupClientOnly;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
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
		LivingEntity entity = event.getEntityLiving();
		if(event.getSlot().getSlotType() == EquipmentSlotType.Group.ARMOR)
		{
			// get data
			DefenseData defDataItemFrom = DefenseDataAPI.get(event.getFrom());
			DefenseData defDataItemTo = DefenseDataAPI.get(event.getTo());

			// compute change
			DefenseData newData = new DefenseData();
			newData.substract(defDataItemFrom);
			newData.add(defDataItemTo);

			// apply change
			DefenseDataAPI.add(entity, newData);
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
		if(StartupClientOnly.TOGGLE_HUD.isKeyDown()){
			ClientConfig.toogleHUD();
		}
	}
	
	@SubscribeEvent
	public static void onItemCrafted(ItemCraftedEvent event) {
		ItemStack stack = event.getCrafting();

		DefenseData defData = DefenseDataAPI.get(stack);
		defData.set(new DefenseData(DefaultPropertiesAPI.getDefenseData(stack)));
		
		AttackData atckData = AttackDataAPI.get(stack);
		atckData.set(new AttackData(DefaultPropertiesAPI.getAttackData(stack)));
	}
}
