package Tavi007.ElementalCombat.loading;

import java.util.HashSet;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;
import com.google.gson.JsonParseException;

import net.minecraft.resources.ResourceLocation;

public class CombatPropertiesContext {

    public final ResourceLocation name;
    private final boolean vanilla;
    public int poolCount = 0;
    public int entryCount = 0;
    private HashSet<String> entryNames = Sets.newHashSet();

    public CombatPropertiesContext(ResourceLocation name) {
        this.name = name;
        this.vanilla = "minecraft".equals(this.name.getNamespace());
    }

    public String validateEntryName(@Nullable String name) {
        if (name != null && !this.entryNames.contains(name)) {
            this.entryNames.add(name);
            return name;
        }

        if (!this.vanilla)
            throw new JsonParseException("Elemental Data \"" + this.name.toString() + "\" Duplicate entry name \"" + name + "\" for pool #"
                + (this.poolCount - 1) + " entry #" + (this.entryCount - 1));

        int x = 0;
        while (this.entryNames.contains(name + "#" + x))
            x++;

        name = name + "#" + x;
        this.entryNames.add(name);

        return name;
    }
}
