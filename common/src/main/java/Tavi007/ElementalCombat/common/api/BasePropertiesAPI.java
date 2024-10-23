package Tavi007.ElementalCombat.common.api;

import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import Tavi007.ElementalCombat.common.enchantments.ElementalResistanceEnchantment;
import Tavi007.ElementalCombat.common.enchantments.ElementalWeaponEnchantment;
import Tavi007.ElementalCombat.common.enchantments.IResistanceEnchantment;
import Tavi007.ElementalCombat.common.enchantments.StyleResistanceEnchantment;
import Tavi007.ElementalCombat.common.potions.ElementalHarmingEffect;
import Tavi007.ElementalCombat.common.potions.ElementalResistanceEffect;
import Tavi007.ElementalCombat.common.registry.ModMobEffects;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

public class BasePropertiesAPI {

    ////////////////
    // Attackdata //
    ////////////////


    /**
     * Returns a copy of the {@link AttackData} of the {@link EffectInstance}.
     * Currently only checks for INSTANT_DAMAGE, which has the style 'magic'. Might rework at some point.
     *
     * @param effect The EffectInstance.
     * @return copy of AttackData.
     */
    public static AttackLayer getAttackLayer(MobEffectInstance effectInstance) {
        AttackLayer base = new AttackLayer();
        MobEffect effect = effectInstance.getEffect();
        if (MobEffects.HARM.equals(effect)) {
            base.setStyle("magic");
        } else if (effect instanceof ElementalHarmingEffect) {
            base = ((ElementalHarmingEffect) effect).getAttackLayer();
        }
        return base;
    }

    /**
     * Returns a copy of the {@link AttackData} of the {@link Enchantment}.
     * Currently only checks hardcoded values. Might rework at some point.
     *
     * @param ench The Enchantment.
     * @return copy of AttackData.
     */
    public static AttackLayer getAttackLayer(Enchantment ench) {
        AttackLayer layer = new AttackLayer();
        if (ench instanceof ElementalWeaponEnchantment) {
            layer = ((ElementalWeaponEnchantment) ench).getAttackLayer();
        } else if (ench.equals(Enchantments.FIRE_ASPECT) || ench.equals(Enchantments.FLAMING_ARROWS)) {
            layer.setElement("fire");
        } else if (ench.equals(Enchantments.CHANNELING)) {
            layer.setElement("thunder");
        }
        return layer;
    }


    /**
     * Returns a copy of the {@link DefenseData} of any {@link Enchantment}.
     *
     * @param ench The Enchantment.
     * @return copy of DefenseData.
     */
    public static DefenseLayer getDefenseLayer(Enchantment ench, int level) {
        DefenseLayer defenseLayer = new DefenseLayer();
        if (level != 0) {
            if (ench instanceof IResistanceEnchantment resistEnchantment) {
                defenseLayer = resistEnchantment.getDefenseLayer(level);
            } else if (ench instanceof ProtectionEnchantment protectEnchantment) { // vanilla enchantments
                if (protectEnchantment.type == ProtectionEnchantment.Type.FIRE) {
                    defenseLayer = ElementalResistanceEnchantment.FIRE_RESISTANCE.getDefenseLayer(level);
                } else if (protectEnchantment.type == ProtectionEnchantment.Type.EXPLOSION) {
                    defenseLayer = StyleResistanceEnchantment.EXPLOSION_RESISTANCE.getDefenseLayer(level);
                } else if (protectEnchantment.type == ProtectionEnchantment.Type.PROJECTILE) {
                    defenseLayer = StyleResistanceEnchantment.PROJECTILE_RESISTANCE.getDefenseLayer(level);
                }
            }
        }
        return defenseLayer;
    }

    /**
     * Returns a copy of the {@link DefenseLayer} of the {@link EffectInstance}.
     * Currently only checks for my own Effect class and vanilla ones.
     *
     * @param effect The EffectInstance.
     * @return copy of DefenseLayer.
     */
    public static DefenseLayer getDefenseLayer(MobEffectInstance effectInstance) {
        DefenseLayer base = new DefenseLayer();
        MobEffect effect = effectInstance.getEffect();
        if (effect instanceof ElementalResistanceEffect) {
            base = ((ElementalResistanceEffect) effect).getDefenseLayer(effectInstance);
        } else if (MobEffects.FIRE_RESISTANCE.equals(effect)) {
            base = ((ElementalResistanceEffect) ModMobEffects.FIRE_RESISTANCE).getDefenseLayer(effectInstance);
        }
        return base;
    }

}
