package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.events.AttachCapabilityEvent;
import Tavi007.ElementalCombat.particle.AbsorbParticleData;
import Tavi007.ElementalCombat.particle.AbsorbParticleType;
import Tavi007.ElementalCombat.particle.ImmunityParticleData;
import Tavi007.ElementalCombat.particle.ImmunityParticleType;
import Tavi007.ElementalCombat.particle.ResistanceParticleData;
import Tavi007.ElementalCombat.particle.ResistanceParticleType;
import Tavi007.ElementalCombat.particle.WeaknessParticleData;
import Tavi007.ElementalCombat.particle.WeaknessParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

public class StartupCommon {

	public static ParticleType<WeaknessParticleData> weaknessParticleType;
	public static ParticleType<ResistanceParticleData> resistanceParticleType;
	public static ParticleType<ImmunityParticleData> immunityParticleType;
	public static ParticleType<AbsorbParticleData> absorbParticleType;

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
		weaknessParticleType = new WeaknessParticleType();
		weaknessParticleType.setRegistryName("elementalcombat:weakness");
	    iParticleTypeRegisterEvent.getRegistry().register(weaknessParticleType);

		resistanceParticleType = new ResistanceParticleType();
		resistanceParticleType.setRegistryName("elementalcombat:resistance");
	    iParticleTypeRegisterEvent.getRegistry().register(resistanceParticleType);

		immunityParticleType = new ImmunityParticleType();
		immunityParticleType.setRegistryName("elementalcombat:immunity");
	    iParticleTypeRegisterEvent.getRegistry().register(immunityParticleType);

		absorbParticleType = new AbsorbParticleType();
		absorbParticleType.setRegistryName("elementalcombat:absorb");
	    iParticleTypeRegisterEvent.getRegistry().register(absorbParticleType);
	    
	    
	    ElementalCombat.LOGGER.info("ElementalCombat particles registered.");
	}
}