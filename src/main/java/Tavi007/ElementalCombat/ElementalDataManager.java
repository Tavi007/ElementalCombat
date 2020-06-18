package Tavi007.ElementalCombat;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.ValidationTracker;

public class ElementalDataManager extends JsonReloadListener 
{
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	private static final Logger LOGGER = LogManager.getLogger();
	private Map<ResourceLocation, ElementalData> registeredElementalData = ImmutableMap.of();
    private static ThreadLocal<Deque<ElementalDataContext>> elemDataContext = new ThreadLocal<Deque<ElementalDataContext>>();
   
	public ElementalDataManager() 
	{
		super(GSON, "elementalcombat");
	}
	
	protected void apply(Map<ResourceLocation, JsonObject> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) 
	{
		Builder<ResourceLocation, ElementalData> builder = ImmutableMap.builder();
		JsonObject jsonobject = objectIn.remove(ElementalData.EMPTY);
	    if (jsonobject != null) 
	    {
	         LOGGER.warn("Datapack tried to redefine {} elemental entity data, ignoring", (Object)ElementalData.EMPTY);
	    }
	    
	    objectIn.forEach((rl, json) -> 
	    {
	    	System.out.println(rl.getPath().toString());
	       try (net.minecraft.resources.IResource res = resourceManagerIn.getResource(getPreparedPath(rl));)
	       {
	    	   
	    	   ElementalData elementalData = loadElementalData(GSON, rl, json, res == null || !res.getPackName().equals("Default"));
	    	   //copied from LootTableManager:
	    	   //LootTable loottable = net.minecraftforge.common.ForgeHooks.loadLootTable(GSON_INSTANCE, rl, json, res == null || !res.getPackName().equals("Default"), this);
	    	   
	    	   builder.put(rl, elementalData);
	       }
	       catch (Exception exception)
	       {
	           LOGGER.error("Couldn't parse elemental entity data {}", rl, exception);
	       }
	    });
	    
	    builder.put(ElementalData.EMPTY, new ElementalData());
	    ImmutableMap<ResourceLocation, ElementalData> immutablemap = builder.build(); //this mapping contains attack and defense data.
	    
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

	    
	    this.registeredElementalData = immutablemap;  
	}
	   
    public static JsonElement toJson(ElementalData elementalData)
    {
	    return GSON.toJsonTree(elementalData);
	}

	public Set<ResourceLocation> getElementalDataKeys() 
	{
	    return this.registeredElementalData.keySet();
	}
	
	@Nullable
    private static ElementalData loadElementalData(Gson gson, ResourceLocation name, JsonObject data, boolean custom)
    {
        Deque<ElementalDataContext> que = elemDataContext.get();
        if (que == null)
        {
            que = Queues.newArrayDeque();
            elemDataContext.set(que);
        }

        ElementalData ret = null;
        try
        {
            que.push(new ElementalDataContext(name, custom));
            ret = gson.fromJson(data, ElementalData.class);
            que.pop();
        }
        catch (JsonParseException e)
        {
            que.pop();
            throw e;
        }

        //if (!custom)
        //    ret = ForgeEventFactory.loadLootTable(name, ret, lootTableManager);

        //if (ret != null)
        //    ret.freeze();

        return ret;
    }
	
	public Map<ResourceLocation, ElementalData> getRegisteredElementalData()
	{
		return this.registeredElementalData;
	}
}