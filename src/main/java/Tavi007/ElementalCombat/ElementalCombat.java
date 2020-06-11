package Tavi007.ElementalCombat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tavi007.ElementalCombat.capabilities.ElementalDefenceData;
import Tavi007.ElementalCombat.capabilities.ElementalDefenceDataStorage;
import Tavi007.ElementalCombat.capabilities.IElementalDefenceData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("elementalcombat")
public class ElementalCombat 
{
	public static ElementalCombat instance;
	public static final String MOD_ID = "elementalcombat";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	public ElementalCombat()
	{
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
		modEventBus.addListener(this::doClientStuff);
        modEventBus.addListener(this::onCommonSetup);
		
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
	
	private void onCommonSetup(FMLCommonSetupEvent e)
    {
        CapabilityManager.INSTANCE.register(IElementalDefenceData.class, new ElementalDefenceDataStorage(), ElementalDefenceData::new);
        
    }
	
}
