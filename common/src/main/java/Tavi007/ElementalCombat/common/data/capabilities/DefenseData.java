package Tavi007.ElementalCombat.common.data.capabilities;

import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.capabilities.DefenseDataNBT;
import Tavi007.ElementalCombat.common.util.ElementalCombatNBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DefenseData {

    // for itemstack
    private final boolean areEnchantmentChangesApplied = false;
    private HashMap<ResourceLocation, DefenseLayer> defenseLayers = new HashMap<>();
    private boolean isInitialized = false;

    public DefenseData() {
    }

    public void set(DefenseData data) {
        this.defenseLayers = data.defenseLayers;
        this.isInitialized = data.isInitialized;
    }

    public Map<String, Integer> getStyles() {
        DefenseLayer sum = new DefenseLayer();
        defenseLayers.forEach((rl, layer) -> {
            sum.addStyles(layer.getStyles());
        });
        return sum.getStyles();
    }

    public Map<String, Integer> getElements() {
        DefenseLayer sum = new DefenseLayer();
        defenseLayers.forEach((rl, layer) -> {
            sum.addElements(layer.getElements());
        });
        return sum.getElements();
    }

    public DefenseLayer toLayer() {
        DefenseLayer layer = new DefenseLayer();
        layer.addElements(getElements());
        layer.addStyles(getStyles());
        return layer;
    }

    public DefenseLayer getLayer(ResourceLocation rl) {
        if (defenseLayers.containsKey(rl)) {
            return defenseLayers.get(rl);
        } else {
            return new DefenseLayer();
        }
    }

    public void putLayer(ResourceLocation rl, DefenseLayer layer) {
        if (layer.isEmpty()) {
            defenseLayers.remove(rl);
        } else {
            defenseLayers.put(rl, layer);
        }
    }

    public boolean isEmpty() {
        return getStyles().isEmpty() && getElements().isEmpty();
    }

    //TODO: fix
//    public void applyEnchantmentChanges(Map<Enchantment, Integer> enchantments) {
//        DefenseData data = new DefenseData();
//        enchantments.forEach((ench, level) -> {
//            data.putLayer(new ResourceLocation(ench.getDescriptionId()), BasePropertiesAPI.getDefenseLayer(ench, level));
//        });
//        if (!data.isEmpty()) {
//            defenseLayers.put(new ResourceLocation("enchantment"), data.toLayer());
//        }
//        areEnchantmentChangesApplied = true;
//    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("isInitialized: " + isInitialized + "\n");
        builder.append("areEnchantmentChangesApplied: " + areEnchantmentChangesApplied + "\n");
        builder.append("as layer: " + toLayer().toString() + "\n");
        builder.append("layers: \n");
        defenseLayers.forEach((rl, layer) -> {
            builder.append(rl.toString() + ":" + layer.toString() + "\n");
        });
        return builder.toString();
    }
    
    //TODO: fix
//
//    public void initialize(ItemStack stack) {
//        putLayer(new ResourceLocation(Constants.MOD_ID, "base"), BasePropertiesAPI.getDefenseLayer(stack));
//
//        List<MobEffectInstance> potionEffects = PotionUtils.getMobEffects(stack);
//        potionEffects.forEach(effect -> {
//            putLayer(new ResourceLocation("potion_" + effect.getDescriptionId()), BasePropertiesAPI.getDefenseLayer(effect));
//        });
//        isInitialized = true;
//    }
//
//    public void initialize(LivingEntity entity) {
//        putLayer(new ResourceLocation(Constants.MOD_ID, "base"), BasePropertiesAPI.getDefenseLayer(entity));
//        isInitialized = true;
//    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public HashMap<ResourceLocation, DefenseLayer> getLayers() {
        return defenseLayers;
    }

    public boolean areEnchantmentChangesApplied() {
        return areEnchantmentChangesApplied;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DefenseData other) {
            Set<ResourceLocation> keysThis = this.defenseLayers.keySet();
            Set<ResourceLocation> keysOther = other.defenseLayers.keySet();
            if (keysThis.size() == keysOther.size()) {
                Iterator<ResourceLocation> iteratorThis = keysThis.iterator();
                while (iteratorThis.hasNext()) {
                    ResourceLocation keyThis = iteratorThis.next();
                    DefenseLayer layerThis = this.defenseLayers.get(keyThis);
                    DefenseLayer layerOther = other.defenseLayers.get(keyThis);
                    if (!layerThis.equals(layerOther)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public CompoundTag serializeNBT() {
        DefenseDataNBT nbt = new DefenseDataNBT();
        ElementalCombatNBTHelper.writeDefenseDataToNBT(nbt, this);
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        DefenseData data = ElementalCombatNBTHelper.readDefenseDataFromNBT(nbt);
        set(data);
    }
}
