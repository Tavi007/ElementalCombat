package Tavi007.ElementalCombat.interaction;

import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.world.entity.LivingEntity;

public class HUDHandlerEntities implements IEntityComponentProvider {

    static final IEntityComponentProvider INSTANCE = new HUDHandlerEntities();

    @Override
    public void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (accessor.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) accessor.getEntity();
            tooltip.addLine(new WailaTooltipComponent(
                AttackDataAPI.getFullDataAsLayer(entity),
                DefenseDataAPI.getFullDataAsLayer(entity)));
        }
    }
}
