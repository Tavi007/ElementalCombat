package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.particle.CombatParticle;
import Tavi007.ElementalCombat.particle.ParticleList;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class StartupClientOnly {
	
	@SubscribeEvent
	public static void onClientSetupEvent(final FMLClientSetupEvent event)
	{
		ElementalCombat.LOGGER.info("clientRegistries method registered.");	
	}
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particles.registerFactory(ParticleList.WEAKNESS_PARTICLE.get(), CombatParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(ParticleList.RESISTANCE_PARTICLE.get(), CombatParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(ParticleList.IMMUNITY_PARTICLE.get(), CombatParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(ParticleList.ABSORPTION_PARTICLE.get(), CombatParticle.Factory::new);
		
	    ElementalCombat.LOGGER.info("ElementalCombat particles factory registered.");
	}
}
