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

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.events.DataLoadEvent;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class DataManager extends JsonReloadListener 
{
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	private static final Logger LOGGER = LogManager.getLogger();
	private Map<ResourceLocation, EntityData> registeredEntityData = ImmutableMap.of();
	private Map<ResourceLocation, GeneralData> registeredItemData = ImmutableMap.of();
	private static ThreadLocal<Deque<DataContext>> dataContext = new ThreadLocal<Deque<DataContext>>();

	public DataManager() 
	{
		super(GSON, ElementalCombat.MOD_ID);
	}

	protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) 
	{
		Builder<ResourceLocation, EntityData> builderEntity = ImmutableMap.builder();
		Builder<ResourceLocation, GeneralData> builderItem = ImmutableMap.builder();

		JsonElement jsonobject = objectIn.remove(EntityData.EMPTY_RESOURCELOCATION);
		if (jsonobject != null) 
		{
			LOGGER.warn("Datapack tried to redefine {} elemental entity data, ignoring", (Object)EntityData.EMPTY_RESOURCELOCATION);
		}

		objectIn.forEach((rl, json) -> 
		{
			try (net.minecraft.resources.IResource res = resourceManagerIn.getResource(getPreparedPath(rl));)
			{
				//check if entity or item gets loaded
				if(rl.getPath().contains("/entities/"))
				{
					EntityData elementalData = (EntityData) loadData(GSON, rl, json, res == null || !res.getPackName().equals("main"), true);
					builderEntity.put(rl, elementalData);
				}
				else if(rl.getPath().contains("/items/"))
				{
					GeneralData elementalData = loadData(GSON, rl, json, res == null || !res.getPackName().equals("main"), false);
					builderItem.put(rl, elementalData);
				}
			}
			catch (Exception exception)
			{
				LOGGER.error("Couldn't parse elemental data {}", rl, exception);
			}
		});

		builderEntity.put(EntityData.EMPTY_RESOURCELOCATION, new EntityData());
		ImmutableMap<ResourceLocation, EntityData> immutablemapEntity = builderEntity.build(); //this mapping contains attack and defense data and biomeDependency.

		builderItem.put(GeneralData.EMPTY_RESOURCELOCATION, new GeneralData());
		ImmutableMap<ResourceLocation, GeneralData> immutablemapItem = builderItem.build(); //this mapping contains attack and defense data.

		//validation of immutablemap missing
		//copied from LootTable:
		//ValidationTracker validationtracker = new ValidationTracker(LootParameterSets.GENERIC, this.field_227507_d_::func_227517_a_, immutablemap::get);
		//immutablemap.forEach((rl, json) -> 
		//{
		//   func_227508_a_(validationtracker, rl, json);
		//});
		//validationtracker.getProblems().forEach((rl, json) -> 
		//{
		//   LOGGER.warn("Found validation problem in " + rl + ": " + json);
		//});


		this.registeredEntityData = immutablemapEntity;  
		this.registeredItemData = immutablemapItem;  
	}

	public static JsonElement toJson(EntityData elementalData)
	{
		return GSON.toJsonTree(elementalData);
	}

	public Set<ResourceLocation> getEntityDataKeys() 
	{
		return this.registeredEntityData.keySet();
	}

	@Nullable
	private GeneralData loadData(Gson gson, ResourceLocation name, JsonElement data, boolean custom, boolean isEntity)
	{
		Deque<DataContext> que = dataContext.get();
		if (que == null)
		{
			que = Queues.newArrayDeque();
			dataContext.set(que);
		}

		GeneralData ret = null;
		try
		{
			que.push(new DataContext(name, custom));
			if (isEntity) {
				ret = gson.fromJson(data, EntityData.class);
			} else {
				ret = gson.fromJson(data, GeneralData.class); 
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
			DataLoadEvent event = new DataLoadEvent(name, ret, this);
			if (MinecraftForge.EVENT_BUS.post(event))
			{
				ret = EntityData.EMPTY;
			}
			ret = event.getEntityData();
		}

		//if (ret != null)
		//    ret.freeze();

		return ret;
	}

	public EntityData getEntityDataFromLocation(ResourceLocation rl)
	{
		return this.registeredEntityData.getOrDefault(rl, EntityData.EMPTY);
	}

	public GeneralData getItemDataFromLocation(ResourceLocation rl)
	{
		return this.registeredItemData.getOrDefault(rl, GeneralData.EMPTY);
	}
}