package Tavi007.ElementalCombat.common.data.datapack;

import Tavi007.ElementalCombat.common.api.data.DefenseMap;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Map;

public class DefenseMapAdapater implements JsonSerializer<DefenseMap>, JsonDeserializer<DefenseMap> {

    private static final Type type = new TypeToken<Map<String, Integer>>() {
    }.getType();

    @Override
    public JsonElement serialize(@NotNull DefenseMap map, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(map.getMap());
    }

    @Override
    public DefenseMap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Map<String, Integer> map = context.deserialize(json, type);
        return new DefenseMap(map);
    }
}
