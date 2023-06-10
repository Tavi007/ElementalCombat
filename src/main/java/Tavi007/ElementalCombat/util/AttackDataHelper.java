package Tavi007.ElementalCombat.util;

import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackDataCapability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class AttackDataHelper {

    public static AttackData get(LivingEntity entity) {
        if(entity == null) {
            return new AttackData();
        }
        AttackData attackData = (AttackData) entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
        if (!attackData.isInitialized()) {
            attackData.initialize(entity);
        }
        return attackData;
    }

    public static void updateItemLayer(LivingEntity entity) {
        if(entity == null) {
            return;
        }
        AttackData attackDataItem = get(entity.getMainHandItem());
        AttackDataAPI.putLayer(entity, attackDataItem.toLayer(), new ResourceLocation("item"));
    }

    public static AttackData get(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return new AttackData();
        } else {
            AttackData attackData = (AttackData) stack.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
            if (!attackData.isInitialized()) {
                attackData.initialize(stack);
            }
            if (!attackData.areEnchantmentChangesApplied()) {
                attackData.applyEnchantmentChanges(EnchantmentHelper.getEnchantments(stack));
            }
            return attackData;
        }
    }

    public static AttackData get(ProjectileEntity projectileEntity) {
        if(projectileEntity == null) {
            return new AttackData();
        }
        AttackData attackData = (AttackData) projectileEntity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
        if (!attackData.isInitialized()) {
            attackData.initialize(projectileEntity);
        }
        return attackData;
    }

    public static AttackData get(DamageSource damageSource) {
        AttackData data = new AttackData();
        if(damageSource == null) {
            return data;
        }

        Entity immediateSource = damageSource.getDirectEntity();
        if(immediateSource != null) {
            if (immediateSource instanceof LivingEntity) {
                data.putLayer(new ResourceLocation("direct_entity"), get((LivingEntity) immediateSource).toLayer());
            } else if (immediateSource instanceof ProjectileEntity) {
                data.putLayer(new ResourceLocation("direct_entity"), get((ProjectileEntity) immediateSource).toLayer());
            }
        }

        // base values have higher priority, so they must be added last.
        data.putLayer(new ResourceLocation("base"), BasePropertiesAPI.getAttackData(damageSource));
        return data;
    }

}
