package Tavi007.ElementalCombat.common.data.datapack;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.data.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class CombatPropertiesListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .registerTypeAdapter(DefenseMap.class, new DefenseMapAdapater())
            .create();
    private static final ResourceLocation BASE_ATTACK = new ResourceLocation("base_attack");
    private static final AttackLayer DEFAULT_ATTACK_LAYER = new AttackLayer("hit", "normal", Condition.BASE);

    public CombatPropertiesListener(String name) {
        super(GSON, name);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonObjects, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
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
    }
}
