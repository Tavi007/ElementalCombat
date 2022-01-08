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
        AttackData attackData = (AttackData) entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
        if (!attackData.isInitialized()) {
            attackData.initialize(entity);
        }
        return attackData;
    }

    public static void updateItemLayer(LivingEntity entity) {
        AttackData attackDataItem = get(entity.getHeldItemMainhand());
        AttackDataAPI.putLayer(entity, attackDataItem.toLayer(), new ResourceLocation("item"));
    }

    public static AttackData get(ItemStack stack) {
        if (stack.isEmpty()) {
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
        AttackData attackData = (AttackData) projectileEntity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
        if (!attackData.isInitialized()) {
            attackData.initialize(projectileEntity);
        }
        return attackData;
    }

    public static AttackData get(DamageSource damageSource) {
        Entity immediateSource = damageSource.getImmediateSource();

        // Get combat data from source
        if (immediateSource instanceof LivingEntity) {
            return get((LivingEntity) immediateSource);
        } else if (immediateSource instanceof ProjectileEntity) {
            return get((ProjectileEntity) immediateSource);
        } else {
            AttackData data = new AttackData();
            data.putLayer(new ResourceLocation("base"), BasePropertiesAPI.getAttackData(damageSource));
            return data;
        }
    }

}
