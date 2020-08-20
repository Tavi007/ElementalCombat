package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class ReloadListenerEvent {

	@SubscribeEvent
	public static void addReloadListenerEvent(AddReloadListenerEvent event)
	{
		event.addListener(ElementalCombat.COMBAT_PROPERTIES_MANGER);
		ElementalCombat.LOGGER.info("Elemental data loaded.");	
	}
}
