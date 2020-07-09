package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.events.AttachCapabilityEvent;
import Tavi007.ElementalCombat.particle.CombatParticleData;
import Tavi007.ElementalCombat.particle.CombatParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

public class StartupCommon {

	public static ParticleType<CombatParticleData> combatParticleType;

	private void onCommonSetup(FMLCommonSetupEvent event){
		MinecraftForge.EVENT_BUS.register(AttachCapabilityEvent.class);
        ElementalCombat.LOGGER.info("setup method registered.");
    }

	@SubscribeEvent
	public void onServerAboutToStart(FMLServerAboutToStartEvent event)
	{
		((IReloadableResourceManager) event.getServer().getDataPackRegistries().func_240970_h_()).addReloadListener(ElementalCombat.DATAMANAGER);
		ElementalCombat.LOGGER.info("Elemental data loaded.");	
	}
	
	@SubscribeEvent
	public static void onIParticleTypeRegistration(RegistryEvent.Register<ParticleType<?>> iParticleTypeRegisterEvent) {
	    combatParticleType = new CombatParticleType();
	    combatParticleType.setRegistryName("elementalcombat:weakness");
	    iParticleTypeRegisterEvent.getRegistry().register(combatParticleType);
	    ElementalCombat.LOGGER.info("ElementalCombat particles registered.");
	}
}
