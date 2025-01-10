package Tavi007.ElementalCombat.common.data;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class CombatPropertiesManager extends SimpleJsonResourceReloadListener {

    public CombatPropertiesManager() {
        super(DatapackDataAccessor.GSON, "elemental_combat_properties");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        DatapackDataAccessor.resetDatapackData(objectIn);
    }
}
