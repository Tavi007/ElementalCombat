package Tavi007.ElementalCombat.init;

import org.lwjgl.glfw.GLFW;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.particle.CombatParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class StartupClientOnly {

	public static final KeyBinding TOGGLE_HUD = new KeyBinding("Toggle HUD", KeyConflictContext.UNIVERSAL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_J, "Elemental Combat");

	@SubscribeEvent
	public static void onClientSetupEvent(final FMLClientSetupEvent event)
	{
		//key bindings
		ClientRegistry.registerKeyBinding(TOGGLE_HUD);

		//ItemProperties
		ItemModelsProperties.registerProperty(ItemList.LIGHT_BOW.get(), new ResourceLocation("pull"), (stack, world, entity) -> {
			if (entity == null) {
				return 0.0F;
			} else {
				return entity.getActiveItemStack() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getItemInUseCount()) / 20.0F;
			}
		});
		ItemModelsProperties.registerProperty(ItemList.LIGHT_BOW.get(), new ResourceLocation("pulling"), (stack, world, entity) -> {
			return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
		});

		ItemModelsProperties.registerProperty(ItemList.DAYNIGHT_CHESTPLATE.get(), new ResourceLocation(ElementalCombat.MOD_ID + ":isday"), (stack, world, entity) -> {
			if(entity != null) {
				if(entity.world != null) {
					long time = entity.world.getDayTime();
					if (time >= 2000 && time<=10000) {
						return 1.0F;
					}
					else if (time >= 14000 && time<=22000) {
						return 0.0F;
					}
					else {
						return 0.5F;
					}
				}
			}
			return 0.0F;
		});


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
