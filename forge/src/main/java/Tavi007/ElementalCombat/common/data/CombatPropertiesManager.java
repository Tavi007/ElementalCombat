package Tavi007.ElementalCombat.common.data;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.data.*;
import Tavi007.ElementalCombat.common.data.datapack.DefenseMapAdapater;
import com.google.common.collect.Queues;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CombatPropertiesManager extends SimpleJsonResourceReloadListener {

    public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(Constants.MOD_ID, "empty");
    public static final ResourceLocation BASE_ATTACK = new ResourceLocation("base_attack");
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .registerTypeAdapter(DefenseMap.class, new DefenseMapAdapater())
            .create();
    private static final ThreadLocal<Deque<CombatPropertiesContext>> dataContext = new ThreadLocal<Deque<CombatPropertiesContext>>();

    public CombatPropertiesManager() {
        super(GSON, "elemental_combat_properties");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        if (objectIn.remove(EMPTY_RESOURCELOCATION) != null) {
            Constants.LOG.warn("Datapack tried to redefine {} elemental entity data, ignoring", EMPTY_RESOURCELOCATION);
        }

        DatapackDataAccessor.clear();
        Map<String, Map<String, Integer>> counter = new HashMap<String, Map<String, Integer>>();
        objectIn.forEach((rl, json) -> {
            try {
                Resource res = resourceManagerIn.getResourceOrThrow(getPreparedPath(rl));
                String modid = rl.getNamespace();
                String type = "incorrect entries (check path!)";

                // check if entity/item/biome/damageSource gets loaded
                if (rl.equals(BASE_ATTACK)) {
                    DatapackDataAccessor.setDefaultAttackLayer(loadData(GSON, rl, json, AttackLayer.class));
                } else if (rl.getPath().contains("mobs/")) {
                    ResourceLocation new_rl = new ResourceLocation(modid, rl.getPath().replace("mobs/", ""));
                    DatapackDataAccessor.putMobDefaultData(new_rl, loadData(GSON, rl, json, ElementalCombatMobData.class));
                    type = "mobs";
                } else if (rl.getPath().contains("items/")) {
                    ResourceLocation new_rl = new ResourceLocation(modid, rl.getPath().replace("items/", ""));
                    DatapackDataAccessor.putItemDefaultLayer(new_rl, loadData(GSON, rl, json, ElementalCombatLayer.class));
                    type = "items";
                } else if (rl.getPath().contains("biomes/")) {
                    ResourceLocation new_rl = new ResourceLocation(modid, rl.getPath().replace("biomes/", ""));
                    DatapackDataAccessor.putBiomeDefaultLayer(new_rl, loadData(GSON, rl, json, DefenseLayer.class));
                    type = "biomes";
                } else if (rl.getPath().contains("damage_types/")) {
                    ResourceLocation new_rl = new ResourceLocation(modid, rl.getPath().replace("damage_types/", ""));
                    DatapackDataAccessor.putDamageTypeDefaultLayer(new_rl, loadData(GSON, rl, json, AttackLayer.class));
                    type = "damage_types";
                } else if (rl.getPath().contains("projectiles/")) {
                    ResourceLocation new_rl = new ResourceLocation(modid, rl.getPath().replace("projectiles/", ""));
                    DatapackDataAccessor.putProjectileDefaultLayer(new_rl, loadData(GSON, rl, json, AttackLayer.class));
                    type = "projectiles";
                }

                Map<String, Integer> propertyCounter = counter.get(modid);
                if (propertyCounter == null) {
                    propertyCounter = new HashMap<String, Integer>();
                    propertyCounter.put(type, 0);
                    counter.put(modid, propertyCounter);
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

        counter.forEach((modid, propertyCounter) -> {
            Constants.LOG.info("The mod " + modid + " loaded: ");
            propertyCounter.forEach((type, amount) -> {
                Constants.LOG.info(amount + " " + type);
            });
        });

        DatapackDataAccessor.logLoadedData();
    }

    @Nullable
    private <T> T loadData(Gson gson, ResourceLocation name, JsonElement data, Class<T> classOfT) {
        Deque<CombatPropertiesContext> que = dataContext.get();
        if (que == null) {
            que = Queues.newArrayDeque();
            dataContext.set(que);
        }

        T ret = null;
        try {
            que.push(new CombatPropertiesContext(name));
            ret = gson.fromJson(data, classOfT);
            que.pop();
        } catch (JsonParseException e) {
            que.pop();
            throw e;
        }
        return ret;
    }
}
