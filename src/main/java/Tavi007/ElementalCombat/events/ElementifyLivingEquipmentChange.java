package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.network.DefenseDataMessageToClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class ElementifyLivingEquipmentChange 
{
	@SubscribeEvent
	public static void elementifyLivingEquipmentChange(LivingEquipmentChangeEvent event)
	{
		//change defense properties
		LivingEntity entity = event.getEntityLiving();
		if(event.getSlot().getSlotType() == EquipmentSlotType.Group.ARMOR)
		{
			// get Data
			DefenseData defDataItemFrom = ElementalCombatAPI.getDefenseDataWithEnchantment(event.getFrom());
			DefenseData defDataItemTo = ElementalCombatAPI.getDefenseDataWithEnchantment(event.getTo());

			// compute Change
			DefenseData newData = new DefenseData();
			newData.substract(defDataItemFrom);
			newData.add(defDataItemTo);

			// apply change
			if (!newData.isEmpty()) {
				DefenseData defDataEntity = ElementalCombatAPI.getDefenseData(entity);
				defDataEntity.add(newData);
				DefenseDataMessageToClient messageToClient = new DefenseDataMessageToClient(newData, entity.getUniqueID());
				ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);
			}
		}
	}

	// since elementifyLivingEquipmentChange is fired, whenever a player logs in,
	// I need to 'remove' armor-stats from the player, when logging out. 
	@SubscribeEvent
	public static void onPlayerLoggedOutEvent(PlayerLoggedOutEvent event) {
		PlayerEntity entity = event.getPlayer();
		if (entity != null) {
			DefenseData defCapEntity = ElementalCombatAPI.getDefenseData(entity);
			entity.getArmorInventoryList().forEach(item -> {
				if (!item.isEmpty()) {
					DefenseData defCapItem = ElementalCombatAPI.getDefenseDataWithEnchantment(item);
					defCapEntity.substract(defCapItem);
				}
			});
		}
	}
}
