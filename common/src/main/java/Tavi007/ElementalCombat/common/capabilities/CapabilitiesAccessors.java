package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import Tavi007.ElementalCombat.common.data.datapack.CombatPropertiesManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class CapabilitiesAccessors {

    private static Function<LivingEntity, ImmersionData> mobImmersionDataAccessor;
    private static Function<LivingEntity, AttackConfig> mobAttackConfigAccessor;
    private static Function<LivingEntity, DefenseConfig> mobDefenseConfigAccessor;
    private static Function<ItemStack, AttackConfig> itemAttackConfigAccessor;
    private static Function<ItemStack, DefenseConfig> itemDefenseConfigAccessor;
    private static Function<Projectile, AttackConfig> projectileAttackConfigAccessor;


    public static void setMobImmersionDataAccessor(Function<LivingEntity, ImmersionData> accessor) {
        mobImmersionDataAccessor = accessor;
    }

    public static ImmersionData getImmersionData(LivingEntity entity) {
        return mobImmersionDataAccessor.apply(entity);
    }

    public static void setMobAttackConfigAccessor(Function<LivingEntity, AttackConfig> accessor) {
        mobAttackConfigAccessor = accessor;
    }

    public static AttackConfig getAttackConfig(Entity entity) {
        if (entity instanceof LivingEntity) {
            return CapabilitiesAccessors.getAttackConfig((LivingEntity) entity);
        } else if (entity instanceof Projectile) {
            return CapabilitiesAccessors.getAttackConfig((Projectile) entity);
        }
        throw new IllegalArgumentException();
    }

    public static AttackConfig getAttackConfig(LivingEntity entity) {
        return mobAttackConfigAccessor.apply(entity);
    }

    public static void setMobDefenseConfigAccessor(Function<LivingEntity, DefenseConfig> accessor) {
        mobDefenseConfigAccessor = accessor;
    }

    public static DefenseConfig getDefenseConfig(LivingEntity entity) {
        return mobDefenseConfigAccessor.apply(entity);
    }

    public static void setItemAttackConfigAccessor(Function<ItemStack, AttackConfig> accessor) {
        itemAttackConfigAccessor = accessor;
    }

    public static AttackConfig getAttackConfig(ItemStack stack) {
        return itemAttackConfigAccessor.apply(stack);
    }

    public static void setItemDefenseConfigAccessor(Function<ItemStack, DefenseConfig> accessor) {
        itemDefenseConfigAccessor = accessor;
    }

    public static DefenseConfig getDefenseConfig(ItemStack stack) {
        return itemDefenseConfigAccessor.apply(stack);
    }


    public static void setProjectileAttackConfigAccessor(Function<Projectile, AttackConfig> accessor) {
        projectileAttackConfigAccessor = accessor;
    }

    public static AttackConfig getAttackConfig(Projectile projectile) {
        return projectileAttackConfigAccessor.apply(projectile);
    }

    public static AttackConfig getAttackConfig(DamageSource damageSource) {
        AttackConfig data = new AttackConfig();
        if (damageSource == null) {
            return data;
        }

        damageSource.typeHolder().unwrapKey().ifPresent(resourceKey -> {
            ResourceLocation resourceLocation = resourceKey.location();
            AttackProperty defaultProperty = CombatPropertiesManager.getDamageTypeDefaultProperty(resourceLocation);
            AttackLayer layer = new AttackLayer(100, defaultProperty);
            data.put(new ResourceLocation("base"), layer);
        });

        Entity immediateSource = damageSource.getDirectEntity();
        if (immediateSource != null) {
            AttackConfig sourceConfig = null;
            if (immediateSource instanceof LivingEntity) {
                sourceConfig = getAttackConfig((LivingEntity) immediateSource);
            } else if (immediateSource instanceof Projectile) {
                sourceConfig = getAttackConfig((Projectile) immediateSource);
            }

            if (sourceConfig != null) {
                AttackProperty property = sourceConfig.getSummedProperty(immediateSource, null, immediateSource.level());
                AttackLayer layer = new AttackLayer(99, property);
                data.put(new ResourceLocation("immediate_source"), layer);
            }
        }

        return data;
    }

}
