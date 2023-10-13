package Tavi007.ElementalCombat.potions;

import javax.annotation.Nullable;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
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
        Reference<DamageType> damageType = livingEntity.level()
            .registryAccess()
            .registryOrThrow(Registries.DAMAGE_TYPE)
            .getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ElementalCombat.MOD_ID, element + "_magic")));
        livingEntity.hurt(new DamageSource(damageType), (float) (6 << level));
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity potionEntity, @Nullable Entity potionEntityOwner, LivingEntity target, int level, double distance) {
        int damage = (int) (distance * (double) (6 << level) + 0.5D);
        Reference<DamageType> damageType = potionEntity.level()
            .registryAccess()
            .registryOrThrow(Registries.DAMAGE_TYPE)
            .getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ElementalCombat.MOD_ID, element + "_magic")));
        target.hurt(new DamageSource(damageType), (float) damage);
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    public AttackLayer getAttackLayer() {
        return new AttackLayer("magic", element);
    }
}
