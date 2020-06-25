package Tavi007.ElementalCombat.events;

import java.util.HashSet;
import java.util.Set;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.ElementalAttackData;
import Tavi007.ElementalCombat.capabilities.ElementalAttackDataCapability;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseData;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseDataCapability;
import Tavi007.ElementalCombat.capabilities.IElementalAttackData;
import Tavi007.ElementalCombat.capabilities.IElementalDefenseData;
import Tavi007.ElementalCombat.loading.AttackFormat;
import Tavi007.ElementalCombat.loading.EntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
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
			Set<String> weaknessSet = entityData.getWeaknessSet();
			Set<String> resistanceSet = entityData.getResistanceSet();
			Set<String> immunitySet = entityData.getImmunitySet();
			Set<String> absorbSet = entityData.getAbsorbSet();
			
			// get values from armor
			// I should add cross-mod interaction with baubles later
			entity.getArmorInventoryList().forEach((item) ->
			{
				if(item.getEquipmentSlot().getSlotType() == EquipmentSlotType.Group.ARMOR)
				{
					if(item.getEquipmentSlot() == event.getSlot())
					{
						item = event.getTo();
					}
					
					IElementalDefenseData elemDefCapItem = item.getCapability(ElementalDefenseDataCapability.DEF_DATA_CAPABILITY, null).orElse(new ElementalDefenseData());
					Set<String> weaknessSetItem = elemDefCapItem.getWeaknessSet();
					Set<String> resistanceSetItem = elemDefCapItem.getResistanceSet();
					Set<String> immunitySetItem = elemDefCapItem.getImmunitySet();
					Set<String> absorbSetItem = elemDefCapItem.getAbsorbSet();
					
					weaknessSet.addAll(weaknessSetItem);
					resistanceSet.addAll(resistanceSetItem);
					immunitySet.addAll(immunitySetItem);
					absorbSet.addAll(absorbSetItem);
				}
			});
			
			// set values
			IElementalDefenseData elemDefCapEntity = entity.getCapability(ElementalDefenseDataCapability.DEF_DATA_CAPABILITY, null).orElse(new ElementalDefenseData());
			elemDefCapEntity.setWeaknessSet(weaknessSet);
			elemDefCapEntity.setResistanceSet(resistanceSet);
			elemDefCapEntity.setImmunitySet(immunitySet);
			elemDefCapEntity.setAbsorbSet(absorbSet);
		}
	}
}
