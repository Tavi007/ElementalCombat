package Tavi007.ElementalCombat.common.data.capabilities.attack;

import Tavi007.ElementalCombat.common.api.DatapackDataAPI;
import Tavi007.ElementalCombat.common.data.capabilities.Condition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public record AttackTree(
        Set<Condition> conditions,
        Map<ResourceLocation, AttackBranch> branches
) {

    public AttackTree() {
        this(new HashSet<>(), new HashMap<>());
    }

    public AttackTree(AttackTree other) {
        this(other.conditions(), other.branches());
    }

    public static void write(FriendlyByteBuf buf, AttackTree tree) {
        buf.writeCollection(tree.conditions, Condition::write);
        buf.writeMap(tree.branches, FriendlyByteBuf::writeResourceLocation, AttackBranch::write);
    }

    public static AttackTree read(FriendlyByteBuf buf) {
        Set<Condition> conditions = buf.readCollection(HashSet::new, Condition::read);
        Map<ResourceLocation, AttackBranch> branches = buf.readMap(FriendlyByteBuf::readResourceLocation, AttackBranch::read);
        return new AttackTree(conditions, branches);
    }

    public static CompoundTag writeTag(AttackTree tree) {
        CompoundTag tag = new CompoundTag();

        ListTag conditionTag = new ListTag();
        tree.conditions.forEach((condition -> {
            conditionTag.add(Condition.writeTag(condition));
        }));
        tag.put("conditions", conditionTag);

        CompoundTag branches = new CompoundTag();
        tree.branches.forEach((rl, branch) -> {
            branches.put(rl.toString(), AttackBranch.writeTag(branch));
        });
        tag.put("branches", branches);

        return tag;
    }


    public static AttackTree readTag(CompoundTag tag) {
        Set<Condition> conditions = new HashSet<>();
        tag.getList("conditions", ListTag.TAG_COMPOUND)
                .forEach(conditionTag -> {
                    conditions.add(Condition.readTag((CompoundTag) conditionTag));
                });

        Map<ResourceLocation, AttackBranch> branches = new HashMap<>();
        CompoundTag branchTag = tag.getCompound("branches");
        branchTag.getAllKeys()
                .forEach(key -> {
                    ResourceLocation rl = new ResourceLocation(key);
                    AttackBranch branch = AttackBranch.readTag((CompoundTag) branchTag.get(key));
                    branches.put(rl, branch);
                });
        return new AttackTree(conditions, branches);
    }

    public AttackProperty getSummedProperty(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        String style = DatapackDataAPI.getDefaultStyle();
        String element = DatapackDataAPI.getDefaultElement();
        if (isActive(entity, stack, level)) {
            for (AttackBranch branch : getSortedBranches()) {
                AttackProperty branchProperty = branch.getSummedProperty(entity, stack, level);
                if (style.equals(DatapackDataAPI.getDefaultStyle())) {
                    style = branchProperty.style();
                }
                if (element.equals(DatapackDataAPI.getDefaultElement())) {
                    element = branchProperty.element();
                }
                if (!style.equals(DatapackDataAPI.getDefaultStyle()) &&
                        !element.equals(DatapackDataAPI.getDefaultElement())) {
                    break;
                }
            }
        }
        return new AttackProperty(style, element);
    }

    public boolean isActive(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        for (Condition condition : conditions) {
            if (condition.isInactive(entity, stack, level)) {
                return false;
            }
        }
        return true;
    }

    private List<AttackBranch> getSortedBranches() {
        return branches.values()
                .stream()
                .sorted(Comparator.comparingInt(AttackBranch::priority))
                .toList();
    }


    public boolean has(ResourceLocation location) {
        return branches.containsKey(location);
    }

    public AttackBranch get(ResourceLocation location) {
        return branches.get(location);
    }

    // traverse the tree until end of queue or a leaf (AttackProperty) is encountered
    public Optional<AttackBranch> get(Queue<ResourceLocation> locations) {
        ResourceLocation location = locations.poll();
        if (location == null) {
            return Optional.empty();
        }
        AttackBranch branch = get(location);
        if (branch == null) {
            return Optional.empty();
        }
        return branch.get(locations);
    }

    public AttackTree put(ResourceLocation location, AttackBranch branch) {
        branches.put(location, branch);
        return this;
    }

    public AttackTree put(Queue<ResourceLocation> locations, AttackBranch branch) {
        ResourceLocation location = locations.poll();
        if(locations.isEmpty()) {
            branches.put(location, branch);
        } else if (has(location)) {
            get(location).put(locations, branch);
        }
        return this;
    }

    // TODO: maybe remove once it not used anymore
    public AttackTree put(AttackTree other) {
        other.branches.forEach(this::put);
        return this;
    }

    public AttackBranch remove(ResourceLocation location) {
        return branches.remove(location);
    }

    public Optional<AttackBranch> remove(Queue<ResourceLocation> locations) {
        ResourceLocation location = locations.poll();
        if(locations.isEmpty()) {
            AttackBranch removedBranch = branches.remove(location);
            return Optional.of(removedBranch);
        } else if (has(location)) {
            return get(location).remove(locations);
        }
        return Optional.empty();
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
        for (AttackBranch branch : branches.values()) {
            if (!branch.isValid()) {
                return false;
            }
        }
        return true;
    }
}
