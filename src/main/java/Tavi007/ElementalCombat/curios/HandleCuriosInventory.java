package Tavi007.ElementalCombat.curios;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.network.DefenseDataMessageToClient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import net.minecraft.entity.LivingEntity;

public class HandleCuriosInventory {
	@SubscribeEvent
	public static void onCurioChange(CurioChangeEvent event) {

		// get Data
		DefenseData defDataItemFrom = ElementalCombatAPI.getDefenseDataWithEnchantment(event.getFrom());
		DefenseData defDataItemTo = ElementalCombatAPI.getDefenseDataWithEnchantment(event.getTo());

		// compute Change
		DefenseData newData = new DefenseData();
		newData.substract(defDataItemFrom);
		newData.add(defDataItemTo);

		// apply change
		if (!newData.isEmpty()) {
			LivingEntity entity = event.getEntityLiving();
			DefenseData defDataEntity = ElementalCombatAPI.getDefenseData(entity);
			defDataEntity.add(newData);
			DefenseDataMessageToClient messageToClient = new DefenseDataMessageToClient(newData, entity.getUniqueID());
			ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);
		}
	}
}
