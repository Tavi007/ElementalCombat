package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class CapabilityAccessor {

    public static AttackData get(LivingEntity entity) {
        if (entity == null) {
            return new AttackData();
        }
        AttackData attackData = entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
        if (!attackData.isInitialized()) {
            attackData.initialize(entity);
        }
        return attackData;
    }

    public static AttackData get(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return new AttackData();
        } else {
            AttackData attackData = stack.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
            if (!attackData.isInitialized()) {
                attackData.initialize(stack);
            }
            if (!attackData.areEnchantmentChangesApplied()) {
                attackData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
            }
            return attackData;
        }
    }


    public static AttackData get(Projectile projectileEntity) {
        if (projectileEntity == null) {
            return new AttackData();
        }
        AttackData attackData = projectileEntity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
        if (!attackData.isInitialized()) {
            attackData.initialize(projectileEntity);
        }
        return attackData;
    }


    public static void updateItemLayer(LivingEntity entity) {
        if (entity == null) {
            return;
        }
        AttackData attackDataItem = CapabilityAccessor.get(entity.getMainHandItem());
        AttackDataAPI.putLayer(entity, attackDataItem.toLayer(), new ResourceLocation("item"));
    }

    public static AttackData get(DamageSource damageSource) {
        AttackData data = new AttackData();
        if (damageSource == null) {
            return data;
        }
        Entity immediateSource = damageSource.getDirectEntity();
        if (immediateSource != null) {
            if (immediateSource instanceof LivingEntity) {
                data.putLayer(new ResourceLocation("direct_entity"), CapabilityAccessor.get((LivingEntity) immediateSource).toLayer());
            } else if (immediateSource instanceof Projectile) {
                data.putLayer(new ResourceLocation("direct_entity"), CapabilityAccessor.get((Projectile) immediateSource).toLayer());
            }
        }
        data.putLayer(new ResourceLocation("base"), BasePropertiesAPI.getAttackData(damageSource));
        return data;

    }


    public static DefenseData get(LivingEntity entity) {
        if (entity == null) {
            return new DefenseData();
        }
        DefenseData defenseData = entity.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
        if (!defenseData.isInitialized()) {
            defenseData.initialize(entity);
        }
        return defenseData;
    }

    public static void updateItemLayer(LivingEntity entity) {

    }

    public static DefenseData get(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return new DefenseData();
        } else {
            DefenseData defenseData = stack.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
            if (!defenseData.isInitialized()) {
                defenseData.initialize(stack);
            }
            if (!defenseData.areEnchantmentChangesApplied()) {
                defenseData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
            }
            return defenseData;
        }
    }
}
