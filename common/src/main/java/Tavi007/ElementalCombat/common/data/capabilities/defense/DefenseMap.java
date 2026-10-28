package Tavi007.ElementalCombat.common.data.capabilities.defense;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public record DefenseMap(Map<String, Integer> map) {

//    public static final Codec<DefenseMap> CODEC = RecordCodecBuilder.create(
//            instance -> instance.group(
//                    Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("map").forGetter((DefenseMap defenseMap) -> defenseMap.map)
//            ).apply(instance, new Function<Map<String, Integer>, DefenseMap>() {
//
//                @Override
//                public DefenseMap apply(Map<String, Integer> map) {
//                    return new DefenseMap(map);
//                }
//            })
//    );

    public DefenseMap() {
        this(new HashMap<String, Integer>());
    }

    public static DefenseMap read(FriendlyByteBuf buf) {
        Map<String, Integer> map = buf.readMap(FriendlyByteBuf::readUtf, FriendlyByteBuf::readInt);
        return new DefenseMap(map);
    }

    public static void write(DefenseMap map, FriendlyByteBuf buf) {
        buf.writeMap(map.map, FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeInt);
    }

    public static CompoundTag writeTag(DefenseMap map) {
        CompoundTag tag = new CompoundTag();
        map.map.forEach(tag::putInt);
        return tag;
    }

    public static DefenseMap readTag(CompoundTag tag) {
        Map<String, Integer> map = new HashMap<>();
        for (String key : tag.getAllKeys()) {
            map.put(key, tag.getInt(key));
        }
        return new DefenseMap(map);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DefenseMap other) {
            Set<String> keysThis = this.map.keySet();
            Set<String> keysOther = other.map.keySet();
            if (keysThis.size() == keysOther.size()) {
                for (String keyThis : keysThis) {
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
     * Adds the value from the other map (Creating the sum).
     *
     * @param other The other DefenseMap.
     */
    public void add(DefenseMap other) {
        this.add(other.map);
    }

    public void add(Map<String, Integer> otherMap) {
        if (otherMap != null) {
            if (!otherMap.isEmpty()) {
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
     * Subtracts the values of the other map.
     *
     * @param other The other DefenseMap.
     */
    public void subtract(DefenseMap other) {
        this.subtract(other.map);
    }

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
     * Put the value from the other map into the base map.
     * If both maps contain the same key, the higher value will be set.
     *
     * @param other The other DefenseMap.
     */
    public void mergeMax(DefenseMap other) {
        this.mergeMax(other.map);
    }

    public void mergeMax(Map<String, Integer> otherMap) {
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

    /**
     * Put the value from the other map into the base map.
     * If both maps contain the same key, the lower value will be set.
     *
     * @param other The other DefenseMap.
     */
    public void mergeMin(DefenseMap other) {
        this.mergeMin(other.map);
    }

    public void mergeMin(Map<String, Integer> otherMap) {
        if (otherMap != null) {
            otherMap.forEach((key, value) -> {
                if (!map.containsKey(key)) {
                    map.put(key, value);
                } else if (map.get(key) < value) {
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

    public boolean isValid() {
        return map != null;
    }
}
