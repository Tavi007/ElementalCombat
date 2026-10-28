package Tavi007.ElementalCombat.common.data.capabilities;

import Tavi007.ElementalCombat.common.registry.ConditionRegistry;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record Condition(String id, @Nullable JsonObject additionalData) {

    public static void write(FriendlyByteBuf buf, Condition condition) {
        buf.writeUtf(condition.id);
        boolean hasData = condition.additionalData != null;
        buf.writeBoolean(hasData);
        if (hasData) {
            buf.writeUtf(condition.additionalData.toString());
        }
    }

    public static Condition read(FriendlyByteBuf buf) {
        String id = buf.readUtf();
        boolean hasData = buf.readBoolean();
        JsonObject additionalData = null;
        if (hasData) {
            additionalData = JsonParser.parseString(buf.readUtf()).getAsJsonObject();
        }
        return new Condition(id, additionalData);
    }

    public static CompoundTag writeTag(Condition condition) {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", condition.id);
        boolean hasData = condition.additionalData != null;
        tag.putBoolean("has_data", hasData);
        if (hasData) {
            tag.putString("additional_data", condition.additionalData.toString());
        }
        return tag;
    }

    public static Condition readTag(CompoundTag tag) {
        String id = tag.getString("id");
        boolean hasData = tag.getBoolean("has_data");
        JsonObject additionalData = null;
        if (hasData) {
            additionalData = (JsonObject) JsonParser.parseString(tag.getString("additional_data"));
        }
        return new Condition(id, additionalData);
    }

    public boolean isInactive(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        return !ConditionRegistry.get(id).apply(entity, stack, level, additionalData);
    }

    public List<String> getDescriptionArguments() {
        return List.of();
    }

    public boolean isValid() {
        return id != null;
    }
}
