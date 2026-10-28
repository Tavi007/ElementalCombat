package Tavi007.ElementalCombat.common.data.datapack;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import Tavi007.ElementalCombat.common.network.packages.SynchronizeDatapackPacket;
import Tavi007.ElementalCombat.server.network.ServerPacketSender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public class CombatPropertiesManager {

    private static final ResourceLocation BASE_ATTACK = new ResourceLocation("base_attack");
    private static AttackProperty defaultAttack = new AttackProperty("hit", "normal");
    private static Map<ResourceLocation, MobCombatConfig> defaultMobConfigs = new HashMap<>();
    private static Map<ResourceLocation, CombatConfig> defaultItemConfigs = new HashMap<>();
    private static Map<ResourceLocation, DefenseConfig> defaultBiomeConfigs = new HashMap<>();
    private static Map<ResourceLocation, AttackConfig> defaultProjectileConfigs = new HashMap<>();
    private static Map<ResourceLocation, AttackProperty> defaultDamageTypeProperties = new HashMap<>();

    public static void replace(
            AttackProperty defaultAttack,
            Map<ResourceLocation, MobCombatConfig> defaultMobConfigs,
            Map<ResourceLocation, CombatConfig> defaultItemConfigs,
            Map<ResourceLocation, DefenseConfig> defaultBiomeConfigs,
            Map<ResourceLocation, AttackConfig> defaultProjectileConfigs,
            Map<ResourceLocation, AttackProperty> defaultDamageTypeProperties
    ) {
        Constants.LOG.info(defaultAttack.toString());
        CombatPropertiesManager.defaultAttack = defaultAttack;
        CombatPropertiesManager.defaultMobConfigs = defaultMobConfigs;
        CombatPropertiesManager.defaultItemConfigs = defaultItemConfigs;
        CombatPropertiesManager.defaultBiomeConfigs = defaultBiomeConfigs;
        CombatPropertiesManager.defaultProjectileConfigs = defaultProjectileConfigs;
        CombatPropertiesManager.defaultDamageTypeProperties = defaultDamageTypeProperties;
    }

    public static String getDefaultStyle() {
        return defaultAttack.style();
    }

    public static String getDefaultElement() {
        return defaultAttack.element();
    }

    public static MobCombatConfig getMobDefaultConfig(ResourceLocation rl) {
        return defaultMobConfigs.getOrDefault(rl, new MobCombatConfig());
    }

    public static CombatConfig getItemDefaultConfig(ResourceLocation rl) {
        return defaultItemConfigs.getOrDefault(rl, new CombatConfig());
    }

    public static DefenseConfig getBiomeDefaultConfig(ResourceLocation rl) {
        return defaultBiomeConfigs.getOrDefault(rl, new DefenseConfig());
    }

    public static AttackConfig getProjectileDefaultConfig(ResourceLocation rl) {
        return defaultProjectileConfigs.getOrDefault(rl, new AttackConfig());
    }

    public static AttackProperty getDamageTypeDefaultProperty(ResourceLocation rl) {
        return defaultDamageTypeProperties.getOrDefault(rl, defaultAttack);
    }

    private static void logLoadedData() {
        logLoadedData("server", defaultMobConfigs.size(), "mobs");
        logLoadedData("server", defaultItemConfigs.size(), "items");
        logLoadedData("server", defaultBiomeConfigs.size(), "biomes");
        logLoadedData("server", defaultProjectileConfigs.size(), "projectiles");
        logLoadedData("server", defaultDamageTypeProperties.size(), "damage types");
    }

    private static void logLoadedData(String side, int size, String type) {
        Constants.LOG.info(side + " loaded " + size + " combat properties for " + type);
    }

    public static void sendSyncMessage(ServerPlayer player) {
        SynchronizeDatapackPacket packet = new SynchronizeDatapackPacket(
                defaultAttack,
                defaultMobConfigs,
                defaultItemConfigs,
                defaultBiomeConfigs,
                defaultProjectileConfigs,
                defaultDamageTypeProperties);
        ServerPacketSender.sendPacket(packet, player);
    }
}
