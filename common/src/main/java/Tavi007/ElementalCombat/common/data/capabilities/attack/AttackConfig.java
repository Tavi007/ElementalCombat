package Tavi007.ElementalCombat.common.data.capabilities.attack;

import Tavi007.ElementalCombat.common.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.common.api.DatapackDataAPI;
import Tavi007.ElementalCombat.common.data.datapack.CombatConfig;
import Tavi007.ElementalCombat.common.data.datapack.MobCombatConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Queue;

//TODO: change to a tree like structure. Because then an entity can have the full attack config from the held item.
// For now keep it like this for Condition testing.
public record AttackConfig(AttackTree tree) {
    private static final ResourceLocation BASE = new ResourceLocation("base");
    private static final ResourceLocation ENCHANTMENT = new ResourceLocation("enchantment");

    public AttackConfig() {
        this(new AttackTree());
    }

    public static AttackConfig read(FriendlyByteBuf buf) {
        AttackTree layers = AttackTree.read(buf);
        return new AttackConfig(layers);
    }

    public static void write(FriendlyByteBuf buf, AttackConfig attack) {
        AttackTree.write(buf, attack.tree);
    }

    public static CompoundTag writeTag(AttackConfig attack) {
        CompoundTag tag = new CompoundTag();
        tag.put("tree", AttackTree.writeTag(attack.tree));
        return tag;
    }

    public static AttackConfig readTag(CompoundTag tag) {
        CompoundTag treeTag = tag.getCompound("tree");
        AttackTree tree = AttackTree.readTag(treeTag);
        return new AttackConfig(tree);
    }

    public void initValues(Entity entity) {
        if (!has(BASE)) {
            if (entity instanceof LivingEntity living) {
                MobCombatConfig baseConfig = DatapackDataAPI.getMobElementalCombatMobData(living);
                put(baseConfig.attack());
            } else if (entity instanceof Projectile projectile) {
                AttackConfig baseConfig = DatapackDataAPI.getAttackConfig(projectile);
                put(baseConfig);
            }
        }
    }

    public void initValues(ItemStack stack) {
        if (!has(BASE)) {
            CombatConfig baseConfig = DatapackDataAPI.getItemElementalCombatConfig(stack);
            put(baseConfig.attack());
        }
        if (!has(ENCHANTMENT)) {
            //TODO: properly init enchantments
            AttackConfig enchantmentConfig = BasePropertiesAPI.getAttackConfig(EnchantmentHelper.getEnchantments(stack));
            if (enchantmentConfig != null) {
                put(enchantmentConfig);
            }
        }
    }

    public boolean has(ResourceLocation location) {
        return tree.has(location);
    }

    public AttackBranch get(ResourceLocation location) {
        return tree.get(location);
    }

    // traverse the tree until end of queue or a leaf (AttackProperty) is encountered
    public Optional<AttackBranch> get(Queue<ResourceLocation> locations) {
        return tree.get(locations);
    }

    public AttackConfig put(ResourceLocation location, AttackBranch branch) {
        tree.put(location, branch);
        return this;
    }

    public AttackConfig put(Queue<ResourceLocation> locations, AttackBranch branch) {
        tree.put(locations, branch);
        return this;
    }

    public AttackConfig put(AttackConfig other) {
        this.tree.put(other.tree);
        return this;
    }

    public AttackBranch remove(ResourceLocation location) {
        return tree.remove(location);
    }

    public AttackProperty getSummedProperty(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        return tree.getSummedProperty(entity, stack, level);
    }

    public boolean isValid() {
        return tree != null && tree.isValid();
    }
}
