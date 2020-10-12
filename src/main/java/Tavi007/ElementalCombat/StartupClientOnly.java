package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.particle.CombatParticle;
import Tavi007.ElementalCombat.particle.ParticleList;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class StartupClientOnly {
	
	@SubscribeEvent
	public static void onClientSetupEvent(final FMLClientSetupEvent event)
	{
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Configuration.CONFIG_SPEC);
		ElementalCombat.LOGGER.info("ElementalCombat clientRegistries method registered.");	
	}
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particles.registerFactory(ParticleList.ELEMENT_WEAKNESS.get(), CombatParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(ParticleList.ELEMENT_RESISTANCE.get(), CombatParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(ParticleList.ELEMENT_IMMUNITY.get(), CombatParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(ParticleList.ELEMENT_ABSORPTION.get(), CombatParticle.Factory::new);
		
		Minecraft.getInstance().particles.registerFactory(ParticleList.STYLE_WEAKNESS.get(), CombatParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(ParticleList.STYLE_RESISTANCE.get(), CombatParticle.Factory::new);
		Minecraft.getInstance().particles.registerFactory(ParticleList.STYLE_IMMUNITY.get(), CombatParticle.Factory::new);
		
	    ElementalCombat.LOGGER.info("ElementalCombat particles factory registered.");
	}
}
