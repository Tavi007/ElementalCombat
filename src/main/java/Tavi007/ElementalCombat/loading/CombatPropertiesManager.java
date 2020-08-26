package Tavi007.ElementalCombat.loading;

import java.util.Deque;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class CombatPropertiesManager extends JsonReloadListener 
{
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
	
	private Map<ResourceLocation, EntityCombatProperties> registeredEntityData = ImmutableMap.of();
	private Map<ResourceLocation, ItemCombatProperties> registeredItemData = ImmutableMap.of();
	private Map<ResourceLocation, BiomeCombatProperties> registeredBiomeData = ImmutableMap.of();
	private Map<ResourceLocation, DamageSourceCombatProperties> registeredDamageSourceData = ImmutableMap.of();
	private static ThreadLocal<Deque<CombatPropertiesContext>> dataContext = new ThreadLocal<Deque<CombatPropertiesContext>>();

	public CombatPropertiesManager() 
	{
		super(GSON, "combat_properties");
	}

	protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) 
	{
		Builder<ResourceLocation, EntityCombatProperties> builderEntity = ImmutableMap.builder();
		Builder<ResourceLocation, ItemCombatProperties> builderItem = ImmutableMap.builder();
		Builder<ResourceLocation, BiomeCombatProperties> builderBiome = ImmutableMap.builder();
		Builder<ResourceLocation, DamageSourceCombatProperties> builderDamageSource = ImmutableMap.builder();

		JsonElement jsonobject = objectIn.remove(EntityCombatProperties.EMPTY_RESOURCELOCATION);
		if (jsonobject != null) 
		{
			ElementalCombat.LOGGER.warn("Datapack tried to redefine {} elemental entity data, ignoring", (Object)EntityCombatProperties.EMPTY_RESOURCELOCATION);
		}

		objectIn.forEach((rl, json) -> 
		{
			try (net.minecraft.resources.IResource res = resourceManagerIn.getResource(getPreparedPath(rl));)
			{
				//check if entity/item/biome/damageSource gets loaded
				if(rl.getPath().contains("/entities/")){
					EntityCombatProperties combatProperties = (EntityCombatProperties) loadData(GSON, rl, json, res == null || !res.getPackName().equals("main"), EntityCombatProperties.class);
					builderEntity.put(rl, combatProperties);
				}
				else if(rl.getPath().contains("/items/")){
					ItemCombatProperties combatProperties = loadData(GSON, rl, json, res == null || !res.getPackName().equals("main"), ItemCombatProperties.class);
					builderItem.put(rl, combatProperties);
				}
				else if(rl.getPath().contains("/biomes/")){
					BiomeCombatProperties combatProperties = loadData(GSON, rl, json, res == null || !res.getPackName().equals("main"), BiomeCombatProperties.class);
					builderBiome.put(rl, combatProperties);
				}
				else if(rl.getPath().contains("/damage_sources/")){
					DamageSourceCombatProperties combatProperties = loadData(GSON, rl, json, res == null || !res.getPackName().equals("main"), DamageSourceCombatProperties.class);
					builderDamageSource.put(rl, combatProperties);
				}
			}
			catch (Exception exception)
			{
				ElementalCombat.LOGGER.error("Couldn't parse elemental data {}", rl, exception);
			}
		});

		//not sure if empty resourceLocation is necessary...
		builderEntity.put(EMPTY_RESOURCELOCATION, new EntityCombatProperties());
		this.registeredEntityData = builderEntity.build();

		builderItem.put(EMPTY_RESOURCELOCATION, new ItemCombatProperties());
		this.registeredItemData = builderItem.build();

		builderBiome.put(EMPTY_RESOURCELOCATION, new BiomeCombatProperties());
		this.registeredBiomeData = builderBiome.build();

		builderDamageSource.put(EMPTY_RESOURCELOCATION, new DamageSourceCombatProperties());
		this.registeredDamageSourceData = builderDamageSource.build();
	}

	public static JsonElement toJson(EntityCombatProperties elementalData)
	{
		return GSON.toJsonTree(elementalData);
	}

	public Set<ResourceLocation> getEntityDataKeys() 
	{
		return this.registeredEntityData.keySet();
	}

	@Nullable
	private <T> T loadData(Gson gson, ResourceLocation name, JsonElement data, boolean custom, Class<T> classOfT)
	{
		Deque<CombatPropertiesContext> que = dataContext.get();
		if (que == null)
		{
			que = Queues.newArrayDeque();
			dataContext.set(que);
		}

		T ret = null;
		try
		{
			que.push(new CombatPropertiesContext(name, custom));
			ret = gson.fromJson(data, classOfT);
			que.pop();
		}
		catch (JsonParseException e)
		{
			que.pop();
			throw e;
		}

		/*
		if (!custom)
		{
			CombatPropertiesLoadEvent event = new CombatPropertiesLoadEvent(name, ret, this);
			if (MinecraftForge.EVENT_BUS.post(event))
			{
				ret = EntityCombatProperties.EMPTY;
			}
			ret = event.getEntityData();
		}

		*/
		//if (ret != null)
		//    ret.freeze();

		return ret;
	}

	public EntityCombatProperties getEntityDataFromLocation(ResourceLocation rl)
	{
		return this.registeredEntityData.getOrDefault(rl, EntityCombatProperties.EMPTY);
	}

	public ItemCombatProperties getItemDataFromLocation(ResourceLocation rl)
	{
		return this.registeredItemData.getOrDefault(rl, ItemCombatProperties.EMPTY);
	}

	public BiomeCombatProperties getBiomeDataFromLocation(ResourceLocation rl)
	{
		return this.registeredBiomeData.getOrDefault(rl, BiomeCombatProperties.EMPTY);
	}

	public DamageSourceCombatProperties getDamageSourceDataFromLocation(ResourceLocation rl)
	{
		return this.registeredDamageSourceData.getOrDefault(rl, DamageSourceCombatProperties.EMPTY);
	}
}