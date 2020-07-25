package Tavi007.ElementalCombat.events;

import java.util.HashMap;
import java.util.HashSet;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.ElementalDefense;
import Tavi007.ElementalCombat.loading.EntityData;
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
			EntityData entityData = ElementalCombat.DATAMANAGER.getEntityDataFromLocation(rl);
			HashMap<String, Integer> weaknessMap = entityData.getWeaknessMap();
			HashMap<String, Integer> resistanceMap = entityData.getResistanceMap();
			HashSet<String> immunitySet = entityData.getImmunitySet();
			HashSet<String> absorbSet = entityData.getAbsorbSet();

			// get values from armor
			// I should add cross-mod interaction with baubles later
			entity.getArmorInventoryList().forEach((item) ->
			{
				if(item.getEquipmentSlot() != null)
				{
					if(item.getEquipmentSlot().getSlotType() == EquipmentSlotType.Group.ARMOR)
					{
						if(item.getEquipmentSlot() == event.getSlot())
						{
							item = event.getTo();
						}

						ElementalDefense elemDefCapItem = ElementalCombatAPI.getElementalDefenseData(item);
						HashMap<String, Integer> weaknessSetItem = elemDefCapItem.getElementalWeakness();
						HashMap<String, Integer> resistanceSetItem = elemDefCapItem.getElementalResistance();
						HashSet<String> immunitySetItem = elemDefCapItem.getElementalImmunity();
						HashSet<String> absorbSetItem = elemDefCapItem.getElementalAbsorption();

						weaknessMap.putAll(weaknessSetItem);
						resistanceMap.putAll(resistanceSetItem);
						immunitySet.addAll(immunitySetItem);
						absorbSet.addAll(absorbSetItem);
					}
				}
			});

			// set values
			ElementalDefense elemDefCapEntity = ElementalCombatAPI.getElementalDefenseData(entity);
			elemDefCapEntity.setElementalWeakness(weaknessMap);
			elemDefCapEntity.setElementalResistance(resistanceMap);
			elemDefCapEntity.setElementalImmunity(immunitySet);
			elemDefCapEntity.setElementalAbsorption(absorbSet);
		}
	}
}
