package Tavi007.ElementalCombat.common.api;

import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatMobData;
import Tavi007.ElementalCombat.common.data.DatapackDataAccessor;
import Tavi007.ElementalCombat.common.util.ResourceLocationAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;

public class DatapackDataAPI {


    public static String getDefaultStyle() {
        return DatapackDataAccessor.getDefaultStyle();
    }

    public static String getDefaultElement() {
        return DatapackDataAccessor.getDefaultElement();
    }

    public static ElementalCombatMobData getMobElementalCombatMobData(ResourceLocation rl) {
        return DatapackDataAccessor.getMobDefaultData(rl);
    }

    public static ElementalCombatMobData getElementalCombatMobData(LivingEntity entity) {
        return getMobElementalCombatMobData(ResourceLocationAccessor.getResourceLocation(entity));
    }

    public static ElementalCombatLayer getItemElementalCombatLayer(ResourceLocation rl) {
        return DatapackDataAccessor.getItemDefaultLayer(rl);
    }

    public static ElementalCombatLayer getElementalCombatLayer(ItemStack stack) {
        return getItemElementalCombatLayer(ResourceLocationAccessor.getResourceLocation(stack));
    }


    public static DefenseLayer getBiomeDefenseLayer(ResourceLocation rl) {
        return DatapackDataAccessor.getBiomeDefaultLayer(rl);
    }

    public static DefenseLayer getDefenseLayer(Biome biome) {
        return getBiomeDefenseLayer(ResourceLocationAccessor.getResourceLocation(biome));
    }

    public static AttackLayer getProjectileAttackLayer(ResourceLocation rl) {
        return DatapackDataAccessor.getProjectileDefaultLayer(rl);
    }

    public static AttackLayer getAttackLayer(Projectile entity) {
        return getProjectileAttackLayer(ResourceLocationAccessor.getResourceLocation(entity));
    }

    public static AttackLayer getDamageTypeAttackLayer(ResourceLocation rl) {
        return DatapackDataAccessor.getDamageTypeDefaultLayer(rl);
    }

    public static AttackLayer getAttackLayer(DamageType damageType) {
        return getDamageTypeAttackLayer(ResourceLocationAccessor.getResourceLocation(damageType));
    }
}
