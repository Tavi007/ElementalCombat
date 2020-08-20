package Tavi007.ElementalCombat.events;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.loading.EntityCombatProperties;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

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

			// get default values
			ResourceLocation rl = new ResourceLocation(entity.getType().getRegistryName().getNamespace(), "elementalproperties/entities/" + entity.getType().getRegistryName().getPath());
			EntityCombatProperties entityData = ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rl);
			HashMap<String, Double> defenseStyle = entityData.getDefenseStyle();
			HashMap<String, Double> defenseElement = entityData.getDefenseElement();
			// get values from armor
			// I should add cross-mod interaction with baubles later
			entity.getArmorInventoryList().forEach((item) ->
			{
				DefenseData defCapItem = ElementalCombatAPI.getDefenseData(item);
				HashMap<String, Double> defenseStyleItem = defCapItem.getStyleScaling();
				HashMap<String, Double> defenseElementItem = defCapItem.getElementScaling();

				defenseStyle.putAll(defenseStyleItem);
				defenseElement.putAll(defenseElementItem);
			});

			// set values
			DefenseData defCapEntity = ElementalCombatAPI.getDefenseData(entity);
			defCapEntity.setStyleScaling(defenseStyle);
			defCapEntity.setElementScaling(defenseElement);
		}
	}
}
