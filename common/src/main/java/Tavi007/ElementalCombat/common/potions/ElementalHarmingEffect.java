package Tavi007.ElementalCombat.common.potions;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class ElementalHarmingEffect extends MobEffect {

    String element;

    public ElementalHarmingEffect(String element, int color) {
        super(MobEffectCategory.HARMFUL, color);
        this.element = element;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int level) {
        ResourceLocation rl = new ResourceLocation(Constants.MOD_ID, element + "_magic");
        ResourceKey<DamageType> resourceKey = ResourceKey.create(Registries.DAMAGE_TYPE, rl);
        Reference<DamageType> damageType = livingEntity.level()
                .registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(resourceKey);
        livingEntity.hurt(new DamageSource(damageType), (float) (6 << level));
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity potionEntity, @Nullable Entity potionEntityOwner, LivingEntity target, int level, double distance) {
        int damage = (int) (distance * (double) (6 << level) + 0.5D);
        ResourceLocation rl = new ResourceLocation(Constants.MOD_ID, element + "_magic");
        ResourceKey<DamageType> resourceKey = ResourceKey.create(Registries.DAMAGE_TYPE, rl);
        Reference<DamageType> damageType = potionEntity.level()
                .registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(resourceKey);
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
