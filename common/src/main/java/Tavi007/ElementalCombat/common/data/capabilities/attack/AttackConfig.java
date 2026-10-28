package Tavi007.ElementalCombat.common.data.capabilities.attack;

import Tavi007.ElementalCombat.common.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.common.api.DatapackDataAPI;
import Tavi007.ElementalCombat.common.data.datapack.CombatConfig;
import Tavi007.ElementalCombat.common.data.datapack.CombatPropertiesManager;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: change to a tree like structure. Because then an entity can have the full attack config from the held item.
// For now keep it like this for Condition testing.
public record AttackConfig(Map<ResourceLocation, AttackLayer> layers) {
    private static final ResourceLocation BASE = new ResourceLocation("base");
    private static final ResourceLocation ENCHANTMENT = new ResourceLocation("enchantment");

    public AttackConfig() {
        this(new HashMap<>());
    }

    public static AttackConfig read(FriendlyByteBuf buf) {
        Map<ResourceLocation, AttackLayer> layers = buf.readMap(FriendlyByteBuf::readResourceLocation, AttackLayer::read);
        return new AttackConfig(layers);
    }

    public static void write(FriendlyByteBuf buf, AttackConfig attack) {
        buf.writeMap(attack.layers, FriendlyByteBuf::writeResourceLocation, AttackLayer::write);
    }

    public static CompoundTag writeTag(AttackConfig attack) {
        CompoundTag tag = new CompoundTag();
        attack.layers.forEach((rl, layer) -> tag.put(rl.toString(), AttackLayer.writeTag(layer)));
        return tag;
    }

    public static AttackConfig readTag(CompoundTag tag) {
        Map<ResourceLocation, AttackLayer> layers = new HashMap<>();
        for (String key : tag.getAllKeys()) {
            layers.put(new ResourceLocation(key), AttackLayer.readTag((CompoundTag) tag.get(key)));
        }
        return new AttackConfig(layers);
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
        }
    }

    public boolean has(ResourceLocation location) {
        return layers.containsKey(location);
    }

    public AttackLayer get(ResourceLocation location) {
        return layers.get(location);
    }

    public AttackConfig put(ResourceLocation location, AttackLayer layer) {
        layers.put(location, layer);
        return this;
    }

    public AttackConfig put(AttackConfig other) {
        other.layers.forEach(this::put);
        return this;
    }

    public AttackLayer remove(ResourceLocation location) {
        return layers.remove(location);
    }

    public AttackProperty getSummedProperty(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        // this could be slow, but I don't think we will ever have enough entries for this to matter.
        List<AttackLayer> layers = this.layers.values().stream().sorted(AttackLayer.COMPARATOR).toList();
        String style = CombatPropertiesManager.getDefaultStyle();
        String element = CombatPropertiesManager.getDefaultElement();
        for (AttackLayer layer : layers) {
            if (layer.isActive(entity, stack, level)) {
                if (style.equals(CombatPropertiesManager.getDefaultStyle())) {
                    style = layer.getStyle();
                }
                if (element.equals(CombatPropertiesManager.getDefaultElement())) {
                    element = layer.getElement();
                }
                if (!style.equals(CombatPropertiesManager.getDefaultStyle())
                        && !element.equals(CombatPropertiesManager.getDefaultElement())) {
                    break;
                }
            }
        }
        return new AttackProperty(style, element);
    }

    public boolean isValid() {
        return layers != null
                && layers.values().stream().allMatch(obj -> obj != null && obj.isValid());
    }
}
