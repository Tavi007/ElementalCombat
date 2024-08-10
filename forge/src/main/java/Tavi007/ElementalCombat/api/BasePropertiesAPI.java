package Tavi007.ElementalCombat.api;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import Tavi007.ElementalCombat.common.data.datapack.ElementalCombatProperties;
import Tavi007.ElementalCombat.common.data.datapack.MobCombatProperties;
import Tavi007.ElementalCombat.common.enchantments.ElementalResistanceEnchantment;
import Tavi007.ElementalCombat.common.enchantments.ElementalWeaponEnchantment;
import Tavi007.ElementalCombat.common.enchantments.IResistanceEnchantment;
import Tavi007.ElementalCombat.common.enchantments.StyleResistanceEnchantment;
import Tavi007.ElementalCombat.common.potions.ElementalHarmingEffect;
import Tavi007.ElementalCombat.common.potions.ElementalResistanceEffect;
import Tavi007.ElementalCombat.init.PotionList;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraftforge.registries.ForgeRegistries;

public class BasePropertiesAPI {

    ////////////////
    // Attackdata //
    ////////////////

    /**
     * Returns the default attack style.
     */
    public static String getDefaultAttackStyle() {
        return ElementalCombat.COMBAT_PROPERTIES_MANGER.getBaseAttackProperties().getAttackStyleCopy();
    }

    /**
     * Returns the default attack element.
     */
    public static String getDefaultAttackElement() {
        return ElementalCombat.COMBAT_PROPERTIES_MANGER.getBaseAttackProperties().getAttackElementCopy();
    }

    /**
     * Returns a copy of the base {@link AttackData} of any {@link LivingEntity}.
     *
     * @param livingEntity The LivingEntity.
     * @return copy of AttackData.
     */
    public static AttackLayer getAttackData(LivingEntity livingEntity) {
        ResourceLocation rlEntity = ForgeRegistries.ENTITY_TYPES.getKey(livingEntity.getType());
        if (rlEntity == null) {
            return new AttackLayer();
        }
        ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "mobs/" + rlEntity.getPath());
        MobCombatProperties property = ElementalCombat.COMBAT_PROPERTIES_MANGER.getMobDataFromLocation(rlProperties);
        return new AttackLayer(property.getAttackStyleCopy(), property.getAttackElementCopy());
    }

    /**
     * Returns a copy of the base {@link AttackData} of any {@link ItemStack}.
     *
     * @param stack The ItemStack.
     * @return copy of AttackData.
     */
    public static AttackLayer getAttackData(ItemStack stack) {
        ResourceLocation rlItem = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (rlItem == null) {
            return new AttackLayer();
        }
        ResourceLocation rlProperties = new ResourceLocation(rlItem.getNamespace(), "items/" + rlItem.getPath());
        ElementalCombatProperties property = ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties);
        return new AttackLayer(property.getAttackStyleCopy(), property.getAttackElementCopy());
    }

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
     * Returns a copy of the {@link AttackData} of any {@link DamageSource}.
     * This includes lightning, burning, drowning, suffocating in a wall and so on.
     *
     * @param damageSource The DamageSource.
     * @return copy of AttackData.
     */
    public static AttackLayer getAttackData(DamageSource damageSource) {
        AttackLayer layer = new AttackLayer();
        damageSource.typeHolder().unwrapKey().ifPresent(resourceKey -> {
            ResourceLocation rlDamageSource = resourceKey.location();
            rlDamageSource = rlDamageSource.withPrefix("damage_types/");
            AttackOnlyCombatProperties property = ElementalCombat.COMBAT_PROPERTIES_MANGER.getDamageTypeDataFromLocation(rlDamageSource);
            layer.set(new AttackLayer(property.getAttackStyleCopy(), property.getAttackElementCopy()));
        });
        return layer;
    }

    /**
     * Returns a copy of the base {@link AttackData} of any {@link ProjectileEntity}.
     *
     * @param projectile The Projectile.
     * @return copy of AttackData.
     */
    public static AttackLayer getAttackData(Projectile projectile) {
        if (projectile instanceof ThrowableItemProjectile) {
            ItemStack stack = ((ThrowableItemProjectile) projectile).getItem();
            return getAttackData(stack);
        }
        ResourceLocation rlEntity = ForgeRegistries.ENTITY_TYPES.getKey(projectile.getType());
        ResourceLocation rlProjectile = new ResourceLocation(rlEntity.getNamespace(), "projectiles/" + rlEntity.getPath());
        AttackOnlyCombatProperties property = ElementalCombat.COMBAT_PROPERTIES_MANGER.getProjectileDataFromLocation(rlProjectile);
        return new AttackLayer(property.getAttackStyleCopy(), property.getAttackElementCopy());
    }

    /////////////////
    // Defensedata //
    /////////////////

    /**
     * Returns a copy of the base {@link DefenseData} of any {@link LivingEntity}.
     *
     * @param livingEntity The LivingEntity.
     * @return copy of DefenseData.
     */
    public static DefenseLayer getDefenseLayer(LivingEntity livingEntity) {
        ResourceLocation rlEntity = ForgeRegistries.ENTITY_TYPES.getKey(livingEntity.getType());
        if (rlEntity == null) {
            return new DefenseLayer();
        }
        ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "mobs/" + rlEntity.getPath());
        MobCombatProperties property = ElementalCombat.COMBAT_PROPERTIES_MANGER.getMobDataFromLocation(rlProperties);
        return new DefenseLayer(property.getDefenseStyleCopy(), property.getDefenseElementCopy());
    }

    /**
     * Returns a copy of the base {@link DefenseData} of any {@link ItemStack}.
     *
     * @param stack The ItemStack.
     * @return copy of DefenseData.
     */
    public static DefenseLayer getDefenseLayer(ItemStack stack) {
        ResourceLocation rlItem = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (rlItem == null) {
            return new DefenseLayer();
        }
        ResourceLocation rlProperties = new ResourceLocation(rlItem.getNamespace(), "items/" + rlItem.getPath());
        ElementalCombatProperties property = ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties);
        return new DefenseLayer(property.getDefenseStyleCopy(), property.getDefenseElementCopy());
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
     * Returns a copy of the {@link DefenseData} of a Biome of ResourceLocation {@link rlBiome}.
     *
     * @param rlBiome The ResourceLocation
     * @return copy of DefenseData.
     */
    public static DefenseLayer getDefenseLayer(ResourceLocation rlBiome) {
        DefenseLayer defData = new DefenseLayer();
        if (rlBiome == null) {
            return defData;
        }
        ResourceLocation rlProperties = new ResourceLocation(rlBiome.getNamespace(), "biomes/" + rlBiome.getPath());
        DefenseOnlyCombatProperties property = ElementalCombat.COMBAT_PROPERTIES_MANGER.getBiomeDataFromLocation(rlProperties);
        return new DefenseLayer(property.getDefenseStyleCopy(), property.getDefenseElementCopy());
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
            base = ((ElementalResistanceEffect) PotionList.FIRE_RESISTANCE_EFFECT.get()).getDefenseLayer(effectInstance);
        }
        return base;
    }

    /////////////////
    // other stuff //
    /////////////////

    /**
     * Returns the biome_dependency of any {@link LivingEntity}.
     *
     * @param livingEntity The LivingEntity.
     * @return boolean isBiomeDependent.
     */
    public static boolean isBiomeDependent(LivingEntity livingEntity) {
        ResourceLocation rlEntity = ForgeRegistries.ENTITY_TYPES.getKey(livingEntity.getType());
        if (rlEntity == null) {
            return false;
        }
        ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "mobs/" + rlEntity.getPath());
        return ElementalCombat.COMBAT_PROPERTIES_MANGER.getMobDataFromLocation(rlProperties).getBiomeDependency();
    }
}
