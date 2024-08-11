package Tavi007.ElementalCombat.common.data;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatMobData;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class DatapackDataAccessor {

    private static final Map<ResourceLocation, ElementalCombatMobData> loadedMobLayers = new HashMap<>();
    private static final Map<ResourceLocation, ElementalCombatLayer> loadedItemLayers = new HashMap<>();
    private static final Map<ResourceLocation, DefenseLayer> loadedBiomeLayers = new HashMap<>();
    private static final Map<ResourceLocation, AttackLayer> loadedProjectileLayers = new HashMap<>();
    private static final Map<ResourceLocation, AttackLayer> loadedDamageTypeLayers = new HashMap<>();
    private static AttackLayer defaultAttack = new AttackLayer("hit", "normal");

    public static void setDefaultAttackLayer(AttackLayer defaultAttack) {
        if (defaultAttack != null) {
            DatapackDataAccessor.defaultAttack = defaultAttack;
        }
        Constants.LOG.info("Server loaded default attack style: " + DatapackDataAccessor.defaultAttack.getStyle());
        Constants.LOG.info("Server loaded default attack element: " + DatapackDataAccessor.defaultAttack.getElement());
    }

    public static String getDefaultStyle() {
        return DatapackDataAccessor.defaultAttack.getStyle();
    }

    public static String getDefaultElement() {
        return DatapackDataAccessor.defaultAttack.getElement();
    }

    public static void putMobDefaultData(ResourceLocation rl, ElementalCombatMobData data) {
        DatapackDataAccessor.loadedMobLayers.put(rl, data);
    }

    public static ElementalCombatMobData getMobDefaultData(ResourceLocation rl) {
        return DatapackDataAccessor.loadedMobLayers.get(rl);
    }

    public static void putItemDefaultLayer(ResourceLocation rl, ElementalCombatLayer data) {
        DatapackDataAccessor.loadedItemLayers.put(rl, data);
    }

    public static ElementalCombatLayer getItemDefaultLayer(ResourceLocation rl) {
        return DatapackDataAccessor.loadedItemLayers.get(rl);
    }

    public static void putBiomeDefaultLayer(ResourceLocation rl, DefenseLayer data) {
        DatapackDataAccessor.loadedBiomeLayers.put(rl, data);
    }

    public static DefenseLayer getBiomeDefaultLayer(ResourceLocation rl) {
        return DatapackDataAccessor.loadedBiomeLayers.get(rl);
    }

    public static void putProjectileDefaultLayer(ResourceLocation rl, AttackLayer data) {
        DatapackDataAccessor.loadedProjectileLayers.put(rl, data);
    }

    public static AttackLayer getProjectileDefaultLayer(ResourceLocation rl) {
        return DatapackDataAccessor.loadedProjectileLayers.get(rl);
    }

    public static void putDamageTypeDefaultLayer(ResourceLocation rl, AttackLayer data) {
        DatapackDataAccessor.loadedDamageTypeLayers.put(rl, data);
    }

    public static AttackLayer getDamageTypeDefaultLayer(ResourceLocation rl) {
        return DatapackDataAccessor.loadedDamageTypeLayers.get(rl);
    }

}
