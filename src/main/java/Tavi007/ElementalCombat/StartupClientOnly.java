package Tavi007.ElementalCombat;

import org.lwjgl.glfw.GLFW;

import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.particle.CombatParticle;
import Tavi007.ElementalCombat.particle.ParticleList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class StartupClientOnly {
	
	public static final KeyBinding TOGGLE_HUD = new KeyBinding("Toggle HUD", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_J, "Elemental Combat");
	
	@SubscribeEvent
	public static void onClientSetupEvent(final FMLClientSetupEvent event)
	{
		//config
		ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.CONFIG_SPEC, ElementalCombat.MOD_ID + "-client.toml");
		//key bindings
		ClientRegistry.registerKeyBinding(TOGGLE_HUD);
		
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
