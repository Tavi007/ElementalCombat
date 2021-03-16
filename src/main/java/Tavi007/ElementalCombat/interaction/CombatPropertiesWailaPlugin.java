package Tavi007.ElementalCombat.interaction;

import Tavi007.ElementalCombat.ElementalCombat;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

@WailaPlugin(ElementalCombat.MOD_ID)
public class CombatPropertiesWailaPlugin implements IWailaPlugin {

	static final ResourceLocation COMBAT_PROPERTIES = new ResourceLocation(ElementalCombat.MOD_ID, "combat_properties");
	
	@Override
	public void register(IRegistrar registrar) {
        registrar.registerComponentProvider(HUDHandlerEntities.INSTANCE, TooltipPosition.BODY, LivingEntity.class);
		registrar.registerTooltipRenderer(COMBAT_PROPERTIES, new WailaTooltipRenderer());
		ElementalCombat.LOGGER.info("Waila Plugin registered.");
	}

}
