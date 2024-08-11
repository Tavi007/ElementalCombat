package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
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
}
