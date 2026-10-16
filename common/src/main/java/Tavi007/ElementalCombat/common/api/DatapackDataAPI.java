package Tavi007.ElementalCombat.common.api;

import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatMobData;
import Tavi007.ElementalCombat.common.data.datapack.CombatPropertiesManager;
import Tavi007.ElementalCombat.common.util.ResourceLocationAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;

public class DatapackDataAPI {


    public static String getDefaultStyle() {
        return CombatPropertiesManager.getDefaultStyle();
    }

    public static String getDefaultElement() {
        return CombatPropertiesManager.getDefaultElement();
    }

    public static ElementalCombatMobData getMobElementalCombatMobData(ResourceLocation rl) {
        return CombatPropertiesManager.getMobDefaultData(rl);
    }

    public static ElementalCombatMobData getElementalCombatMobData(LivingEntity entity) {
        return getMobElementalCombatMobData(ResourceLocationAccessor.getResourceLocation(entity));
    }

    public static ElementalCombatLayer getItemElementalCombatLayer(ResourceLocation rl) {
        return CombatPropertiesManager.getItemDefaultLayer(rl);
    }

    public static ElementalCombatLayer getElementalCombatLayer(ItemStack stack) {
        return getItemElementalCombatLayer(ResourceLocationAccessor.getResourceLocation(stack));
    }


    public static DefenseLayer getBiomeDefenseLayer(ResourceLocation rl) {
        return CombatPropertiesManager.getBiomeDefaultLayer(rl);
    }

    public static DefenseLayer getDefenseLayer(Biome biome) {
        return getBiomeDefenseLayer(ResourceLocationAccessor.getResourceLocation(biome));
    }

    public static AttackLayer getProjectileAttackLayer(ResourceLocation rl) {
        return CombatPropertiesManager.getProjectileDefaultLayer(rl);
    }

    public static AttackLayer getAttackLayer(Projectile entity) {
        return getProjectileAttackLayer(ResourceLocationAccessor.getResourceLocation(entity));
    }

    public static AttackLayer getDamageTypeAttackLayer(ResourceLocation rl) {
        return CombatPropertiesManager.getDamageTypeDefaultLayer(rl);
    }

    public static AttackLayer getAttackLayer(DamageSource damageSource) {
        return getDamageTypeAttackLayer(ResourceLocationAccessor.getResourceLocation(damageSource));
    }
}
