package Tavi007.ElementalCombat.loading;

import java.util.Deque;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class CombatPropertiesManager extends JsonReloadListener 
{
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	private static final Logger LOGGER = LogManager.getLogger();
	private Map<ResourceLocation, EntityCombatProperties> registeredEntityData = ImmutableMap.of();
	private Map<ResourceLocation, GeneralCombatProperties> registeredItemData = ImmutableMap.of();
	private static ThreadLocal<Deque<CombatPropertiesContext>> dataContext = new ThreadLocal<Deque<CombatPropertiesContext>>();

	public CombatPropertiesManager() 
	{
		super(GSON, "combat_properties");
	}

	protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) 
	{
		Builder<ResourceLocation, EntityCombatProperties> builderEntity = ImmutableMap.builder();
		Builder<ResourceLocation, GeneralCombatProperties> builderItem = ImmutableMap.builder();

		JsonElement jsonobject = objectIn.remove(EntityCombatProperties.EMPTY_RESOURCELOCATION);
		if (jsonobject != null) 
		{
			LOGGER.warn("Datapack tried to redefine {} elemental entity data, ignoring", (Object)EntityCombatProperties.EMPTY_RESOURCELOCATION);
		}

		objectIn.forEach((rl, json) -> 
		{
			try (net.minecraft.resources.IResource res = resourceManagerIn.getResource(getPreparedPath(rl));)
			{
				//check if entity or item gets loaded
				if(rl.getPath().contains("/entities/"))
				{
					EntityCombatProperties combatProperties = (EntityCombatProperties) loadData(GSON, rl, json, res == null || !res.getPackName().equals("main"), true);
					builderEntity.put(rl, combatProperties);
				}
				else if(rl.getPath().contains("/items/"))
				{
					GeneralCombatProperties combatProperties = loadData(GSON, rl, json, res == null || !res.getPackName().equals("main"), false);
					builderItem.put(rl, combatProperties);
				}
			}
			catch (Exception exception)
			{
				LOGGER.error("Couldn't parse elemental data {}", rl, exception);
			}
		});

		builderEntity.put(EntityCombatProperties.EMPTY_RESOURCELOCATION, new EntityCombatProperties());
		ImmutableMap<ResourceLocation, EntityCombatProperties> immutablemapEntity = builderEntity.build(); //this mapping contains attack and defense data and biomeDependency.

		builderItem.put(GeneralCombatProperties.EMPTY_RESOURCELOCATION, new GeneralCombatProperties());
		ImmutableMap<ResourceLocation, GeneralCombatProperties> immutablemapItem = builderItem.build(); //this mapping contains attack and defense data.

		this.registeredEntityData = immutablemapEntity;  
		this.registeredItemData = immutablemapItem;  
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
	private GeneralCombatProperties loadData(Gson gson, ResourceLocation name, JsonElement data, boolean custom, boolean isEntity)
	{
		Deque<CombatPropertiesContext> que = dataContext.get();
		if (que == null)
		{
			que = Queues.newArrayDeque();
			dataContext.set(que);
		}

		GeneralCombatProperties ret = null;
		try
		{
			que.push(new CombatPropertiesContext(name, custom));
			if (isEntity) {
				ret = gson.fromJson(data, EntityCombatProperties.class);
			} else {
				ret = gson.fromJson(data, GeneralCombatProperties.class); 
			}
			que.pop();
		}
		catch (JsonParseException e)
		{
			que.pop();
			throw e;
		}

		if (!custom)
		{
			CombatPropertiesLoadEvent event = new CombatPropertiesLoadEvent(name, ret, this);
			if (MinecraftForge.EVENT_BUS.post(event))
			{
				ret = EntityCombatProperties.EMPTY;
			}
			ret = event.getEntityData();
		}

		//if (ret != null)
		//    ret.freeze();

		return ret;
	}

	public EntityCombatProperties getEntityDataFromLocation(ResourceLocation rl)
	{
		return this.registeredEntityData.getOrDefault(rl, EntityCombatProperties.EMPTY);
	}

	public GeneralCombatProperties getItemDataFromLocation(ResourceLocation rl)
	{
		return this.registeredItemData.getOrDefault(rl, GeneralCombatProperties.EMPTY);
	}
}