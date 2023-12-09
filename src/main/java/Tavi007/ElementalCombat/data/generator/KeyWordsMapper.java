package Tavi007.ElementalCombat.data.generator;

import java.util.Arrays;
import java.util.List;

public enum KeyWordsMapper {

    NORMAL(KeyWordsMapperType.ELEMENT, null),
    FIRE(KeyWordsMapperType.ELEMENT, "ice", "fire", "lava", "heat"),
    ICE(KeyWordsMapperType.ELEMENT, "fire", "ice", "frost", "frozen"),
    THUNDER(KeyWordsMapperType.ELEMENT, "water", "thunder"),
    WATER(KeyWordsMapperType.ELEMENT, "thunder", "water", "trident", "turtle"),
    EARTH(KeyWordsMapperType.ELEMENT, "wind", "earth", "ground"),
    WIND(KeyWordsMapperType.ELEMENT, "earth", "wind"),
    LIGHT(KeyWordsMapperType.ELEMENT, "darkness", "light", "sparkling", "sparkle"),
    DARKNESS(KeyWordsMapperType.ELEMENT, "light", "darkness"),
    FLORA(KeyWordsMapperType.ELEMENT, null, "flora"),

    HIT(KeyWordsMapperType.STYLE, null, "glove"),
    SLASH(KeyWordsMapperType.STYLE, null, "sword", "axe"),
    STAB(KeyWordsMapperType.STYLE, null, "pickaxe"),
    PROJECTILE(KeyWordsMapperType.STYLE, null, "arrow"),
    EXPLOSION(KeyWordsMapperType.STYLE, null, "tnt", "bomb", "exploding"),
    MAGIC(KeyWordsMapperType.STYLE, null, "potion", "magic"),
    ENVIRONMENT(KeyWordsMapperType.STYLE, null);

    private KeyWordsMapperType type;
    private String opposite;
    private List<String> inputs;

    private KeyWordsMapper(KeyWordsMapperType type, String opposite, String... inputs) {
        this.type = type;
        this.opposite = opposite;
        this.inputs = Arrays.asList(inputs);
    }

    private boolean canMap(KeyWordsMapperType type, String input) {
        return this.type.equals(type) && inputs.contains(input);
    }

    public static String findMatch(KeyWordsMapperType type, String input) {
        for (KeyWordsMapper mapper : KeyWordsMapper.values()) {
            if (mapper.canMap(type, input)) {
                return mapper.name().toLowerCase();
            }
        }
        return null;
    }

    public enum KeyWordsMapperType {
        ELEMENT,
        STYLE;
    }
}
