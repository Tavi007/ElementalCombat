package Tavi007.ElementalCombat.loading;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Queues;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.network.clientbound.BasePropertiesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CombatPropertiesManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
    public static final ResourceLocation BASE_ATTACK = new ResourceLocation("base_attack");

    private Map<ResourceLocation, MobCombatProperties> registeredMobData = ImmutableMap.of();
    private Map<ResourceLocation, ElementalCombatProperties> registeredItemData = ImmutableMap.of();
    private Map<ResourceLocation, BiomeCombatProperties> registeredBiomeData = ImmutableMap.of();
    private Map<ResourceLocation, AttackOnlyCombatProperties> registeredDamageTypeData = ImmutableMap.of();
    private Map<ResourceLocation, AttackOnlyCombatProperties> registeredProjectileData = ImmutableMap.of();

    private AttackOnlyCombatProperties baseAttackProperties = new AttackOnlyCombatProperties("hit", "normal");

    private static ThreadLocal<Deque<CombatPropertiesContext>> dataContext = new ThreadLocal<Deque<CombatPropertiesContext>>();

    public CombatPropertiesManager() {
        super(GSON, "elemental_combat_properties");
    }

    public void set(BasePropertiesPacket message) {
        baseAttackProperties = message.getBaseAttack();
        registeredMobData = message.getMobData();
        registeredItemData = message.getItemData();
        registeredBiomeData = message.getBiomeData();
        registeredProjectileData = message.getProjectileData();
        registeredDamageTypeData = message.getDamageTypeData();

        ElementalCombat.LOGGER.info("Client loaded default attack style: " + baseAttackProperties.getAttackStyle());
        ElementalCombat.LOGGER.info("Client loaded default attack element: " + baseAttackProperties.getAttackElement());
        logLoading("client", registeredMobData.size() - 1, "mobs");
        logLoading("client", registeredItemData.size() - 1, "items");
        logLoading("client", registeredBiomeData.size() - 1, "biomes");
        logLoading("client", registeredProjectileData.size() - 1, "projectiles");
        logLoading("client", registeredDamageTypeData.size() - 1, "damage types");
    }

    private void logLoading(String side, int size, String type) {
        ElementalCombat.LOGGER.info(side + " loaded " + size + " combat properties for " + type);
    }

    public BasePropertiesPacket createSyncMessage() {
        return new BasePropertiesPacket(baseAttackProperties,
            registeredMobData,
            registeredItemData,
            registeredBiomeData,
            registeredDamageTypeData,
            registeredProjectileData);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        Builder<ResourceLocation, MobCombatProperties> builderMob = ImmutableMap.builder();
        Builder<ResourceLocation, ElementalCombatProperties> builderItem = ImmutableMap.builder();
        Builder<ResourceLocation, BiomeCombatProperties> builderBiome = ImmutableMap.builder();
        Builder<ResourceLocation, AttackOnlyCombatProperties> builderDamageType = ImmutableMap.builder();
        Builder<ResourceLocation, AttackOnlyCombatProperties> builderProjectile = ImmutableMap.builder();

        if (objectIn.remove(EMPTY_RESOURCELOCATION) != null) {
            ElementalCombat.LOGGER.warn("Datapack tried to redefine {} elemental entity data, ignoring", (Object) EMPTY_RESOURCELOCATION);
        }

        Map<String, Map<String, Integer>> counter = new HashMap<String, Map<String, Integer>>();
        objectIn.forEach((rl, json) -> {
            try {
                Resource res = resourceManagerIn.getResourceOrThrow(getPreparedPath(rl));
                String modid = rl.getNamespace();
                String type = "incorrect entries (check path!)";

                // check if entity/item/biome/damageSource gets loaded
                if (rl.equals(BASE_ATTACK)) {
                    baseAttackProperties = loadData(GSON, rl, json, AttackOnlyCombatProperties.class);
                } else if (rl.getPath().contains("mobs/")) {
                    MobCombatProperties combatProperties = loadData(GSON, rl, json, MobCombatProperties.class);
                    builderMob.put(rl, combatProperties);
                    type = "mobs";
                } else if (rl.getPath().contains("items/")) {
                    ElementalCombatProperties combatProperties = loadData(GSON, rl, json, ElementalCombatProperties.class);
                    builderItem.put(rl, combatProperties);
                    type = "items";
                } else if (rl.getPath().contains("biomes/")) {
                    BiomeCombatProperties combatProperties = loadData(GSON, rl, json, BiomeCombatProperties.class);
                    builderBiome.put(rl, combatProperties);
                    type = "biomes";
                } else if (rl.getPath().contains("damage_types/")) {
                    AttackOnlyCombatProperties combatProperties = loadData(GSON, rl, json, AttackOnlyCombatProperties.class);
                    builderDamageType.put(rl, combatProperties);
                    type = "damage_types";
                } else if (rl.getPath().contains("projectiles/")) {
                    AttackOnlyCombatProperties combatProperties = loadData(GSON, rl, json, AttackOnlyCombatProperties.class);
                    builderProjectile.put(rl, combatProperties);
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
                ElementalCombat.LOGGER.error("Couldn't parse combat properties {}", rl, exception);
            }
        });

        if (baseAttackProperties == null) {
            baseAttackProperties = new AttackOnlyCombatProperties("hit", "normal");
        }

        // not sure if empty resourceLocation is necessary...
        builderMob.put(EMPTY_RESOURCELOCATION, new MobCombatProperties());
        builderItem.put(EMPTY_RESOURCELOCATION, new ElementalCombatProperties());
        builderBiome.put(EMPTY_RESOURCELOCATION, new BiomeCombatProperties());
        builderDamageType.put(EMPTY_RESOURCELOCATION, baseAttackProperties);
        builderProjectile.put(EMPTY_RESOURCELOCATION, baseAttackProperties);

        registeredMobData = builderMob.build();
        registeredItemData = builderItem.build();
        registeredBiomeData = builderBiome.build();
        registeredDamageTypeData = builderDamageType.build();
        registeredProjectileData = builderProjectile.build();

        ElementalCombat.LOGGER.info("Server loaded default attack style: " + baseAttackProperties.getAttackStyle());
        ElementalCombat.LOGGER.info("Server loaded default attack element: " + baseAttackProperties.getAttackElement());

        counter.forEach((modid, propertyCounter) -> {
            ElementalCombat.LOGGER.info("The mod " + modid + " loaded: ");
            propertyCounter.forEach((type, amount) -> {
                ElementalCombat.LOGGER.info("" + amount + " " + type);
            });
        });

        logLoading("server", registeredMobData.size(), "mobs");
        logLoading("server", registeredItemData.size(), "items");
        logLoading("server", registeredBiomeData.size(), "biomes");
        logLoading("server", registeredProjectileData.size(), "projectiles");
        logLoading("server", registeredDamageTypeData.size(), "damage types");
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

    public AttackOnlyCombatProperties getBaseAttackProperties() {
        return new AttackOnlyCombatProperties(baseAttackProperties);
    }

    public MobCombatProperties getMobDataFromLocation(ResourceLocation rl) {
        return new MobCombatProperties(registeredMobData.getOrDefault(rl, new MobCombatProperties()));
    }

    public ElementalCombatProperties getItemDataFromLocation(ResourceLocation rl) {
        return new ElementalCombatProperties(registeredItemData.getOrDefault(rl, new ElementalCombatProperties()));
    }

    public BiomeCombatProperties getBiomeDataFromLocation(ResourceLocation rl) {
        return new BiomeCombatProperties(registeredBiomeData.getOrDefault(rl, new BiomeCombatProperties()));
    }

    public AttackOnlyCombatProperties getDamageTypeDataFromLocation(ResourceLocation rl) {
        return new AttackOnlyCombatProperties(registeredDamageTypeData.getOrDefault(rl, baseAttackProperties));
    }

    public AttackOnlyCombatProperties getProjectileDataFromLocation(ResourceLocation rl) {
        return new AttackOnlyCombatProperties(registeredProjectileData.getOrDefault(rl, baseAttackProperties));
    }
}
