package Tavi007.ElementalCombat.common.api;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseMap;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseProperty;
import Tavi007.ElementalCombat.common.data.datapack.CombatPropertiesManager;
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

import java.util.Map;

public class BasePropertiesAPI {

    ////////////////
    // Attackdata //
    ////////////////


    /**
     * Returns a copy of the {@link AttackProperty} of the {@link EffectInstance}.
     * Currently only checks for INSTANT_DAMAGE, which has the style 'magic'. Might rework at some point.
     *
     * @param effectInstance The EffectInstance.
     * @return copy of AttackProperty.
     */
    public static AttackProperty getAttackProperty(MobEffectInstance effectInstance) {
        String style = CombatPropertiesManager.getDefaultStyle();
        String element = CombatPropertiesManager.getDefaultElement();
        MobEffect effect = effectInstance.getEffect();
        if (MobEffects.HARM.equals(effect)) {
            style = Constants.MAGIC;
        } else if (effect instanceof ElementalHarmingEffect) {
            return ((ElementalHarmingEffect) effect).getAttackProperty();
        }
        return new AttackProperty(style, element);
    }

    /**
     * Returns a copy of the {@link AttackProperty} of the {@link Enchantment}.
     * Currently only checks hardcoded values. Might rework at some point.
     *
     * @param enchantment The Enchantment.
     * @return copy of AttackProperty.
     */
    public static AttackProperty getAttackProperty(Enchantment enchantment) {
        String style = CombatPropertiesManager.getDefaultStyle();
        if (enchantment instanceof ElementalWeaponEnchantment) {
            String element = ((ElementalWeaponEnchantment) enchantment).getElement();
            return new AttackProperty(style, element);
        } else if (enchantment.equals(Enchantments.FIRE_ASPECT)
                || enchantment.equals(Enchantments.FLAMING_ARROWS)) {
            String element = Constants.FIRE;
            return new AttackProperty(style, element);
        } else if (enchantment.equals(Enchantments.CHANNELING)) {
            String element = Constants.THUNDER;
            return new AttackProperty(style, element);
        }
        return null;
    }

    public static AttackConfig getAttackConfig(Map<Enchantment, Integer> enchantments) {
        return null;
    }


    /////////////////
    // Defensedata //
    /////////////////

    /**
     * Returns a copy of the {@link DefenseProperty} of the {@link EffectInstance}.
     * Currently only checks for my own Effect class and vanilla ones.
     *
     * @param effectInstance The EffectInstance.
     * @return copy of DefenseProperty.
     */
    public static DefenseProperty getDefenseProperty(MobEffectInstance effectInstance) {
        DefenseMap element = new DefenseMap();
        MobEffect effect = effectInstance.getEffect();
        if (effect instanceof ElementalResistanceEffect) {
            element = ((ElementalResistanceEffect) effect).getDefenseMap(effectInstance);
        } else if (MobEffects.FIRE_RESISTANCE.equals(effect)) {
            element = ((ElementalResistanceEffect) ModMobEffects.FIRE_RESISTANCE).getDefenseMap(effectInstance);
        }
        return new DefenseProperty(new DefenseMap(), element);
    }

    /**
     * Returns a copy of the {@link DefenseProperty} of any {@link Enchantment}.
     *
     * @param enchantment The Enchantment.
     * @return copy of DefenseProperty.
     */
    public static DefenseProperty getDefenseProperty(Enchantment enchantment, int level) {
        if (level != 0) {
            if (enchantment instanceof IResistanceEnchantment resistEnchantment) {
                return resistEnchantment.getDefenseProperty(level);
            } else if (enchantment instanceof ProtectionEnchantment protectEnchantment) { // vanilla enchantments
                if (protectEnchantment.type == ProtectionEnchantment.Type.FIRE) {
                    return ElementalResistanceEnchantment.FIRE_RESISTANCE.getDefenseProperty(level);
                } else if (protectEnchantment.type == ProtectionEnchantment.Type.EXPLOSION) {
                    return StyleResistanceEnchantment.EXPLOSION_RESISTANCE.getDefenseProperty(level);
                } else if (protectEnchantment.type == ProtectionEnchantment.Type.PROJECTILE) {
                    return StyleResistanceEnchantment.PROJECTILE_RESISTANCE.getDefenseProperty(level);
                }
            }
        }
        return new DefenseProperty();
    }

}
