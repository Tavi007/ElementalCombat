package Tavi007.ElementalCombat.common.data.capabilities;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.Condition;
import Tavi007.ElementalCombat.common.capabilities.AttackDataNBT;
import Tavi007.ElementalCombat.common.data.DatapackDataAccessor;
import Tavi007.ElementalCombat.common.util.ElementalCombatNBTHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class AttackData {

    private boolean isInitialized = false;
    // for itemstack
    private boolean areEnchantmentChangesApplied = false;
    private LinkedHashMap<ResourceLocation, AttackLayer> attackLayers = new LinkedHashMap<>();

    public AttackData() {
    }

    public void set(AttackData data) {
        attackLayers = data.getLayers();
    }

    public LinkedHashMap<ResourceLocation, AttackLayer> getLayers() {
        return attackLayers;
    }


    public LinkedHashMap<Condition, List<AttackLayer>> getLayersSortedByCondition() {
        return this.attackLayers.values().stream()
                .collect(Collectors.groupingBy(
                        AttackLayer::getCondition,
                        LinkedHashMap::new, // preserve outer order
                        Collectors.toList() // preserves inner order
                ));
    }

    // like getLayersSortedByCondition, but the List of AttackLayers is compressed into a single AttackLayer
    public LinkedHashMap<Condition, AttackLayer> getLayerSortedByCondition() {
        LinkedHashMap<Condition, AttackLayer> groupedLayers = new LinkedHashMap<Condition, AttackLayer>();
        getLayersSortedByCondition().forEach((condition, layers) -> {
            List<String> elements = layers.stream().map(AttackLayer::getElement).toList();
            String element = getLastNonDefaultValue(elements, DatapackDataAccessor.getDefaultElement());
            List<String> styles = layers.stream().map(AttackLayer::getStyle).toList();
            String style = getLastNonDefaultValue(styles, DatapackDataAccessor.getDefaultStyle());
            groupedLayers.put(condition, new AttackLayer(style, element, null));
        });
        return groupedLayers;
    }

    public String getElement(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        List<AttackLayer> layers = getActiveLayers(entity, stack, level);
        List<String> elements = layers.stream().map(AttackLayer::getElement).toList();
        return getLastNonDefaultValue(elements, DatapackDataAccessor.getDefaultElement());
    }

    public String getStyle(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        List<AttackLayer> layers = getActiveLayers(entity, stack, level);
        List<String> styles = layers.stream().map(AttackLayer::getStyle).toList();
        return getLastNonDefaultValue(styles, DatapackDataAccessor.getDefaultStyle());
    }

    private List<AttackLayer> getActiveLayers(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        LinkedHashMap<Condition, List<AttackLayer>> conditionLayerMap = getLayersSortedByCondition();
        for (Map.Entry<Condition, List<AttackLayer>> entry : conditionLayerMap.entrySet()) {
            Condition condition = entry.getKey();
            if (condition.isActive(entity, stack, level)) {
                return entry.getValue();
            }
        }
        return new ArrayList<>();
    }

    private String getLastNonDefaultValue(List<String> values, String defaultValue) {
        // Collections.reverse(values);
        for (String val : values) {
            if (!val.equals(defaultValue)) {
                return val;
            }
        }
        return defaultValue;
    }

    public AttackLayer toLayer(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        return new AttackLayer(getStyle(entity, stack, level), getElement(entity, stack, level), null);
    }

    public AttackLayer getLayer(ResourceLocation rl) {
        if (attackLayers.containsKey(rl)) {
            return attackLayers.get(rl);
        } else {
            return new AttackLayer();
        }
    }

    public void putLayer(ResourceLocation rl, AttackLayer layer) {
        attackLayers.remove(rl);
        if (!layer.isDefault()) {
            attackLayers.put(rl, layer);
        }
    }

    public void applyEnchantmentChanges(Map<Enchantment, Integer> currentEnchantments) {
        AttackLayer layer = new AttackLayer();
        for (Enchantment ench : currentEnchantments.keySet()) {
            layer = BasePropertiesAPI.getAttackLayer(ench);
            if (!layer.isDefault()) {
                break;
            }
        }
        attackLayers.put(new ResourceLocation("enchantment"), layer);
        areEnchantmentChangesApplied = true;
    }

    public void initialize(ItemStack stack) {
        ResourceLocation itemRl = BuiltInRegistries.ITEM.getKey(stack.getItem());
        putLayer(new ResourceLocation(Constants.MOD_ID, "base"), DatapackDataAccessor.getItemDefaultLayer(itemRl).getAttack());
        List<MobEffectInstance> potionEffects = PotionUtils.getMobEffects(stack);
        potionEffects.forEach(effect -> {
            putLayer(new ResourceLocation("potion_" + effect.getDescriptionId()), BasePropertiesAPI.getAttackLayer(effect));
        });
        isInitialized = true;
    }

    public void initialize(LivingEntity entity) {
        ResourceLocation entityRl = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        AttackLayer base = DatapackDataAccessor.getMobDefaultData(entityRl).getAttack();
        attackLayers.put(new ResourceLocation("base"), base);
        isInitialized = true;
    }

    public void initialize(Projectile entity) {
        ResourceLocation entityRl = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        AttackLayer base = DatapackDataAccessor.getProjectileDefaultLayer(entityRl);
        attackLayers.put(new ResourceLocation("base"), base);
        isInitialized = true;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof AttackData other) {
            Set<ResourceLocation> keysThis = this.attackLayers.keySet();
            Set<ResourceLocation> keysOther = other.attackLayers.keySet();
            if (keysThis.size() == keysOther.size()) {
                Iterator<ResourceLocation> iteratorThis = keysThis.iterator();
                Iterator<ResourceLocation> iteratorOther = keysOther.iterator();
                while (iteratorThis.hasNext()) {
                    ResourceLocation keyThis = iteratorThis.next();
                    ResourceLocation keyOther = iteratorOther.next();
                    if (!keyThis.equals(keyOther)) {
                        return false;
                    }
                    AttackLayer layerThis = this.attackLayers.get(keyThis);
                    AttackLayer layerOther = other.attackLayers.get(keyOther);
                    if (!layerThis.equals(layerOther)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append("isInitialized: ").append(isInitialized).append("\n")
                .append("areEnchantmentChangesApplied:").append(areEnchantmentChangesApplied).append("\n")
                .append("layers: \n");
        attackLayers.forEach((rl, layer) -> {
            builder.append(rl.toString()).append(":").append(layer.toString()).append("\n");
        });
        return builder.toString();
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public boolean areEnchantmentChangesApplied() {
        return areEnchantmentChangesApplied;
    }

    public CompoundTag serializeNBT() {
        AttackDataNBT nbt = new AttackDataNBT();
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, this);
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        AttackData data = ElementalCombatNBTHelper.readAttackDataFromNBT(nbt);
        set(data);
    }
}
