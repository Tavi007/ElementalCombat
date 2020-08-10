package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class AddReloadListenerEvent {

	//can't be in  StartupCommon nor in StartupClientOnly, because this even fires on the Forge Eventbus.
	@SubscribeEvent
	public static void onServerAboutToStart(FMLServerAboutToStartEvent event)
	{
		((IReloadableResourceManager) event.getServer().getDataPackRegistries().func_240970_h_()).addReloadListener(ElementalCombat.DATAMANAGER);
		ElementalCombat.LOGGER.info("Elemental data loaded.");	
	}
}
