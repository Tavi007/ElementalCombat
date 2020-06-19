package Tavi007.ElementalCombat.events;

import java.util.Map;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalData;
import Tavi007.ElementalCombat.capabilities.ElementalAttackData;
import Tavi007.ElementalCombat.capabilities.ElementalAttackDataCapability;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseData;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseDataCapability;
import Tavi007.ElementalCombat.capabilities.IElementalAttackData;
import Tavi007.ElementalCombat.capabilities.IElementalDefenseData;
import net.minecraft.entity.LivingEntity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class ElementifyLivingEntitySpawnEvent 
{
	@SubscribeEvent
	public static void elementifyLivingHurtEvent(CheckSpawn event)
	{
		succesfullSpawnEvent(event);
	}
	

	@SubscribeEvent
	public static void elementifyLivingHurtEvent(SpecialSpawn event)
	{
		succesfullSpawnEvent(event);
	}
	
	private static void succesfullSpawnEvent(LivingEvent event)
	{
		ElementalCombat.LOGGER.info("Elementify Living Spawn Event fired.");
		LivingEntity entity = event.getEntityLiving();
		Map<ResourceLocation, ElementalData> map = ElementalCombat.ELEMDATAMANAGER.getRegisteredElementalData();
		
		String mobname = entity.getDisplayName().getString().toLowerCase();
		System.out.println("mobname: " + mobname);
		
		ResourceLocation rl = new ResourceLocation("default", "entities/minecraft/" + mobname);
		ElementalData elemData = map.get(rl);
		if(elemData != null)
		{
			IElementalAttackData elem_atck_cap = entity.getCapability(ElementalAttackDataCapability.ATK_DATA_CAPABILITY, null).orElse(new ElementalAttackData());
			IElementalDefenseData elem_def_cap = entity.getCapability(ElementalDefenseDataCapability.DEF_DATA_CAPABILITY, null).orElse(new ElementalDefenseData());
			
			elem_atck_cap.setAttackSet(elemData.getAttackSet());
			elem_def_cap.setWeaknessSet(elemData.getWeaknessSet());
			elem_def_cap.setResistanceSet(elemData.getResistanceSet());
			elem_def_cap.setWallSet(elemData.getWallSet());
			elem_def_cap.setAbsorbSet(elemData.getAbsorbSet());
			
			System.out.println("attack: " + elem_atck_cap.getAttackSet());
			System.out.println("weakness: " + elem_def_cap.getWeaknessSet());
		}
		
	}
}
