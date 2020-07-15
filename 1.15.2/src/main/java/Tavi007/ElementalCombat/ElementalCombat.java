package Tavi007.ElementalCombat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tavi007.ElementalCombat.loading.DataManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("elementalcombat")
public class ElementalCombat 
{
	public static ElementalCombat instance;
	public static final String MOD_ID = "elementalcombat";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static DataManager DATAMANAGER = new DataManager();
    public static IEventBus MOD_EVENT_BUS;
	
	public ElementalCombat()
	{
		MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
		
		//register everything
		MOD_EVENT_BUS.register(StartupCommon.class);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> ElementalCombat::registerClientOnlyEvents);
		
		instance = this;
        MinecraftForge.EVENT_BUS.register(this);
	}
	
	public static void registerClientOnlyEvents() {
		MOD_EVENT_BUS.register(StartupClientOnly.class);
	}

	@SubscribeEvent
	public void onServerAboutToStart(FMLServerAboutToStartEvent event)
	{
		event.getServer().getResourceManager().addReloadListener(ElementalCombat.DATAMANAGER);
		ElementalCombat.LOGGER.info("Elemental data loaded.");	
	}
	
}
