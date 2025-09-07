package Tavi007.ElementalCombat.common.api.data;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.registry.ConditionRegistry;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Function4;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class Condition {

    public static Condition BASE = new Condition("base", null); // always true

    private final String id;
    private final String descriptionId;
    private final JsonObject arguments;

    private final Function4<Entity, ItemStack, Level, JsonObject, Boolean> check;

    public Condition(String id, JsonObject arguments) {
        this.id = id;
        this.descriptionId = Constants.MOD_ID + ".condition." + id;
        this.arguments = arguments;
        this.check = ConditionRegistry.get(id);
    }

    public Condition(String id, String descriptionId, JsonObject arguments) {
        this.id = id;
        this.descriptionId = descriptionId;
        this.arguments = arguments;
        this.check = ConditionRegistry.get(id);
    }

    public String getId() {
        return id;
    }

    public String getDescriptionId() {
        return descriptionId;
    }

    public boolean isActive(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        return this.check.apply(entity, stack, level, this.arguments);
    }

    @Override
    public String toString() {
        return id + ": " + arguments;
    }
}
