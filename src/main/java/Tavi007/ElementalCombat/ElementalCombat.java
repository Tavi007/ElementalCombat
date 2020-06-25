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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
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
		event.getServer().getResourceManager().addReloadListener(DATAMANAGER);
		LOGGER.info("Elemental data registered.");	
	}
	
	private void onCommonSetup(FMLCommonSetupEvent event)
    {
        CapabilityManager.INSTANCE.register(IElementalDefenseData.class, new ElementalDefenseDataStorage(), ElementalDefenseData::new);
        CapabilityManager.INSTANCE.register(IElementalAttackData.class, new ElementalAttackDataStorage(), ElementalAttackData::new);
        
        //add here a class/function for loading data from .json
    }
	
}
