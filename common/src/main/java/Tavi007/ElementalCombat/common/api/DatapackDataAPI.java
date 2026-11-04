package Tavi007.ElementalCombat.common.api;

import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import Tavi007.ElementalCombat.common.data.datapack.CombatConfig;
import Tavi007.ElementalCombat.common.data.datapack.CombatPropertiesManager;
import Tavi007.ElementalCombat.common.data.datapack.MobCombatConfig;
import Tavi007.ElementalCombat.common.util.ResourceLocationAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;

public class DatapackDataAPI {


    public static AttackProperty getDefaultAttack() {
        return new AttackProperty(getDefaultStyle(), getDefaultElement());
    }

    public static String getDefaultStyle() {
        return CombatPropertiesManager.getDefaultStyle();
    }

    public static String getDefaultElement() {
        return CombatPropertiesManager.getDefaultElement();
    }

    public static MobCombatConfig getMobElementalCombatMobData(ResourceLocation rl) {
        return CombatPropertiesManager.getMobDefaultConfig(rl);
    }

    public static MobCombatConfig getMobElementalCombatMobData(LivingEntity entity) {
        return getMobElementalCombatMobData(ResourceLocationAccessor.getResourceLocation(entity));
    }

    public static CombatConfig getItemElementalCombatConfig(ResourceLocation rl) {
        return CombatPropertiesManager.getItemDefaultConfig(rl);
    }

    public static CombatConfig getItemElementalCombatConfig(ItemStack stack) {
        return getItemElementalCombatConfig(ResourceLocationAccessor.getResourceLocation(stack));
    }


    public static DefenseConfig getBiomeDefenseConfig(ResourceLocation rl) {
        return CombatPropertiesManager.getBiomeDefaultConfig(rl);
    }

    public static DefenseConfig getDefenseConfig(Biome biome) {
        return getBiomeDefenseConfig(ResourceLocationAccessor.getResourceLocation(biome));
    }

    public static AttackConfig getProjectileAttackConfig(ResourceLocation rl) {
        return CombatPropertiesManager.getProjectileDefaultConfig(rl);
    }

    public static AttackConfig getAttackConfig(Projectile entity) {
        return getProjectileAttackConfig(ResourceLocationAccessor.getResourceLocation(entity));
    }

    public static AttackProperty getDamageTypeAttackProperty(ResourceLocation rl) {
        return CombatPropertiesManager.getDamageTypeDefaultProperty(rl);
    }

    public static AttackProperty getAttackProperty(DamageSource damageSource) {
        return getDamageTypeAttackProperty(ResourceLocationAccessor.getResourceLocation(damageSource));
    }
}
