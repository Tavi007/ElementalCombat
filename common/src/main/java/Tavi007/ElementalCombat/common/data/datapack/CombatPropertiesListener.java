package Tavi007.ElementalCombat.common.data.datapack;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class CombatPropertiesListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .registerTypeAdapter(DefenseMap.class, new DefenseMapAdapter())
            .create();
    private static final ResourceLocation BASE_ATTACK = new ResourceLocation("base_attack");
    private static final AttackProperty DEFAULT_ATTACK = new AttackProperty("hit", "normal");

    public CombatPropertiesListener(String name) {
        super(GSON, name);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonObjects, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        Map<String, Map<String, Integer>> counter = new HashMap<String, Map<String, Integer>>(); // for logging

        AtomicReference<AttackProperty> defaultAttack = new AtomicReference<>(new AttackProperty("hit", "normal"));
        Map<ResourceLocation, MobCombatConfig> defaultMobLayers = new HashMap<>();
        Map<ResourceLocation, CombatConfig> defaultItemLayers = new HashMap<>();
        Map<ResourceLocation, DefenseConfig> defaultBiomeLayers = new HashMap<>();
        Map<ResourceLocation, AttackConfig> defaultProjectileLayers = new HashMap<>();
        Map<ResourceLocation, AttackProperty> defaultDamageTypeProperties = new HashMap<>();

        jsonObjects.forEach((rl, json) -> {
            try {
                String modId = rl.getNamespace();
                String type = "incorrect resource location (check the directory path for typos!)";

                // check if entity/item/biome/damageSource gets loaded
                if (rl.equals(BASE_ATTACK)) {
                    defaultAttack.set(GSON.fromJson(json, AttackProperty.class));
                } else if (rl.getPath().contains("mobs/")) {
                    ResourceLocation new_rl = new ResourceLocation(modId, rl.getPath().replace("mobs/", ""));
                    defaultMobLayers.put(new_rl, GSON.fromJson(json, MobCombatConfig.class));
                    type = "mobs";
                } else if (rl.getPath().contains("items/")) {
                    ResourceLocation new_rl = new ResourceLocation(modId, rl.getPath().replace("items/", ""));
                    defaultItemLayers.put(new_rl, GSON.fromJson(json, CombatConfig.class));
                    type = "items";
                } else if (rl.getPath().contains("biomes/")) {
                    ResourceLocation new_rl = new ResourceLocation(modId, rl.getPath().replace("biomes/", ""));
                    defaultBiomeLayers.put(new_rl, GSON.fromJson(json, DefenseConfig.class));
                    type = "biomes";
                } else if (rl.getPath().contains("projectiles/")) {
                    ResourceLocation new_rl = new ResourceLocation(modId, rl.getPath().replace("projectiles/", ""));
                    defaultProjectileLayers.put(new_rl, GSON.fromJson(json, AttackConfig.class));
                    type = "projectiles";
                } else if (rl.getPath().contains("damage_types/")) {
                    ResourceLocation new_rl = new ResourceLocation(modId, rl.getPath().replace("damage_types/", ""));
                    defaultDamageTypeProperties.put(new_rl, GSON.fromJson(json, AttackProperty.class));
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

        CombatPropertiesManager.replace(
                defaultAttack.get(),
                defaultMobLayers,
                defaultItemLayers,
                defaultBiomeLayers,
                defaultProjectileLayers,
                defaultDamageTypeProperties
        );
    }
}
