package Tavi007.ElementalCombat.events;

import java.util.HashSet;
import java.util.Set;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseData;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseDataCapability;
import Tavi007.ElementalCombat.capabilities.IElementalDefenseData;
import net.minecraft.entity.LivingEntity;
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
		LivingEntity entity = event.getEntityLiving();
		Set<String> weaknessSetEntity = new HashSet<String>();
		Set<String> resistanceSetEntity = new HashSet<String>();
		Set<String> immunitySetEntity = new HashSet<String>();
		Set<String> absorbSetEntity = new HashSet<String>();
		
		entity.getArmorInventoryList().forEach((item) ->
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
			
			weaknessSetEntity.addAll(weaknessSetItem);
			resistanceSetEntity.addAll(resistanceSetItem);
			immunitySetEntity.addAll(immunitySetItem);
			absorbSetEntity.addAll(absorbSetItem);
		});
		
		// this seems to be rather inefficient. However these lists are usually really short, so this might not be a problem.
		// if this turns out to be a problem, then change the the list-filling up above. 
		weaknessSetEntity.removeAll(absorbSetEntity);
		resistanceSetEntity.removeAll(absorbSetEntity);
		immunitySetEntity.removeAll(absorbSetEntity);

		weaknessSetEntity.removeAll(immunitySetEntity);
		resistanceSetEntity.removeAll(immunitySetEntity);

		weaknessSetEntity.removeAll(resistanceSetEntity);
		
		IElementalDefenseData elemDefCapEntity = entity.getCapability(ElementalDefenseDataCapability.DEF_DATA_CAPABILITY, null).orElse(new ElementalDefenseData());
		elemDefCapEntity.setWeaknessSet(weaknessSetEntity);
		elemDefCapEntity.setResistanceSet(resistanceSetEntity);
		elemDefCapEntity.setImmunitySet(immunitySetEntity);
		elemDefCapEntity.setAbsorbSet(absorbSetEntity);
	}
}
