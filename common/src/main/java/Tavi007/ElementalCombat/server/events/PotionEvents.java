package Tavi007.ElementalCombat.server.events;

import Tavi007.ElementalCombat.common.potions.ElementalResistanceEffect;
import Tavi007.ElementalCombat.common.registry.ModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class PotionEvents {


    public static boolean adjustPotionEffect(LivingEntity entity, MobEffectInstance effect) {
        if (effect == null || entity == null) {
            return false;
        }
        if (MobEffects.FIRE_RESISTANCE.equals(effect.getEffect())) {
            entity.addEffect(new MobEffectInstance(ModMobEffects.FIRE_RESISTANCE, effect.getDuration()));
            return true;
        }
        return false;
    }


    public static void onAddPotionEvent(LivingEntity entity, MobEffectInstance effect) {
        if (effect == null || entity == null) {
            return;
        }
        if (effect.getEffect() instanceof ElementalResistanceEffect) {
            if (entity.hasEffect(effect.getEffect())) {
                MobEffectInstance currentEffect = entity.getEffect(effect.getEffect());
                if (currentEffect.getAmplifier() < effect.getAmplifier()) {
                    ((ElementalResistanceEffect) effect.getEffect()).applyEffect(entity, effect);
                }
            } else {
                ((ElementalResistanceEffect) effect.getEffect()).applyEffect(entity, effect);
            }
        }
    }

    public static void onRemovePotionEvent(LivingEntity entity, MobEffectInstance effect) {
        if (effect == null || entity == null) {
            return;
        }
        if (effect.getEffect() instanceof ElementalResistanceEffect) {
            ((ElementalResistanceEffect) effect.getEffect()).removeEffect(entity);
        }
    }
}
