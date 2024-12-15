package Tavi007.ElementalCombat.common.api.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class DefenseMap {

    public static final Codec<DefenseMap> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("map").forGetter((DefenseMap defenseMap) -> defenseMap.map)
            ).apply(instance, new Function<Map<String, Integer>, DefenseMap>() {

                @Override
                public DefenseMap apply(Map<String, Integer> map) {
                    return new DefenseMap(map);
                }
            })
    );

    private final Map<String, Integer> map;

    public DefenseMap(Map<String, Integer> map) {
        this.map = map;
    }

    public DefenseMap() {
        this.map = new HashMap<String, Integer>();
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DefenseMap other) {
            Set<String> keysThis = this.map.keySet();
            Set<String> keysOther = other.map.keySet();
            if (keysThis.size() == keysOther.size()) {
                Iterator<String> iteratorThis = keysThis.iterator();
                while (iteratorThis.hasNext()) {
                    String keyThis = iteratorThis.next();
                    if (!this.map.get(keyThis).equals(other.map.get(keyThis))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Merges an additional Map into the base map. If both maps contains the same key, the value will be summed up.
     *
     * @param baseMap       The base mapping. Additional values will be written into this one.
     * @param additionalMap The additional mapping.
     */
    public void add(Map<String, Integer> otherMap) {
        if (otherMap != null) {
            if (otherMap.size() != 0) {
                int a = 0;
            }
            otherMap.forEach((key, value) -> {
                if (!map.containsKey(key)) {
                    map.put(key, value);
                } else {
                    int newValue = map.get(key) + value;
                    if (newValue == 0) {
                        map.remove(key);
                    } else {
                        map.put(key, newValue);
                    }
                }
            });
        }
    }

    /**
     * Merges an additional Map into the base map. If both maps contains the same key, the value will be summed up.
     *
     * @param baseMap       The base mapping. Additional values will be written into this one.
     * @param additionalMap The additional mapping.
     */
    public void subtract(Map<String, Integer> otherMap) {
        if (otherMap != null) {
            otherMap.forEach((key, value) -> {
                if (!map.containsKey(key)) {
                    map.put(key, -value);
                } else {
                    int newValue = map.get(key) - value;
                    if (newValue == 0) {
                        map.remove(key);
                    } else {
                        map.put(key, newValue);
                    }
                }
            });
        }
    }

    /**
     * Merges an additional Map into the base map. If both maps contains the same key, the highest value will persist.
     *
     * @param baseMap       The base mapping. Additional values will be written into this one.
     * @param additionalMap The additional mapping.
     */
    public void merge(Map<String, Integer> otherMap) {
        if (otherMap != null) {
            otherMap.forEach((key, value) -> {
                if (!map.containsKey(key)) {
                    map.put(key, value);
                } else if (map.get(key) > value) {
                    map.put(key, value);
                }
            });
        }
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public String toString() {
        return map.toString();
    }
}
