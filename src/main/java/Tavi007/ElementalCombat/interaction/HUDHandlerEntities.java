package Tavi007.ElementalCombat.interaction;

import java.util.List;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.RenderableTextComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.*;

public class HUDHandlerEntities implements IEntityComponentProvider {
	
    static final IEntityComponentProvider INSTANCE = new HUDHandlerEntities();

	@Override
	public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
		if (accessor.getEntity() instanceof LivingEntity) {
			CompoundNBT nbt = new CompoundNBT();
            tooltip.add(new RenderableTextComponent(CombatPropertiesWailaPlugin.COMBAT_PROPERTIES, nbt));
		}
	}
}
