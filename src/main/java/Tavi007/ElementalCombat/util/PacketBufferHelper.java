package Tavi007.ElementalCombat.util;

import java.util.HashMap;

import net.minecraft.network.FriendlyByteBuf;

public class PacketBufferHelper {

    public static void writeHashMap(FriendlyByteBuf buf, HashMap<String, Integer> map) {
        buf.writeInt(map.size());
        map.forEach((key, value) -> {
            buf.writeInt(value);
            buf.writeUtf(key);
        });

    }

    public static HashMap<String, Integer> readHashMap(FriendlyByteBuf buf) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            int value = buf.readInt();
            String key = buf.readUtf();
            map.put(key, value);
        }
        return map;
    }
}
