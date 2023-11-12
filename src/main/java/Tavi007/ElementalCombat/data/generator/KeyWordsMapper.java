package Tavi007.ElementalCombat.data.generator;

import java.util.Arrays;
import java.util.List;

public enum KeyWordsMapper {

    NORMAL(KeyWordsMapperType.ELEMENT),
    FIRE(KeyWordsMapperType.ELEMENT, "fire", "lava", "heat"),
    ICE(KeyWordsMapperType.ELEMENT, "ice", "frost", "frozen"),
    THUNDER(KeyWordsMapperType.ELEMENT, "thunder"),
    WATER(KeyWordsMapperType.ELEMENT, "water"),
    EARTH(KeyWordsMapperType.ELEMENT, "earth"),
    WIND(KeyWordsMapperType.ELEMENT, "wind"),
    LIGHT(KeyWordsMapperType.ELEMENT, "light"),
    DARKNESS(KeyWordsMapperType.ELEMENT, "darkness"),
    FLORA(KeyWordsMapperType.ELEMENT, "flora"),

    HIT(KeyWordsMapperType.STYLE),
    SLASH(KeyWordsMapperType.STYLE),
    STAB(KeyWordsMapperType.STYLE),
    PROJECTILE(KeyWordsMapperType.STYLE),
    EXPLOSION(KeyWordsMapperType.STYLE),
    MAGIC(KeyWordsMapperType.STYLE),
    ENVIRONMENT(KeyWordsMapperType.STYLE);

    private KeyWordsMapperType type;
    private List<String> inputs;

    private KeyWordsMapper(KeyWordsMapperType type, String... inputs) {
        this.type = type;
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
