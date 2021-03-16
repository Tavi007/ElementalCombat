package Tavi007.ElementalCombat.interaction;

import Tavi007.ElementalCombat.ElementalCombat;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.util.ResourceLocation;

@WailaPlugin(ElementalCombat.MOD_ID)
public class CombatPropertiesWailaPlugin implements IWailaPlugin {

	@Override
	public void register(IRegistrar registrar) {
		registrar.registerTooltipRenderer(new ResourceLocation(ElementalCombat.MOD_ID, "combat_properties"), new WailaTooltipRenderer());
		ElementalCombat.LOGGER.info("Waila Plugin registered.");
	}

}
