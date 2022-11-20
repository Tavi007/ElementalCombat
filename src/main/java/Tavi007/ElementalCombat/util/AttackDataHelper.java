package Tavi007.ElementalCombat.util;

import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackDataCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class AttackDataHelper {

    public static AttackData get(LivingEntity entity) {
        AttackData attackData = (AttackData) entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
        if (!attackData.isInitialized()) {
            attackData.initialize(entity);
        }
        return attackData;
    }

    public static void updateItemLayer(LivingEntity entity) {
        AttackData attackDataItem = get(entity.getMainHandItem());
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

    public static AttackData get(Projectile projectileEntity) {
        AttackData attackData = (AttackData) projectileEntity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
        if (!attackData.isInitialized()) {
            attackData.initialize(projectileEntity);
        }
        return attackData;
    }

    public static AttackData get(DamageSource damageSource) {
        Entity immediateSource = damageSource.getDirectEntity();

        // Get combat data from source
        if (immediateSource instanceof LivingEntity) {
            return get((LivingEntity) immediateSource);
        } else if (immediateSource instanceof Projectile) {
            return get((Projectile) immediateSource);
        } else {
            AttackData data = new AttackData();
            data.putLayer(new ResourceLocation("base"), BasePropertiesAPI.getAttackData(damageSource));
            return data;
        }
    }

}
