package Tavi007.ElementalCombat.potions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.config.ServerConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

public class ElementalResistanceEffect extends Effect {

    List<String> buffs;
    List<String> debuffs;

    public ElementalResistanceEffect(EffectType category, int color, List<String> buffs, List<String> debuffs) {
        super(category, color);
        this.buffs = buffs;
        this.debuffs = debuffs;
    }

    public ElementalResistanceEffect(EffectType category, int color, String buff, String debuff) {
        this(category, color, Arrays.asList(buff), Arrays.asList(debuff));
    }

    private HashMap<String, Integer> getResistanceMap(int level) {
        HashMap<String, Integer> map = new HashMap<>();
        buffs.forEach(buff -> map.put(buff, level * ServerConfig.getPotionScaling()));
        debuffs.forEach(debuff -> map.put(debuff, -level * ServerConfig.getPotionScaling() / 2));
        return map;
    }

    public DefenseLayer getDefenseLayer(EffectInstance instance) {
        DefenseLayer layer = new DefenseLayer();
        layer.addElement(getResistanceMap(instance.getAmplifier() + 1));
        return layer;
    }

    public void applyEffect(LivingEntity target, EffectInstance instance) {
        DefenseDataAPI.putLayer(target, getDefenseLayer(instance), this.getRegistryName());
    }

    public ResourceLocation getResourceLocation() {
        return this.getRegistryName();
    }

    public void removeEffect(LivingEntity target) {
        DefenseDataAPI.deleteLayer(target, this.getRegistryName());
    }
}
