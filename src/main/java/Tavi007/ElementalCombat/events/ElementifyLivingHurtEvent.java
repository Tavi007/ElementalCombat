package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class ElementifyLivingHurtEvent 
{
	@SubscribeEvent
	public static void elementifyLivingHurtEvent(LivingHurtEvent event)
	{
		ElementalCombat.LOGGER.info("Elementify Living Hurt Event fired.");
		LivingEntity target = event.getEntityLiving();
		DamageSource damageSource = event.getSource();
		float damageAmount = event.getAmount();
		
		Entity source = damageSource.getImmediateSource();
		String sourceName = source.getEntityString();
		
		String targetName = target.getEntityString();
		
		// testing area
		event.setAmount(99);
		
		
	}
}
