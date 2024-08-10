package Tavi007.ElementalCombat.common.api;

import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatLayer;
import Tavi007.ElementalCombat.common.data.DatapackDataAccessor;
import net.minecraft.resources.ResourceLocation;

public class DatapackDataAPI {


    public static String getDefaultStyle() {
        return DatapackDataAccessor.getDefaultStyle();
    }

    public static String getDefaultElement() {
        return DatapackDataAccessor.getDefaultElement();
    }

    public static ElementalCombatLayer getMobElementalCombatLayer(ResourceLocation rl) {
        return DatapackDataAccessor.getMobData(rl);
    }

    public static ElementalCombatLayer getItemElementalCombatLayer(ResourceLocation rl) {
        return DatapackDataAccessor.getItemData(rl);
    }


    public static DefenseLayer getBiomeDefenseLayer(ResourceLocation rl) {
        return DatapackDataAccessor.getBiomeData(rl);
    }

    public static AttackLayer getProjectileAttackLayer(ResourceLocation rl) {
        return DatapackDataAccessor.getProjectileData(rl);
    }

    public static AttackLayer getDamageTypeAttackLayer(ResourceLocation rl) {
        return DatapackDataAccessor.getDamageTypeData(rl);
    }
}
