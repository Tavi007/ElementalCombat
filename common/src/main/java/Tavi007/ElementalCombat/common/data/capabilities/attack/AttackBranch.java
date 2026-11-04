package Tavi007.ElementalCombat.common.data.capabilities.attack;

import Tavi007.ElementalCombat.common.api.DatapackDataAPI;
import com.mojang.datafixers.util.Either;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Queue;

public record AttackBranch(Either<AttackTree, AttackProperty> value, int priority) {

    public AttackBranch(AttackTree tree, int priority) {
        this(Either.left(tree), priority);
    }

    public AttackBranch(AttackProperty property, int priority) {
        this(Either.right(property), priority);
    }

    public AttackBranch(int priority) {
        this(DatapackDataAPI.getDefaultAttack(), priority);
    }

    public AttackBranch(AttackBranch branch) {
        this(branch.value, branch.priority);
    }


    public static void write(FriendlyByteBuf buf, AttackBranch branch) {
        Optional<AttackTree> tree = branch.value.left();
        buf.writeBoolean(tree.isPresent());
        tree.ifPresent(attackTree -> AttackTree.write(buf, attackTree));
        Optional<AttackProperty> property = branch.value.right();
        buf.writeBoolean(property.isPresent());
        property.ifPresent(attackProperty -> AttackProperty.write(buf, attackProperty));
        buf.writeInt(branch.priority);
    }

    public static AttackBranch read(FriendlyByteBuf buf) {
        Either<AttackTree, AttackProperty> value = null;
        boolean hasLeft = buf.readBoolean();
        if (hasLeft) {
            value = Either.left(AttackTree.read(buf));
        }
        boolean hasRight = buf.readBoolean();
        if (hasRight) {
            value = Either.right(AttackProperty.read(buf));
        }
        int priority = buf.readInt();
        return new AttackBranch(value, priority);
    }

    public static CompoundTag writeTag(AttackBranch branch) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("priority", branch.priority);

        Optional<AttackTree> tree = branch.value.left();
        tree.ifPresent(attackTree -> tag.put("tree", AttackTree.writeTag(attackTree)));

        Optional<AttackProperty> property = branch.value.right();
        property.ifPresent(attackProperty -> tag.put("property", AttackProperty.writeTag(attackProperty)));
        return tag;
    }

    public static AttackBranch readTag(CompoundTag tag) {
        int priority = tag.getInt("priority");
        if (tag.contains("tree")) {
            return new AttackBranch(AttackTree.readTag((CompoundTag) tag.get("tree")), priority);
        }
        if (tag.contains("property")) {
            return new AttackBranch(AttackProperty.readTag((CompoundTag) tag.get("property")), priority);
        }
        return new AttackBranch(priority);
    }


    public AttackProperty getSummedProperty(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        if (value.left().isPresent()) {
            return value.left().get().getSummedProperty(entity, stack, level);
        }
        if (value.right().isPresent()) {
            return value.right().get();
        }
        return DatapackDataAPI.getDefaultAttack();
    }

    public Optional<AttackBranch> get(Queue<ResourceLocation> locations) {
        if (locations.isEmpty()) {
            return Optional.of(this);
        }
        if (value.left().isPresent()) {
            return value.left().get().get(locations);
        }
        return Optional.empty();
    }

    public AttackBranch put(Queue<ResourceLocation> locations, AttackBranch branch) {
        if (locations.isEmpty()) {
            this.value.;
        }

        return this;
    }

    public boolean isValid() {
        return false;
    }
}
