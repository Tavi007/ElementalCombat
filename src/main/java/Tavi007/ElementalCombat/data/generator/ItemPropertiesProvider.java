package Tavi007.ElementalCombat.data.generator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.data.ElementalCombatProperties;
import Tavi007.ElementalCombat.data.generator.KeyWordsMapper.KeyWordsMapperType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemPropertiesProvider extends AbstractCombatPropertiesProvider implements IDataProvider {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;

    public ItemPropertiesProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(DirectoryCache cache) throws IOException {
        Path path = this.generator.getOutputFolder();
        Map<ResourceLocation, ElementalCombatProperties> map = Maps.newHashMap();

        ForgeRegistries.ITEMS.forEach(item -> {
            if (!(item instanceof BlockItem)) {

                ResourceLocation itemRl = item.getRegistryName();

                if (!(itemRl.getNamespace().equals("minecraft") || itemRl.getNamespace().equals(ElementalCombat.MOD_ID))) {
                    System.out.println(itemRl);
                }

                String attackStyle = getFirstMappedKeywordsFromName(itemRl, KeyWordsMapperType.STYLE);
                String attackElement = getFirstMappedKeywordsFromName(itemRl, KeyWordsMapperType.ELEMENT);

                HashMap<String, Integer> defenseStyle = null;
                HashMap<String, Integer> defenseElement = null;

                if (attackStyle != null || attackElement != null || defenseStyle != null || defenseElement != null) {
                    ElementalCombatProperties property = new ElementalCombatProperties(defenseStyle, defenseElement, attackStyle, attackElement);
                    map.put(itemRl, property);

                }
            }
        });

        map.forEach((itemRl, property) -> {
            Path finalPath = createPath(path, itemRl, "items");
            JsonElement json = GSON.toJsonTree(property);
            try {
                IDataProvider.save(GSON, cache, json, finalPath);
            } catch (IOException ioexception) {
                ElementalCombat.LOGGER.error("Couldn't save item property {}", finalPath, ioexception);
            }
        });
    }

    @Override
    public String getName() {
        return ElementalCombat.MOD_ID + " item provider";
    }

}
