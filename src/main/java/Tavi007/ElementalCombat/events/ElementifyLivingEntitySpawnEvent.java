package Tavi007.ElementalCombat.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalData;
import Tavi007.ElementalCombat.ElementalData.AttackFormat;
import Tavi007.ElementalCombat.capabilities.ElementalAttackData;
import Tavi007.ElementalCombat.capabilities.ElementalAttackDataCapability;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseData;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseDataCapability;
import Tavi007.ElementalCombat.capabilities.IElementalAttackData;
import Tavi007.ElementalCombat.capabilities.IElementalDefenseData;
import net.minecraft.entity.LivingEntity;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome.TempCategory;
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
		LivingEntity entity = event.getEntityLiving();
		ResourceLocation rl = new ResourceLocation(entity.getType().getRegistryName().getNamespace(), "elementalproperties/entities/" + entity.getDisplayName().getString().toLowerCase().replace(" ", "_"));
		ElementalData elemData = ElementalCombat.ELEMDATAMANAGER.getElementalDataFromLocation(rl);
		if(elemData != null)
		{
			IElementalAttackData elem_atck_cap = entity.getCapability(ElementalAttackDataCapability.ATK_DATA_CAPABILITY, null).orElse(new ElementalAttackData());
			IElementalDefenseData elem_def_cap = entity.getCapability(ElementalDefenseDataCapability.DEF_DATA_CAPABILITY, null).orElse(new ElementalDefenseData());
			
			Set<AttackFormat> attackFormatSet = elemData.getAttackSet();
			Map<String, Integer> attackMap = new HashMap<String, Integer>();
			attackFormatSet.forEach((attack) ->
			{
				attackMap.put(attack.getName(), attack.getValue());
			});
			Set<String> weaknessSet = elemData.getWeaknessSet();
			Set<String> resistanceSet = elemData.getResistanceSet();
			Set<String> immunitySet = elemData.getImmunitySet();
			Set<String> absorbSet = elemData.getAbsorbSet();
			
			
			if(elemData.getBiomeDependency())
			{
				String biomeProperties = null;
				TempCategory category = entity.getEntityWorld().getBiome(entity.getPosition()).getTempCategory();
				if(category == TempCategory.COLD)
				{
					biomeProperties = "ice";
				}
				else if(category == TempCategory.WARM)
				{
					biomeProperties = "fire";
				}
				else if(category == TempCategory.OCEAN)
				{
					biomeProperties = "water";
				}
				if(biomeProperties != null)
				{
					weaknessSet.remove(biomeProperties);
					resistanceSet.add(biomeProperties);
				}
			}
			
			elem_atck_cap.setAttackMap(attackMap);
			elem_def_cap.setWeaknessSet(weaknessSet);
			elem_def_cap.setResistanceSet(resistanceSet);
			elem_def_cap.setImmunitySet(immunitySet);
			elem_def_cap.setAbsorbSet(absorbSet);
		}
		
	}
}
