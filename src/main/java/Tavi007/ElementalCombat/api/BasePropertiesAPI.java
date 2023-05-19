package Tavi007.ElementalCombat.api;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.enchantments.ElementalWeaponEnchantment;
import Tavi007.ElementalCombat.enchantments.IResistanceEnchantment;
import Tavi007.ElementalCombat.init.PotionList;
import Tavi007.ElementalCombat.loading.AttackOnlyCombatProperties;
import Tavi007.ElementalCombat.loading.BiomeCombatProperties;
import Tavi007.ElementalCombat.loading.ElementalCombatProperties;
import Tavi007.ElementalCombat.loading.MobCombatProperties;
import Tavi007.ElementalCombat.potions.ElementalHarmingEffect;
import Tavi007.ElementalCombat.potions.ElementalResistanceEffect;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.BlockPos;
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
import net.minecraftforge.registries.ForgeRegistries;

public class BasePropertiesAPI {

    ////////////////
    // Attackdata //
    ////////////////

    /**
     * Returns the default attack style.
     */
    public static String getDefaultAttackStyle() {
        return ElementalCombat.COMBAT_PROPERTIES_MANGER.getBaseAttackProperties().getAttackStyle();
    }

    /**
     * Returns the default attack element.
     */
    public static String getDefaultAttackElement() {
        return ElementalCombat.COMBAT_PROPERTIES_MANGER.getBaseAttackProperties().getAttackElement();
    }

    /**
     * Returns a copy of the default {@link AttackData} of any {@link LivingEntity}.
     * 
     * @param livingEntity
     *            The LivingEntity.
     * @return copy of AttackData.
     */
    public static AttackLayer getAttackData(LivingEntity livingEntity) {
        ResourceLocation rlEntity = ForgeRegistries.ENTITY_TYPES.getKey(livingEntity.getType());
        if (rlEntity == null) {
            return new AttackLayer();
        }
        ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "mobs/" + rlEntity.getPath());
        MobCombatProperties property = new MobCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getMobDataFromLocation(rlProperties));
        return new AttackLayer(property.getAttackStyle(), property.getAttackElement());
    }

    /**
     * Returns a copy of the default {@link AttackData} of any {@link ItemStack}.
     * 
     * @param stack
     *            The ItemStack.
     * @return copy of AttackData.
     */
    public static AttackLayer getAttackData(ItemStack stack) {
        ResourceLocation rlItem = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (rlItem == null) {
            return new AttackLayer();
        }
        ResourceLocation rlProperties = new ResourceLocation(rlItem.getNamespace(), "items/" + rlItem.getPath());
        ElementalCombatProperties property = new ElementalCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties));
        return new AttackLayer(property.getAttackStyle(), property.getAttackElement());
    }

    /**
     * Returns a copy of the default {@link AttackData} of the {@link EffectInstance}.
     * Currently only checks for INSTANT_DAMAGE, which has the style 'magic'. Might rework at some point.
     * 
     * @param effect
     *            The EffectInstance.
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
     * Returns a copy of the default {@link AttackData} of the {@link Enchantment}.
     * Currently only checks hardcoded values. Might rework at some point.
     * 
     * @param ench
     *            The Enchantment.
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
     * Returns a copy of the default {@link AttackData} of any {@link DamageSource}.
     * This includes lightning, burning, drowning, suffocating in a wall and so on.
     * 
     * @param damageSource
     *            The DamageSource.
     * @return copy of AttackData.
     */
    public static AttackLayer getAttackData(DamageSource damageSource) {
        if (damageSource.getMsgId() != null) {
            String name = damageSource.getMsgId().toLowerCase();
            ResourceLocation rlDamageSource = new ResourceLocation("minecraft", "damage_sources/" + name);
            AttackOnlyCombatProperties property = new AttackOnlyCombatProperties(
                ElementalCombat.COMBAT_PROPERTIES_MANGER.getDamageSourceDataFromLocation(rlDamageSource));
            return new AttackLayer(property.getAttackStyle(), property.getAttackElement());
        }
        return new AttackLayer();
    }

    /**
     * Returns a copy of the default {@link AttackData} of any {@link ProjectileEntity}.
     * 
     * @param projectile
     *            The Projectile.
     * @return copy of AttackData.
     */
    public static AttackLayer getAttackData(Projectile projectile) {
        if (projectile instanceof ThrowableItemProjectile) {
            ItemStack stack = ((ThrowableItemProjectile) projectile).getItem();
            return getAttackData(stack);
        }
        ResourceLocation resourcelocation = ForgeRegistries.ENTITY_TYPES.getKey(projectile.getType());
        ResourceLocation rlProjectile = new ResourceLocation(resourcelocation.getNamespace(), "projectiles/" + resourcelocation.getPath());
        AttackOnlyCombatProperties property = new AttackOnlyCombatProperties(
            ElementalCombat.COMBAT_PROPERTIES_MANGER.getProjectileDataFromLocation(rlProjectile));
        return new AttackLayer(property.getAttackStyle(), property.getAttackElement());
    }

    /////////////////
    // Defensedata //
    /////////////////

    /**
     * Returns a copy of the default {@link DefenseData} of any {@link LivingEntity}.
     * 
     * @param livingEntity
     *            The LivingEntity.
     * @return copy of DefenseData.
     */
    public static DefenseLayer getDefenseLayer(LivingEntity livingEntity) {
        ResourceLocation rlEntity = ForgeRegistries.ENTITY_TYPES.getKey(livingEntity.getType());
        if (rlEntity == null) {
            return new DefenseLayer();
        }
        ResourceLocation rlProperties = new ResourceLocation(rlEntity.getNamespace(), "mobs/" + rlEntity.getPath());
        MobCombatProperties property = new MobCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getMobDataFromLocation(rlProperties));
        return new DefenseLayer(property.getDefenseStyle(), property.getDefenseElement());
    }

    /**
     * Returns a copy of the default {@link DefenseData} of any {@link ItemStack}.
     * 
     * @param stack
     *            The ItemStack.
     * @return copy of DefenseData.
     */
    public static DefenseLayer getDefenseLayer(ItemStack stack) {
        ResourceLocation rlItem = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (rlItem == null) {
            return new DefenseLayer();
        }
        ResourceLocation rlProperties = new ResourceLocation(rlItem.getNamespace(), "items/" + rlItem.getPath());
        ElementalCombatProperties property = new ElementalCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties));
        return new DefenseLayer(property.getDefenseStyle(), property.getDefenseElement());
    }

    /**
     * Returns a copy of the default {@link DefenseLayer} of the {@link EffectInstance}.
     * Currently only checks for my own Effect class and vanilla ones.
     * 
     * @param effect
     *            The EffectInstance.
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

    /**
     * Returns a copy of the default {@link DefenseData} of any {@link Enchantment}.
     * 
     * @param ench
     *            The Enchantment.
     * @return copy of DefenseData.
     */
    public static DefenseLayer getDefenseLayer(Enchantment ench, int level) {
        DefenseLayer defenseLayer = new DefenseLayer();
        if (level != 0 && ench instanceof IResistanceEnchantment) {
            defenseLayer = ((IResistanceEnchantment) ench).getDefenseLayer(level);
        }
        return defenseLayer;
    }

    /**
     * Returns a copy of the default {@link DefenseData} of a Biome at position {@link BlockPos}.
     * 
     * @param world
     *            A World.
     * @param position
     *            The BlockPos
     * @return copy of DefenseData.
     */
    public static DefenseLayer getDefenseLayer(ResourceLocation rlBiome) {
        DefenseLayer defData = new DefenseLayer();
        if (rlBiome == null) {
            return defData;
        }
        ResourceLocation rlProperties = new ResourceLocation(rlBiome.getNamespace(), "biomes/" + rlBiome.getPath());
        ;
        BiomeCombatProperties property = new BiomeCombatProperties(ElementalCombat.COMBAT_PROPERTIES_MANGER.getBiomeDataFromLocation(rlProperties));
        defData.addElement(property.getDefenseElement());
        return defData;
    }

    /////////////////
    // other stuff //
    /////////////////

    /**
     * Returns the default value of the biome_dependency of any {@link LivingEntity}.
     * 
     * @param livingEntity
     *            The LivingEntity.
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
