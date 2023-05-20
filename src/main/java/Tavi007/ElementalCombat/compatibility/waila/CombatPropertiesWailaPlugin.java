package Tavi007.ElementalCombat.compatibility.waila;

import Tavi007.ElementalCombat.ElementalCombat;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class CombatPropertiesWailaPlugin implements IWailaPlugin {

    static final ResourceLocation COMBAT_PROPERTIES = new ResourceLocation(ElementalCombat.MOD_ID, "combat_properties");

    @Override
    public void register(IRegistrar registrar) {
        registrar.addComponent(HUDHandlerEntities.INSTANCE, TooltipPosition.BODY, LivingEntity.class);
        ElementalCombat.LOGGER.info("Waila Plugin registered.");
    }
}
