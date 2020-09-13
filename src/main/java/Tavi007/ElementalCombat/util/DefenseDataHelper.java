package Tavi007.ElementalCombat.util;

import java.util.HashMap;
import java.util.Map;

import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.enchantments.CombatEnchantments;
import net.minecraft.enchantment.Enchantment;

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
	
	public static DefenseData getEnchantmentData(Map<Enchantment, Integer> enchantments) {
		HashMap<String, Integer> defStyle = new HashMap<String, Integer>();
		HashMap<String, Integer> defElement = new HashMap<String, Integer>();
		enchantments.forEach((key, level) -> {
			//currently only comparing strings.
			//maybe change to resourceLocation later, so other mods can interact with this as well.
			
			// elemental enchantments
			if(key.getName() == CombatEnchantments.ICE_RESISTANCE.getName()) {
				defElement.put("ice", level*scaleEnchantment);
				defElement.put("fire", -level*scaleEnchantment);
			}
			else if(key.getName() == CombatEnchantments.FIRE_RESISTANCE.getName()) {
				defElement.put( "fire", level*scaleEnchantment);
				defElement.put( "ice", -level*scaleEnchantment);
			}
			else if(key.getName() == CombatEnchantments.WATER_RESISTANCE.getName()) {
				defElement.put( "water", level*scaleEnchantment);
				defElement.put( "thunder", -level*scaleEnchantment);
			}
			else if(key.getName() == CombatEnchantments.THUNDER_RESISTANCE.getName()) {
				defElement.put( "thunder", level*scaleEnchantment);
				defElement.put( "water", -level*scaleEnchantment);
			}

			// style enchantments
			if(key.getName() == CombatEnchantments.BLAST_PROTECTION.getName()) {
				defStyle.put("explosion", level*scaleEnchantment);
			}
			else if(key.getName() == CombatEnchantments.PROJECTILE_PROTECTION.getName()) {
				defStyle.put("projectile", level*scaleEnchantment);
			}
		});
		return new DefenseData(defStyle, defElement);
	}
	
	public static float getScaling(HashMap<String, Integer> map, String key) {
		Integer factor = map.getOrDefault(key, 0);
		return 1.0f - getPercentage(factor);
	}
	
	public static float getPercentage(Integer factor) {
		return ((float) factor)/maxFactor;
	}
}
