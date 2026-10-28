package Tavi007.ElementalCombat.common.data.capabilities.defense;

import Tavi007.ElementalCombat.common.data.capabilities.Condition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public record DefenseLayer(
        Set<Condition> conditions,
        DefenseProperty property) {

    public DefenseLayer(DefenseProperty property) {
        this(new HashSet<>(), property);
    }

    public DefenseLayer(DefenseLayer other) {
        this(other.conditions(), other.property());
    }

    public static DefenseLayer read(FriendlyByteBuf buf) {
        Set<Condition> conditions = buf.readCollection(HashSet::new, Condition::read);
        DefenseProperty property = DefenseProperty.read(buf);
        return new DefenseLayer(conditions, property);
    }

    public static void write(FriendlyByteBuf buf, DefenseLayer layer) {
        buf.writeCollection(layer.conditions, Condition::write);
        DefenseProperty.write(layer.property, buf);
    }

    public static CompoundTag writeTag(DefenseLayer layer) {
        CompoundTag tag = new CompoundTag();
        ListTag conditionTag = new ListTag();
        for (Condition condition : layer.conditions) {
            conditionTag.add(Condition.writeTag(condition));
        }
        tag.put("conditions", conditionTag);
        tag.put("property", DefenseProperty.writeTag(layer.property));
        return tag;
    }


    public static DefenseLayer readTag(CompoundTag tag) {
        Set<Condition> conditions = new HashSet<>();
        for (Tag condtionTag : tag.getList("conditions", ListTag.TAG_COMPOUND)) {
            conditions.add(Condition.readTag((CompoundTag) condtionTag));
        }
        DefenseProperty property = DefenseProperty.readTag(tag.getCompound("property"));
        return new DefenseLayer(conditions, property);
    }


    public DefenseMap getStyle() {
        return property.style();
    }

    public DefenseMap getElement() {
        return property.element();
    }

    public boolean isActive(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        for (Condition condition : conditions) {
            if (condition.isInactive(entity, stack, level)) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid() {
        for (Condition condition : conditions) {
            if (!condition.isValid()) {
                return false;
            }
        }
        return property.isValid();
    }
}
