package Tavi007.ElementalCombat.common.data.capabilities.defense;

import Tavi007.ElementalCombat.common.api.DatapackDataAPI;
import Tavi007.ElementalCombat.common.data.datapack.CombatConfig;
import Tavi007.ElementalCombat.common.data.datapack.MobCombatConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public record DefenseConfig(Map<ResourceLocation, DefenseLayer> layers) {

    private static final ResourceLocation BASE = new ResourceLocation("base");
    private static final ResourceLocation ENCHANTMENT = new ResourceLocation("enchantment");

    public DefenseConfig() {
        this(new HashMap<>());
    }

    public static DefenseConfig read(FriendlyByteBuf buf) {
        Map<ResourceLocation, DefenseLayer> layers = buf.readMap(FriendlyByteBuf::readResourceLocation, DefenseLayer::read);
        return new DefenseConfig(layers);
    }

    public static void write(FriendlyByteBuf buf, DefenseConfig defense) {
        buf.writeMap(defense.layers, FriendlyByteBuf::writeResourceLocation, DefenseLayer::write);
    }

    public static CompoundTag writeTag(DefenseConfig defense) {
        CompoundTag tag = new CompoundTag();
        defense.layers.forEach((rl, layer) -> tag.put(rl.toString(), DefenseLayer.writeTag(layer)));
        return tag;
    }

    public static DefenseConfig readTag(CompoundTag tag) {
        Map<ResourceLocation, DefenseLayer> layers = new HashMap<>();
        for (String key : tag.getAllKeys()) {
            layers.put(new ResourceLocation(key), DefenseLayer.readTag((CompoundTag) tag.get(key)));
        }
        return new DefenseConfig(layers);
    }

    public void initValues(Entity entity) {
        if (!has(BASE)) {
            if (entity instanceof LivingEntity living) {
                MobCombatConfig baseConfig = DatapackDataAPI.getMobElementalCombatMobData(living);
                put(baseConfig.defense());
            }
        }
    }

    public void initValues(ItemStack stack) {
        if (!has(BASE)) {
            CombatConfig baseConfig = DatapackDataAPI.getItemElementalCombatConfig(stack);
            put(baseConfig.defense());
        }
        if (!has(ENCHANTMENT)) {
            //TODO: properly init enchantments
            //AttackConfig enchantmentConfig = BasePropertiesAPI.getAttackConfig(EnchantmentHelper.getEnchantments(stack));
        }
    }

    public boolean has(ResourceLocation location) {
        return layers.containsKey(location);
    }

    public DefenseLayer get(ResourceLocation location) {
        return layers.get(location);
    }

    public DefenseConfig put(ResourceLocation location, DefenseLayer layer) {
        layers.put(location, layer);
        return this;
    }

    public DefenseConfig put(DefenseConfig other) {
        other.layers.forEach(this::put);
        return this;
    }

    public DefenseLayer remove(ResourceLocation location) {
        return layers.remove(location);
    }

    public DefenseProperty getSummedProperty(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        DefenseMap style = new DefenseMap();
        DefenseMap element = new DefenseMap();
        for (DefenseLayer layer : layers.values()) {
            if (layer.isActive(entity, stack, level)) {
                style.add(layer.getStyle());
                element.add(layer.getElement());
            }
        }
        return new DefenseProperty(style, element);
    }

    public boolean isValid() {
        return layers != null
                && layers.values().stream().allMatch(obj -> obj != null && obj.isValid());
    }
}
