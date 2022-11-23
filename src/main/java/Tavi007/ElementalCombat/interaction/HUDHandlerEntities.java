package Tavi007.ElementalCombat.interaction;

import java.util.List;

import mcp.mobius.waila.api.IDrawableText;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

public class HUDHandlerEntities implements IEntityComponentProvider {

    static final IEntityComponentProvider INSTANCE = new HUDHandlerEntities();

    @Override
    public void appendBody(List<Component> tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (accessor.getEntity() instanceof LivingEntity) {
            CompoundTag nbt = new CompoundTag();
            tooltip.add(IDrawableText.of(CombatPropertiesWailaPlugin.COMBAT_PROPERTIES, nbt));
        }
    }
}
