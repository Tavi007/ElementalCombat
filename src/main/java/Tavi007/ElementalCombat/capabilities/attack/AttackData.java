package Tavi007.ElementalCombat.capabilities.attack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.util.ElementalCombatNBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.util.INBTSerializable;

public class AttackData implements INBTSerializable<CompoundTag> {

    private LinkedHashMap<ResourceLocation, AttackLayer> attackLayers = new LinkedHashMap<>();
    private boolean isInitialized = false;

    // for itemstack
    private boolean areEnchantmentChangesApplied = false;

    public AttackData() {
    }

    public void set(AttackData data) {
        attackLayers = data.getLayers();
    }

    public String getElement() {
        List<ResourceLocation> keys = new ArrayList<ResourceLocation>(attackLayers.keySet());
        Collections.reverse(keys);
        for (ResourceLocation rl : keys) {
            AttackLayer layer = attackLayers.get(rl);
            String element = layer.getElement();
            if (!element.equals(ServerConfig.getDefaultElement())) {
                return element;
            }
        }
        return ServerConfig.getDefaultElement();
    }

    public String getStyle() {
        List<ResourceLocation> keys = new ArrayList<ResourceLocation>(attackLayers.keySet());
        Collections.reverse(keys);
        for (ResourceLocation rl : keys) {
            AttackLayer layer = attackLayers.get(rl);
            String style = layer.getStyle();
            if (!style.equals(ServerConfig.getDefaultStyle())) {
                return style;
            }
        }
        return ServerConfig.getDefaultStyle();
    }

    public AttackLayer toLayer() {
        return new AttackLayer(getStyle(), getElement());
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

    public boolean isDefault() {
        return getElement().equals(ServerConfig.getDefaultElement())
            && getStyle().equals(ServerConfig.getDefaultStyle());
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
        AttackLayer base = BasePropertiesAPI.getAttackData(stack);
        attackLayers.put(new ResourceLocation("base"), base);
        List<MobEffectInstance> potionEffects = PotionUtils.getMobEffects(stack);
        potionEffects.forEach(effect -> {
            attackLayers.put(new ResourceLocation("potion_" + effect.getDescriptionId()), BasePropertiesAPI.getAttackLayer(effect));
        });
        isInitialized = true;
    }

    public void initialize(LivingEntity entity) {
        AttackLayer base = BasePropertiesAPI.getAttackData(entity);
        attackLayers.put(new ResourceLocation("base"), base);
        isInitialized = true;
    }

    public void initialize(Projectile entity) {
        AttackLayer base = BasePropertiesAPI.getAttackData(entity);
        attackLayers.put(new ResourceLocation("base"), base);
        isInitialized = true;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof AttackData) {
            AttackData other = (AttackData) object;
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
        StringBuilder builder = new StringBuilder();
        builder.append("isInitialized: " + isInitialized + "\n");
        builder.append("areEnchantmentChangesApplied: " + areEnchantmentChangesApplied + "\n");
        builder.append("as layer: " + toLayer().toString() + "\n");
        builder.append("layers: \n");
        attackLayers.forEach((rl, layer) -> {
            builder.append(rl.toString() + ":" + layer.toString() + "\n");
        });
        return builder.toString();
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public boolean areEnchantmentChangesApplied() {
        return areEnchantmentChangesApplied;
    }

    public LinkedHashMap<ResourceLocation, AttackLayer> getLayers() {
        return attackLayers;
    }

    @Override
    public CompoundTag serializeNBT() {
        AttackDataNBT nbt = new AttackDataNBT();
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, this);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        AttackData data = ElementalCombatNBTHelper.readAttackDataFromNBT(nbt);
        set(data);
    }
}
