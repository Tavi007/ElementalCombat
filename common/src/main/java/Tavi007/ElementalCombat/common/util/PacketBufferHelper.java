package Tavi007.ElementalCombat.common.util;

import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.Map;

public class PacketBufferHelper {

    public static void writeMap(FriendlyByteBuf buf, Map<String, Integer> map) {
        buf.writeInt(map.size());
        map.forEach((key, value) -> {
            buf.writeInt(value);
            buf.writeUtf(key);
        });

    }

    public static Map<String, Integer> readMap(FriendlyByteBuf buf) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            int value = buf.readInt();
            String key = buf.readUtf();
            map.put(key, value);
        }
        return map;
    }
}
