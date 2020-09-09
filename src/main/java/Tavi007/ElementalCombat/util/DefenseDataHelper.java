package Tavi007.ElementalCombat.util;

import java.util.HashMap;

public class DefenseDataHelper {
	private static Integer maxFactor = 25;
	public static Integer scaleEnchantment = (int) Math.round(maxFactor/12.5);
	
	
	//merge the two maps, so the highest value will persist.
	public static HashMap<String, Integer> mergeMaps(HashMap<String, Integer> baseMap, HashMap<String, Integer> additionalMap){
		additionalMap.forEach((key, value)->{
			if(!baseMap.containsKey(key)) {
				baseMap.put(key, value);
			}
			else if(baseMap.get(key) > value) {
				baseMap.put(key, value);
			}
		});	
		return baseMap;
	}

	//merge the two maps, so the values of the same key get summed up.
	public static HashMap<String, Integer> sumMaps(HashMap<String, Integer> baseMap, HashMap<String, Integer> additionalMap){
		additionalMap.forEach((key, value)->{
			if(!baseMap.containsKey(key)) {
				baseMap.put(key, value);
			}
			else {
				baseMap.put(key, value + baseMap.get(key));
			}
		});	
		return baseMap;
	}
	
	public static float getScaling(HashMap<String, Integer> map, String key) {
		Integer factor = map.getOrDefault(key, 0);
		return 1.0f - getPercentage(factor);
	}
	
	public static float getPercentage(Integer factor) {
		return ((float) factor)/maxFactor;
	}
}
