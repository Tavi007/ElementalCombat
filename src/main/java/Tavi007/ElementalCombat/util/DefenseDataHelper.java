package Tavi007.ElementalCombat.util;

import java.util.HashMap;

public class DefenseDataHelper {
	private static Integer maxFactor = 25;
	public static Integer scaleEnchantment = (int) Math.round(maxFactor/12.5);
	
	
	//merge the @param additionalMap into the @param baseMap, so the highest value persists.
	public static void mergeMaps(HashMap<String, Integer> baseMap, HashMap<String, Integer> additionalMap){
		additionalMap.forEach((key, value)->{
			if(!baseMap.containsKey(key)) {
				baseMap.put(key, value);
			}
			else if(baseMap.get(key) > value) {
				baseMap.put(key, value);
			}
		});	
	}

	//merge the @param additionalMap into the @param baseMap, so the values of the same key get summed up.
	public static void sumMaps(HashMap<String, Integer> baseMap, HashMap<String, Integer> additionalMap){
		additionalMap.forEach((key, value)->{
			if(!baseMap.containsKey(key)) {
				baseMap.put(key, value);
			}
			else {
				baseMap.put(key, value + baseMap.get(key));
			}
		});	
	}
	
	public static float getScaling(HashMap<String, Integer> map, String key) {
		Integer factor = map.getOrDefault(key, 0);
		return 1.0f - getPercentage(factor);
	}
	
	public static float getPercentage(Integer factor) {
		return ((float) factor)/maxFactor;
	}
}
