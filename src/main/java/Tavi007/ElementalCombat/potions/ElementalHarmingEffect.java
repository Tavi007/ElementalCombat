package Tavi007.ElementalCombat.potions;

import javax.annotation.Nullable;

import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class ElementalHarmingEffect extends MobEffect {

    String element;

    public ElementalHarmingEffect(String element, int color) {
        super(MobEffectCategory.HARMFUL, color);
        this.element = element;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int level) {
        livingEntity.hurt(new DamageSource("magic." + element), (float) (6 << level));
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity potionEntity, @Nullable Entity potionEntityOwner, LivingEntity target, int level, double distance) {
        int damage = (int) (distance * (double) (6 << level) + 0.5D);
        target.hurt(new DamageSource("magic." + element), (float) damage);
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    public AttackLayer getAttackLayer() {
        return new AttackLayer("magic", element);
    }
}
