package Tavi007.ElementalCombat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.util.DamageSource;

@Mixin(ProtectionEnchantment.class)
public class MixinProtectionEnchantment {

    @Inject(at = @At("HEAD"), method = "getDamageProtection(ILnet/minecraft/util/DamageSource;)I", cancellable = true)
    private void getDamageProtection(int level, DamageSource damageSource, CallbackInfoReturnable<Integer> callback) {
        if (damageSource.isBypassInvul()) {
            callback.setReturnValue(0);
        } else if (this.type == ProtectionEnchantment.Type.FIRE && damageSource.isFire()) {
            callback.setReturnValue(0);
        } else if (this.type == ProtectionEnchantment.Type.EXPLOSION && damageSource.isExplosion()) {
            callback.setReturnValue(0);
        } else if (this.type == ProtectionEnchantment.Type.PROJECTILE && damageSource.isProjectile()) {
            callback.setReturnValue(0);
        }
    }

    @Shadow
    private ProtectionEnchantment.Type type;

}
