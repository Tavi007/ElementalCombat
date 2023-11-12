package Tavi007.ElementalCombat.data.generator;

import java.nio.file.Path;

import Tavi007.ElementalCombat.data.generator.KeyWordsMapper.KeyWordsMapperType;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractCombatPropertiesProvider {

    protected String getKeywordFromName(ResourceLocation rl, KeyWordsMapperType type) {
        return KeyWordsMapper.findMatch(type, rl.getPath());
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
