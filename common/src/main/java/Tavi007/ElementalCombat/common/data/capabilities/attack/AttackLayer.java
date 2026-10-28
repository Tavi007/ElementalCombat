package Tavi007.ElementalCombat.common.data.capabilities.attack;

import Tavi007.ElementalCombat.common.data.capabilities.Condition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public record AttackLayer(
        Set<Condition> conditions,
        int priority,
        AttackProperty property
) {

    public static Comparator<AttackLayer> COMPARATOR = Comparator.comparing(property -> property.priority);

    public AttackLayer(AttackLayer other) {
        this(other.conditions(), other.priority(), other.property());
    }

    public AttackLayer(int priority, AttackProperty property) {
        this(new HashSet<>(), priority, property);
    }

    public static AttackLayer read(FriendlyByteBuf buf) {
        Set<Condition> conditions = buf.readCollection(HashSet::new, Condition::read);
        int priority = buf.readInt();
        AttackProperty property = AttackProperty.read(buf);
        return new AttackLayer(conditions, priority, property);
    }

    public static void write(FriendlyByteBuf buf, AttackLayer layer) {
        buf.writeCollection(layer.conditions, Condition::write);
        buf.writeInt(layer.priority);
        AttackProperty.write(buf, layer.property);
    }

    public static CompoundTag writeTag(AttackLayer layer) {
        CompoundTag tag = new CompoundTag();
        ListTag conditionTag = new ListTag();
        for (Condition condition : layer.conditions) {
            conditionTag.add(Condition.writeTag(condition));
        }
        tag.put("conditions", conditionTag);
        tag.putInt("priority", layer.priority);
        tag.put("property", AttackProperty.writeTag(layer.property));
        return tag;
    }


    public static AttackLayer readTag(CompoundTag tag) {
        Set<Condition> conditions = new HashSet<>();
        for (Tag condtionTag : tag.getList("conditions", ListTag.TAG_COMPOUND)) {
            conditions.add(Condition.readTag((CompoundTag) condtionTag));
        }
        int priority = tag.getInt("priority");
        AttackProperty property = AttackProperty.readTag(tag.getCompound("property"));
        return new AttackLayer(conditions, priority, property);
    }

    public String getStyle() {
        return property.style();
    }

    public String getElement() {
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
        if (conditions == null) {
            return false;
        }
        for (Condition con : conditions) {
            if (!con.isValid()) {
                return false;
            }
        }
        return property.isValid();
    }
}
