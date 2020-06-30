package Tavi007.ElementalCombat.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.ElementalAttackData;
import Tavi007.ElementalCombat.capabilities.ElementalAttackDataCapability;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseData;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseDataCapability;
import Tavi007.ElementalCombat.capabilities.IElementalAttackData;
import Tavi007.ElementalCombat.capabilities.IElementalDefenseData;
import Tavi007.ElementalCombat.loading.EntityData;
import Tavi007.ElementalCombat.loading.AttackFormat;
import net.minecraft.entity.LivingEntity;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
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
		
		// load default data from dataManager
		ResourceLocation rl = new ResourceLocation(entity.getType().getRegistryName().getNamespace(), "elementalproperties/entities/" + entity.getType().getRegistryName().getPath());
		EntityData entityData = ElementalCombat.DATAMANAGER.getEntityDataFromLocation(rl);
		Set<String> weaknessSet = entityData.getWeaknessSet();
		Set<String> resistanceSet = entityData.getResistanceSet();
		Set<String> immunitySet = entityData.getImmunitySet();
		Set<String> absorbSet = entityData.getAbsorbSet();
		// rewrite set to mapping
		Set<AttackFormat> attackFormatSet = entityData.getAttackSet();
		Map<String, Integer> attackMap = new HashMap<String, Integer>();
		attackFormatSet.forEach((attack) ->
		{
			Integer value = attack.getValue();
			if (value == 0)
			{
				ElementalCombat.LOGGER.info("Elemental damage value of " + attack.getName() + " for " + entity.getName().toString() + " is 0. Using 1 instead.");
				value = 1;
			}
			attackMap.put(attack.getName(), value);
		});
		
		// player spawn should be biome independent
		if(entityData.getBiomeDependency()) 
		{
			String biomeProperties = null;
			BlockPos blockPos = new BlockPos(entity.getPositionVec());
			
			TempCategory category = entity.getEntityWorld().getBiome(blockPos).getTempCategory();
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

		// set capability
		IElementalAttackData elemAtckCap = entity.getCapability(ElementalAttackDataCapability.ATK_DATA_CAPABILITY, null).orElse(new ElementalAttackData());
		IElementalDefenseData elemDefCap = entity.getCapability(ElementalDefenseDataCapability.DEF_DATA_CAPABILITY, null).orElse(new ElementalDefenseData());
		elemAtckCap.setAttackMap(attackMap);
		elemDefCap.setWeaknessSet(weaknessSet);
		elemDefCap.setResistanceSet(resistanceSet);
		elemDefCap.setImmunitySet(immunitySet);
		elemDefCap.setAbsorbSet(absorbSet);
	}
}
