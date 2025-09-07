package Tavi007.ElementalCombat.common.data;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.data.*;
import Tavi007.ElementalCombat.common.data.datapack.DefenseMapAdapater;
import Tavi007.ElementalCombat.common.network.SyncronizeDatapackPacket;
import Tavi007.ElementalCombat.server.network.ServerPacketSender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public class DatapackDataAccessor {

    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .registerTypeAdapter(DefenseMap.class, new DefenseMapAdapater())
            .create();
    private static final ResourceLocation BASE_ATTACK = new ResourceLocation("base_attack");
    private static final AttackLayer defaultAttackLayer = new AttackLayer("hit", "normal", Condition.BASE);
    private static Map<ResourceLocation, ElementalCombatMobData> defaultMobLayers = new HashMap<>();
    private static Map<ResourceLocation, ElementalCombatLayer> defaultItemLayers = new HashMap<>();
    private static Map<ResourceLocation, DefenseLayer> defaultBiomeLayers = new HashMap<>();
    private static Map<ResourceLocation, AttackLayer> defaultProjectileLayers = new HashMap<>();
    private static Map<ResourceLocation, AttackLayer> defaultDamageTypeLayers = new HashMap<>();

    public static void resetDatapackData(Map<ResourceLocation, JsonElement> jsonObjects) {
        Map<String, Map<String, Integer>> counter = new HashMap<String, Map<String, Integer>>(); // for logging

        AttackLayer defaultAttackLayer = new AttackLayer("hit", "normal", Condition.BASE);
        Map<ResourceLocation, ElementalCombatMobData> defaultMobLayers = new HashMap<>();
        Map<ResourceLocation, ElementalCombatLayer> defaultItemLayers = new HashMap<>();
        Map<ResourceLocation, DefenseLayer> defaultBiomeLayers = new HashMap<>();
        Map<ResourceLocation, AttackLayer> defaultProjectileLayers = new HashMap<>();
        Map<ResourceLocation, AttackLayer> defaultDamageTypeLayers = new HashMap<>();

        jsonObjects.forEach((rl, json) -> {
            try {
                String modId = rl.getNamespace();
                String type = "incorrect resource location (check the directory path for typos!)";

                // check if entity/item/biome/damageSource gets loaded
                if (rl.equals(BASE_ATTACK)) {
                    defaultAttackLayer.set(GSON.fromJson(json, AttackLayer.class));
                } else if (rl.getPath().contains("mobs/")) {
                    ResourceLocation new_rl = new ResourceLocation(modId, rl.getPath().replace("mobs/", ""));
                    defaultMobLayers.put(new_rl, GSON.fromJson(json, ElementalCombatMobData.class));
                    type = "mobs";
                } else if (rl.getPath().contains("items/")) {
                    ResourceLocation new_rl = new ResourceLocation(modId, rl.getPath().replace("items/", ""));
                    defaultItemLayers.put(new_rl, GSON.fromJson(json, ElementalCombatLayer.class));
                    type = "items";
                } else if (rl.getPath().contains("biomes/")) {
                    ResourceLocation new_rl = new ResourceLocation(modId, rl.getPath().replace("biomes/", ""));
                    defaultBiomeLayers.put(new_rl, GSON.fromJson(json, DefenseLayer.class));
                    type = "biomes";
                } else if (rl.getPath().contains("projectiles/")) {
                    ResourceLocation new_rl = new ResourceLocation(modId, rl.getPath().replace("projectiles/", ""));
                    defaultProjectileLayers.put(new_rl, GSON.fromJson(json, AttackLayer.class));
                    type = "projectiles";
                } else if (rl.getPath().contains("damage_types/")) {
                    ResourceLocation new_rl = new ResourceLocation(modId, rl.getPath().replace("damage_types/", ""));
                    defaultDamageTypeLayers.put(new_rl, GSON.fromJson(json, AttackLayer.class));
                    type = "damage_types";
                } else {
                    Constants.LOG.warn("Unknown resource location {}", rl);
                }

                Map<String, Integer> propertyCounter = counter.get(modId);
                if (propertyCounter == null) {
                    propertyCounter = new HashMap<String, Integer>();
                    propertyCounter.put(type, 0);
                    counter.put(modId, propertyCounter);
                } else {
                    Integer count = propertyCounter.get(type);
                    if (count == null) {
                        propertyCounter.put(type, 0);
                    } else {
                        propertyCounter.put(type, propertyCounter.get(type) + 1);
                    }
                }
            } catch (Exception exception) {
                Constants.LOG.error("Couldn't parse combat properties {}", rl, exception);
            }
        });

        counter.forEach((modId, propertyCounter) -> {
            Constants.LOG.info("The mod " + modId + " loaded: ");
            propertyCounter.forEach((type, amount) -> {
                Constants.LOG.info(amount + " " + type);
            });
        });

        resetDatapackData(defaultAttackLayer,
                defaultMobLayers,
                defaultItemLayers,
                defaultBiomeLayers,
                defaultProjectileLayers,
                defaultDamageTypeLayers);
    }

    public static void resetDatapackData(AttackLayer defaultAttackLayer,
                                         Map<ResourceLocation, ElementalCombatMobData> defaultMobLayers,
                                         Map<ResourceLocation, ElementalCombatLayer> defaultItemLayers,
                                         Map<ResourceLocation, DefenseLayer> defaultBiomeLayers,
                                         Map<ResourceLocation, AttackLayer> defaultProjectileLayers,
                                         Map<ResourceLocation, AttackLayer> defaultDamageTypeLayers) {

        setDefaultAttackLayer(defaultAttackLayer);
        DatapackDataAccessor.defaultMobLayers = defaultMobLayers;
        DatapackDataAccessor.defaultItemLayers = defaultItemLayers;
        DatapackDataAccessor.defaultBiomeLayers = defaultBiomeLayers;
        DatapackDataAccessor.defaultProjectileLayers = defaultProjectileLayers;
        DatapackDataAccessor.defaultDamageTypeLayers = defaultDamageTypeLayers;
        logLoadedData();
    }


//    private static AttackLayer fillWithDefault(AttackLayer attackLayer) {
//        if (attackLayer == null) {
//            return DatapackDataAccessor.defaultAttackLayer;
//        }
//        if (attackLayer.getStyle() != null) {
//            attackLayer.setStyle(defaultAttackLayer.getStyle());
//        }
//        if (attackLayer.getElement() != null) {
//            attackLayer.setElement(defaultAttackLayer.getElement());
//        }
//        return attackLayer;
//    }
//
//    private static DefenseLayer fillWithDefault(DefenseLayer defenseLayer) {
//        if (defenseLayer == null) {
//            return new DefenseLayer();
//        }
//        if (defenseLayer.getStyles() != null) {
//        }
//        return defenseLayer;
//    }

    public static void setDefaultAttackLayer(AttackLayer defaultAttackLayer) {
        if (defaultAttackLayer != null) {
            if (defaultAttackLayer.getStyle() != null) {
                DatapackDataAccessor.defaultAttackLayer.setStyle(defaultAttackLayer.getStyle());
            }
            if (defaultAttackLayer.getElement() != null) {
                DatapackDataAccessor.defaultAttackLayer.setElement(defaultAttackLayer.getElement());
            }
        }
        Constants.LOG.info("Server loaded default attack style: " + DatapackDataAccessor.defaultAttackLayer.getStyle());
        Constants.LOG.info("Server loaded default attack element: " + DatapackDataAccessor.defaultAttackLayer.getElement());
    }

    public static String getDefaultStyle() {
        return defaultAttackLayer.getStyle();
    }

    public static String getDefaultElement() {
        return defaultAttackLayer.getElement();
    }

    public static void putMobDefaultData(ResourceLocation rl, ElementalCombatMobData data) {
        defaultMobLayers.put(rl, data);
    }


    public static ElementalCombatMobData getMobDefaultData(ResourceLocation rl) {
        return defaultMobLayers.getOrDefault(rl, new ElementalCombatMobData());
    }

    public static void putItemDefaultLayer(ResourceLocation rl, ElementalCombatLayer data) {
        defaultItemLayers.put(rl, data);
    }

    public static ElementalCombatLayer getItemDefaultLayer(ResourceLocation rl) {
        return defaultItemLayers.getOrDefault(rl, new ElementalCombatLayer());
    }

    public static void putBiomeDefaultLayer(ResourceLocation rl, DefenseLayer data) {
        defaultBiomeLayers.put(rl, data);
    }

    public static DefenseLayer getBiomeDefaultLayer(ResourceLocation rl) {
        return defaultBiomeLayers.getOrDefault(rl, new DefenseLayer());
    }

    public static void putProjectileDefaultLayer(ResourceLocation rl, AttackLayer data) {
        defaultProjectileLayers.put(rl, data);
    }

    public static AttackLayer getProjectileDefaultLayer(ResourceLocation rl) {
        return defaultProjectileLayers.getOrDefault(rl, new AttackLayer());
    }

    public static void putDamageTypeDefaultLayer(ResourceLocation rl, AttackLayer data) {
        defaultDamageTypeLayers.put(rl, data);
    }

    public static AttackLayer getDamageTypeDefaultLayer(ResourceLocation rl) {
        return defaultDamageTypeLayers.getOrDefault(rl, new AttackLayer());
    }

    public static void logLoadedData() {
        logLoadedData("server", defaultMobLayers.size(), "mobs");
        logLoadedData("server", defaultItemLayers.size(), "items");
        logLoadedData("server", defaultBiomeLayers.size(), "biomes");
        logLoadedData("server", defaultProjectileLayers.size(), "projectiles");
        logLoadedData("server", defaultDamageTypeLayers.size(), "damage types");
    }

    private static void logLoadedData(String side, int size, String type) {
        Constants.LOG.info(side + " loaded " + size + " combat properties for " + type);
    }

    public static void sendSyncMessage(ServerPlayer player) {
        SyncronizeDatapackPacket packet = new SyncronizeDatapackPacket(
                defaultAttackLayer,
                defaultMobLayers,
                defaultItemLayers,
                defaultBiomeLayers,
                defaultProjectileLayers,
                defaultDamageTypeLayers);
        ServerPacketSender.sendPacket(packet, player);
    }
}
