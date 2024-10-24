package Tavi007.ElementalCombat.common.data;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatMobData;
import Tavi007.ElementalCombat.common.network.SyncronizeDatapackPacket;
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
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final ThreadLocal<Deque<CombatPropertiesContext>> dataContext = new ThreadLocal<Deque<CombatPropertiesContext>>();

    public CombatPropertiesManager() {
        super(GSON, "elemental_combat_properties");
    }

    public void set(SyncronizeDatapackPacket message) {
//        baseAttackProperties = message.getBaseAttack();
//        registeredMobData = message.getMobData();
//        registeredItemData = message.getItemData();
//        registeredBiomeData = message.getBiomeData();
//        registeredProjectileData = message.getProjectileData();
//        registeredDamageTypeData = message.getDamageTypeData();
//
//        ElementalCombat.LOGGER.info("Client loaded default attack style: " + baseAttackProperties.getAttackStyleCopy());
//        ElementalCombat.LOGGER.info("Client loaded default attack element: " + baseAttackProperties.getAttackElementCopy());
//        logLoading("client", registeredMobData.size() - 1, "mobs");
//        logLoading("client", registeredItemData.size() - 1, "items");
//        logLoading("client", registeredBiomeData.size() - 1, "biomes");
//        logLoading("client", registeredProjectileData.size() - 1, "projectiles");
//        logLoading("client", registeredDamageTypeData.size() - 1, "damage sources");
    }

    private void logLoading(String side, int size, String type) {
        Constants.LOG.info(side + " loaded " + size + " combat properties for " + type);
    }

    public SyncronizeDatapackPacket createSyncMessage() {
//        return new SyncronizeDatapackPacket(baseAttackProperties,
//                registeredMobData,
//                registeredItemData,
//                registeredBiomeData,
//                registeredDamageTypeData,
//                registeredProjectileData);
        return null;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        if (objectIn.remove(EMPTY_RESOURCELOCATION) != null) {
            Constants.LOG.warn("Datapack tried to redefine {} elemental entity data, ignoring", EMPTY_RESOURCELOCATION);
        }

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
                    DatapackDataAccessor.putMobDefaultData(rl, loadData(GSON, rl, json, ElementalCombatMobData.class));
                    type = "mobs";
                } else if (rl.getPath().contains("items/")) {
                    DatapackDataAccessor.putItemDefaultLayer(rl, loadData(GSON, rl, json, ElementalCombatLayer.class));
                    type = "items";
                } else if (rl.getPath().contains("biomes/")) {
                    DatapackDataAccessor.putBiomeDefaultLayer(rl, loadData(GSON, rl, json, DefenseLayer.class));
                    type = "biomes";
                } else if (rl.getPath().contains("damage_types/")) {
                    DatapackDataAccessor.putDamageTypeDefaultLayer(rl, loadData(GSON, rl, json, AttackLayer.class));
                    type = "damage_types";
                } else if (rl.getPath().contains("projectiles/")) {
                    DatapackDataAccessor.putProjectileDefaultLayer(rl, loadData(GSON, rl, json, AttackLayer.class));
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

//        logLoading("server", registeredMobData.size(), "mobs");
//        logLoading("server", registeredItemData.size(), "items");
//        logLoading("server", registeredBiomeData.size(), "biomes");
//        logLoading("server", registeredProjectileData.size(), "projectiles");
//        logLoading("server", registeredDamageTypeData.size(), "damage sources");
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
