package Tavi007.ElementalCombat.common.potions;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.DefenseAPI;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseMap;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseProperty;
import Tavi007.ElementalCombat.server.ServerConfigAccessors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ElementalResistanceEffect extends MobEffect {
    List<String> buffs;
    List<String> debuffs;

    public ElementalResistanceEffect(MobEffectCategory category, int color, List<String> buffs, List<String> debuffs) {
        super(category, color);
        this.buffs = buffs;
        this.debuffs = debuffs;
    }

    public ElementalResistanceEffect(MobEffectCategory category, int color, String buff, String debuff) {
        this(category, color, Collections.singletonList(buff), Collections.singletonList(debuff));
    }

    private HashMap<String, Integer> getResistanceMap(int level) {
        HashMap<String, Integer> map = new HashMap<>();
        buffs.forEach(buff -> map.put(buff, level * ServerConfigAccessors.getPotionScaling()));
        debuffs.forEach(debuff -> map.put(debuff, -level * ServerConfigAccessors.getPotionScaling() / 2));
        return map;
    }

    public DefenseMap getDefenseMap(MobEffectInstance instance) {
        DefenseMap map = new DefenseMap();
        map.add(getResistanceMap(instance.getAmplifier() + 1));
        return map;
    }

    public void applyEffect(LivingEntity target, MobEffectInstance instance) {
        DefenseMap element = getDefenseMap(instance);
        DefenseLayer layer = new DefenseLayer(new DefenseProperty(new DefenseMap(), element));
        DefenseAPI.put(target, layer, getResourceLocation());
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.MOD_ID, this.getDescriptionId());
    }

    public void removeEffect(LivingEntity target) {
        DefenseAPI.remove(target, getResourceLocation());
    }
}
