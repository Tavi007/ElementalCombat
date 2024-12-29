package Tavi007.ElementalCombat.common.data;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatMobData;
import Tavi007.ElementalCombat.common.network.SyncronizeDatapackPacket;
import Tavi007.ElementalCombat.server.network.ServerPacketSender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public class DatapackDataAccessor {

    private static final Map<ResourceLocation, ElementalCombatMobData> loadedMobLayers = new HashMap<>();
    private static final Map<ResourceLocation, ElementalCombatLayer> loadedItemLayers = new HashMap<>();
    private static final Map<ResourceLocation, DefenseLayer> loadedBiomeLayers = new HashMap<>();
    private static final Map<ResourceLocation, AttackLayer> loadedProjectileLayers = new HashMap<>();
    private static final Map<ResourceLocation, AttackLayer> loadedDamageTypeLayers = new HashMap<>();
    private static final AttackLayer defaultAttack = new AttackLayer("hit", "normal");

    private static AttackLayer fillWithDefault(AttackLayer attackLayer) {
        if (attackLayer == null) {
            return DatapackDataAccessor.defaultAttack;
        }
        if (attackLayer.getStyle() != null) {
            attackLayer.setStyle(defaultAttack.getStyle());
        }
        if (attackLayer.getElement() != null) {
            attackLayer.setElement(defaultAttack.getElement());
        }
        return attackLayer;
    }

    private static DefenseLayer fillWithDefault(DefenseLayer defenseLayer) {
        if (defenseLayer == null) {
            return new DefenseLayer();
        }
        if (defenseLayer.getStyles() != null) {
        }
        return defenseLayer;
    }

    public static void clear() {
        defaultAttack.set("hit", "normal");
        loadedMobLayers.clear();
        loadedItemLayers.clear();
        loadedBiomeLayers.clear();
        loadedProjectileLayers.clear();
        loadedDamageTypeLayers.clear();
    }

    public static void setDefaultAttackLayer(AttackLayer defaultAttack) {
        if (defaultAttack != null) {
            if (defaultAttack.getStyle() != null) {
                DatapackDataAccessor.defaultAttack.setStyle(defaultAttack.getStyle());
            }
            if (defaultAttack.getElement() != null) {
                DatapackDataAccessor.defaultAttack.setElement(defaultAttack.getElement());
            }
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
        return DatapackDataAccessor.loadedMobLayers.getOrDefault(rl, new ElementalCombatMobData());
    }

    public static void putItemDefaultLayer(ResourceLocation rl, ElementalCombatLayer data) {
        DatapackDataAccessor.loadedItemLayers.put(rl, data);
    }

    public static ElementalCombatLayer getItemDefaultLayer(ResourceLocation rl) {
        return DatapackDataAccessor.loadedItemLayers.getOrDefault(rl, new ElementalCombatLayer());
    }

    public static void putBiomeDefaultLayer(ResourceLocation rl, DefenseLayer data) {
        DatapackDataAccessor.loadedBiomeLayers.put(rl, data);
    }

    public static DefenseLayer getBiomeDefaultLayer(ResourceLocation rl) {
        return DatapackDataAccessor.loadedBiomeLayers.getOrDefault(rl, new DefenseLayer());
    }

    public static void putProjectileDefaultLayer(ResourceLocation rl, AttackLayer data) {
        DatapackDataAccessor.loadedProjectileLayers.put(rl, data);
    }

    public static AttackLayer getProjectileDefaultLayer(ResourceLocation rl) {
        return DatapackDataAccessor.loadedProjectileLayers.getOrDefault(rl, new AttackLayer());
    }

    public static void putDamageTypeDefaultLayer(ResourceLocation rl, AttackLayer data) {
        DatapackDataAccessor.loadedDamageTypeLayers.put(rl, data);
    }

    public static AttackLayer getDamageTypeDefaultLayer(ResourceLocation rl) {
        return DatapackDataAccessor.loadedDamageTypeLayers.getOrDefault(rl, new AttackLayer());
    }

    public static void logLoadedData() {
        logLoadedData("server", loadedMobLayers.size(), "mobs");
        logLoadedData("server", loadedItemLayers.size(), "items");
        logLoadedData("server", loadedBiomeLayers.size(), "biomes");
        logLoadedData("server", loadedProjectileLayers.size(), "projectiles");
        logLoadedData("server", loadedDamageTypeLayers.size(), "damage types");
    }

    private static void logLoadedData(String side, int size, String type) {
        Constants.LOG.info(side + " loaded " + size + " combat properties for " + type);
    }

    public static void sendSyncMessage(ServerPlayer player) {
        SyncronizeDatapackPacket packet = new SyncronizeDatapackPacket(
                defaultAttack,
                loadedMobLayers,
                loadedItemLayers,
                loadedBiomeLayers,
                loadedDamageTypeLayers,
                loadedProjectileLayers);
        ServerPacketSender.sendPacket(packet, player);
    }
}
