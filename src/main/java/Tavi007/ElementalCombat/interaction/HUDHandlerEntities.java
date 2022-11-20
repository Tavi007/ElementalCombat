package Tavi007.ElementalCombat.interaction;

import java.util.List;

import mcp.mobius.waila.api.IDrawableText;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.entity.LivingEntity;

public class HUDHandlerEntities implements IEntityComponentProvider {

    static final IEntityComponentProvider INSTANCE = new HUDHandlerEntities();

    @Override
    public void appendBody(List<ITextComponent> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (accessor.getEntity() instanceof LivingEntity) {
            CompoundNBT nbt = new CompoundNBT();
            tooltip.add(IDrawableText.of(CombatPropertiesWailaPlugin.COMBAT_PROPERTIES, nbt));
        }
    }
}
