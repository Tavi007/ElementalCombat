package Tavi007.ElementalCombat.util;

import java.util.HashMap;

public class MapHelper {

    public static HashMap<String, Integer> getDeepcopy(HashMap<String, Integer> base) {
        HashMap<String, Integer> ret = new HashMap<>();
        base.forEach((key, value) -> {
            ret.put(new String(key), new Integer(value));
        });
        return ret;
    }
}
