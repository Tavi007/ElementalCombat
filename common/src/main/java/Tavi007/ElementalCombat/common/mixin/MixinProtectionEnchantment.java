package Tavi007.ElementalCombat.common.mixin;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProtectionEnchantment.class)
public class MixinProtectionEnchantment {

    @Shadow
    private ProtectionEnchantment.Type type;

    @Inject(at = @At("HEAD"), method = "getDamageProtection(ILnet/minecraft/world/damagesource/DamageSource;)I", cancellable = true)
    private void getDamageProtection(int level, DamageSource damageSource, CallbackInfoReturnable<Integer> callback) {
        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            callback.setReturnValue(0);
        } else if (this.type == ProtectionEnchantment.Type.FIRE && damageSource.is(DamageTypeTags.IS_FIRE)) {
            callback.setReturnValue(0);
        } else if (this.type == ProtectionEnchantment.Type.EXPLOSION && damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
            callback.setReturnValue(0);
        } else if (this.type == ProtectionEnchantment.Type.PROJECTILE && damageSource.is(DamageTypeTags.IS_PROJECTILE)) {
            callback.setReturnValue(0);
        }
    }

}
