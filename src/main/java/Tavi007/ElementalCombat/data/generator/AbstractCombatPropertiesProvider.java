package Tavi007.ElementalCombat.data.generator;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Tavi007.ElementalCombat.data.generator.KeyWordsMapper.KeyWordsMapperType;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractCombatPropertiesProvider {

    protected String getFirstMappedKeywordsFromName(ResourceLocation rl, KeyWordsMapperType type) {
        List<String> mappedKeywords = getMappedKeywordsFromName(rl, type);
        String ret = null;
        if (mappedKeywords.size() != 0) {
            ret = mappedKeywords.get(0);
        }
        return ret;
    }

    protected List<String> getMappedKeywordsFromName(ResourceLocation rl, KeyWordsMapperType type) {
        List<String> mappedKeywords = new ArrayList<>();
        getKeywordsFromName(rl).forEach(keyword -> {
            String match = KeyWordsMapper.findMatch(type, keyword);
            if (match != null) {
                mappedKeywords.add(match);
            }
        });
        return mappedKeywords;
    }

    private List<String> getKeywordsFromName(ResourceLocation rl) {
        return Arrays.asList(rl.getPath().split("_"));
    }

    protected Path createPath(Path path, ResourceLocation rl, String type) {
        return path.resolve("data/" +
            rl.getNamespace() +
            "/elemental_combat_properties/" +
            type +
            "/" +
            rl.getPath() +
            ".json");
    }

}
