package Tavi007.ElementalCombat.common.util;

import java.util.HashMap;

public class MapHelper {

    public static HashMap<String, Integer> getDeepcopy(HashMap<String, Integer> base) {
        HashMap<String, Integer> ret = new HashMap<>();
        base.forEach((key, value) -> {
            ret.put(new String(key), Integer.valueOf(value));
        });
        return ret;
    }
}
