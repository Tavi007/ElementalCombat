package Tavi007.ElementalCombat.common.potions;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.DefenseDataAPI;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.server.ServerConfig;
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
        buffs.forEach(buff -> map.put(buff, level * ServerConfig.getPotionScaling()));
        debuffs.forEach(debuff -> map.put(debuff, -level * ServerConfig.getPotionScaling() / 2));
        return map;
    }

    public DefenseLayer getDefenseLayer(MobEffectInstance instance) {
        DefenseLayer layer = new DefenseLayer();
        layer.addElements(getResistanceMap(instance.getAmplifier() + 1));
        return layer;
    }

    public void applyEffect(LivingEntity target, MobEffectInstance instance) {
        DefenseDataAPI.putLayer(target, getDefenseLayer(instance), getResourceLocation());
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.MOD_ID, this.getDescriptionId());
    }

    public void removeEffect(LivingEntity target) {
        DefenseDataAPI.deleteLayer(target, getResourceLocation());
    }
}
