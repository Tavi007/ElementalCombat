package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.DatapackDataAccessor;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class CapabilitiesAccessors {

    private static Function<LivingEntity, ImmersionData> mobImmersionDataAccessor;
    private static Function<LivingEntity, AttackData> mobAttackDataAccessor;
    private static Function<LivingEntity, DefenseData> mobDefenseDataAccessor;
    private static Function<ItemStack, AttackData> itemAttackDataAccessor;
    private static Function<ItemStack, DefenseData> itemDefenseDataAccessor;
    private static Function<Projectile, AttackData> projectileAttackDataAccessor;


    public static void setMobImmersionDataAccessor(Function<LivingEntity, ImmersionData> accessor) {
        mobImmersionDataAccessor = accessor;
    }

    public static ImmersionData getImmersionData(LivingEntity entity) {
        return mobImmersionDataAccessor.apply(entity);
    }

    public static void setMobAttackDataAccessor(Function<LivingEntity, AttackData> accessor) {
        mobAttackDataAccessor = accessor;
    }

    public static AttackData getAttackData(LivingEntity entity) {
        return mobAttackDataAccessor.apply(entity);
    }

    public static void setMobDefenseDataAccessor(Function<LivingEntity, DefenseData> accessor) {
        mobDefenseDataAccessor = accessor;
    }

    public static DefenseData getDefenseData(LivingEntity entity) {
        return mobDefenseDataAccessor.apply(entity);
    }

    public static void setItemAttackDataAccessor(Function<ItemStack, AttackData> accessor) {
        itemAttackDataAccessor = accessor;
    }

    public static AttackData getAttackData(ItemStack stack) {
        return itemAttackDataAccessor.apply(stack);
    }

    public static void setItemDefenseDataAccessor(Function<ItemStack, DefenseData> accessor) {
        itemDefenseDataAccessor = accessor;
    }

    public static DefenseData getDefenseData(ItemStack stack) {
        return itemDefenseDataAccessor.apply(stack);
    }


    public static void setProjectileAttackDataAccessor(Function<Projectile, AttackData> accessor) {
        projectileAttackDataAccessor = accessor;
    }

    public static AttackData getAttackData(Projectile projectile) {
        return projectileAttackDataAccessor.apply(projectile);
    }

    public static AttackData getAttackData(DamageSource damageSource) {
        AttackData data = new AttackData();
        if (damageSource == null) {
            return data;
        }

        damageSource.typeHolder().unwrapKey().ifPresent(resourceKey -> {
            ResourceLocation resourceLocation = resourceKey.location();
            data.putLayer(new ResourceLocation("base"), DatapackDataAccessor.getDamageTypeDefaultLayer(resourceLocation));
        });

        Entity immediateSource = damageSource.getDirectEntity();
        if (immediateSource != null) {
            if (immediateSource instanceof LivingEntity) {
                data.putLayer(new ResourceLocation("direct_entity"), getAttackData((LivingEntity) immediateSource).toLayer(immediateSource, null, immediateSource.level()));
            } else if (immediateSource instanceof Projectile) {
                data.putLayer(new ResourceLocation("direct_entity"), getAttackData((Projectile) immediateSource).toLayer(immediateSource, null, immediateSource.level()));
            }
        }

        return data;
    }

}
