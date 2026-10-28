package Tavi007.ElementalCombat.common.data.capabilities;


import com.google.gson.JsonObject;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface ConditionPredicate {
    Boolean apply(Entity entity, ItemStack stack, Level level, JsonObject json);
}
