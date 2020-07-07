package Tavi007.ElementalCombat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tavi007.ElementalCombat.capabilities.ElementalAttackData;
import Tavi007.ElementalCombat.capabilities.ElementalAttackDataStorage;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseData;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseDataStorage;
import Tavi007.ElementalCombat.capabilities.IElementalAttackData;
import Tavi007.ElementalCombat.capabilities.IElementalDefenseData;
import Tavi007.ElementalCombat.loading.DataManager;
import Tavi007.ElementalCombat.particle.CombatParticleData;
import Tavi007.ElementalCombat.particle.CombatParticleFactory;
import Tavi007.ElementalCombat.particle.CombatParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("elementalcombat")
public class ElementalCombat 
{
	public static ElementalCombat instance;
	public static final String MOD_ID = "elementalcombat";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static DataManager DATAMANAGER = new DataManager();
	public static ParticleType<CombatParticleData> combatParticleType;
	
	public ElementalCombat()
	{
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
		modEventBus.addListener(this::doClientStuff);
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onServerAboutToStart);
		
		instance = this;
        MinecraftForge.EVENT_BUS.register(this);
        
	}
	
	private void setup(final FMLCommonSetupEvent event)
	{
		LOGGER.info("setup method registered.");
	}
	
	private void doClientStuff(final FMLClientSetupEvent event)
	{
		LOGGER.info("clientRegistries method registered.");	
	}
	
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event)
	{
		
	}

	@SubscribeEvent
	public void onServerAboutToStart(FMLServerAboutToStartEvent event)
	{
		((IReloadableResourceManager) event.getServer().getDataPackRegistries().func_240970_h_()).addReloadListener(DATAMANAGER);
		LOGGER.info("Elemental data registered.");	
	}
	  
	@SubscribeEvent
	public static void onIParticleTypeRegistration(RegistryEvent.Register<ParticleType<?>> iParticleTypeRegisterEvent) {
	    combatParticleType = new CombatParticleType();
	    combatParticleType.setRegistryName("elementalcombat:particles/weakness");
	    iParticleTypeRegisterEvent.getRegistry().register(combatParticleType);
	    LOGGER.info("ElementalCombat particles registered.");
	}

	@SubscribeEvent
	public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {

		// beware - there are two registerFactory methods with different signatures.
		// If you use the wrong one it will put Minecraft into an infinite loading loop with no console errors
		Minecraft.getInstance().particles.registerFactory(combatParticleType, sprite -> new CombatParticleFactory(sprite));
		//  This lambda may not be obvious: its purpose is:
		//  the registerFactory method creates an IAnimatedSprite, then passes it to the constructor of FlameParticleFactory

		//  General rule of thumb:
		// If you are creating a TextureParticle with a corresponding json to specify textures which will be stitched into the
		//    particle texture sheet, then use the 1-parameter constructor method
		// If you're supplying the render yourself, or using a texture from the block sheet, use the 0-parameter constructor method
		//   (examples are MobAppearanceParticle, DiggingParticle).  See ParticleManager::registerFactories for more.
	}

	private void onCommonSetup(FMLCommonSetupEvent event)
    {
        CapabilityManager.INSTANCE.register(IElementalDefenseData.class, new ElementalDefenseDataStorage(), ElementalDefenseData::new);
        CapabilityManager.INSTANCE.register(IElementalAttackData.class, new ElementalAttackDataStorage(), ElementalAttackData::new);
    }
	
}
