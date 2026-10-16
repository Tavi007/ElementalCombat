package Tavi007.ElementalCombat.common.data.datapack;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.data.*;
import Tavi007.ElementalCombat.common.network.SyncronizeDatapackPacket;
import Tavi007.ElementalCombat.server.network.ServerPacketSender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public class CombatPropertiesManager  {

    private static final ResourceLocation BASE_ATTACK = new ResourceLocation("base_attack");
    private static AttackLayer defaultAttackLayer = new AttackLayer("hit", "normal", Condition.BASE);
    private static Map<ResourceLocation, ElementalCombatMobData> defaultMobLayers = new HashMap<>();
    private static Map<ResourceLocation, ElementalCombatLayer> defaultItemLayers = new HashMap<>();
    private static Map<ResourceLocation, DefenseLayer> defaultBiomeLayers = new HashMap<>();
    private static Map<ResourceLocation, AttackLayer> defaultProjectileLayers = new HashMap<>();
    private static Map<ResourceLocation, AttackLayer> defaultDamageTypeLayers = new HashMap<>();

    public static void replace(
        AttackLayer defaultAttackLayer,
        Map<ResourceLocation, ElementalCombatMobData> defaultMobLayers,
        Map<ResourceLocation, ElementalCombatLayer> defaultItemLayers,
        Map<ResourceLocation, DefenseLayer> defaultBiomeLayers,
        Map<ResourceLocation, AttackLayer> defaultProjectileLayers,
        Map<ResourceLocation, AttackLayer> defaultDamageTypeLayers
    ) {
        Constants.LOG.info(defaultAttackLayer.toString());
        CombatPropertiesManager.defaultAttackLayer = defaultAttackLayer;
        CombatPropertiesManager.defaultMobLayers = defaultMobLayers;
        CombatPropertiesManager.defaultItemLayers = defaultItemLayers;
        CombatPropertiesManager.defaultBiomeLayers = defaultBiomeLayers;
        CombatPropertiesManager.defaultProjectileLayers = defaultProjectileLayers;
        CombatPropertiesManager.defaultDamageTypeLayers = defaultDamageTypeLayers;
    }

    public static String getDefaultStyle() {
        return defaultAttackLayer.getStyle();
    }

    public static String getDefaultElement() {
        return defaultAttackLayer.getElement();
    }

    public static ElementalCombatMobData getMobDefaultData(ResourceLocation rl) {
        return defaultMobLayers.getOrDefault(rl, new ElementalCombatMobData());
    }

    public static ElementalCombatLayer getItemDefaultLayer(ResourceLocation rl) {
        return defaultItemLayers.getOrDefault(rl, new ElementalCombatLayer());
    }

    public static DefenseLayer getBiomeDefaultLayer(ResourceLocation rl) {
        return defaultBiomeLayers.getOrDefault(rl, new DefenseLayer());
    }

    public static AttackLayer getProjectileDefaultLayer(ResourceLocation rl) {
        return defaultProjectileLayers.getOrDefault(rl, new AttackLayer());
    }

    public static AttackLayer getDamageTypeDefaultLayer(ResourceLocation rl) {
        return defaultDamageTypeLayers.getOrDefault(rl, new AttackLayer());
    }

    private static void logLoadedData() {
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
