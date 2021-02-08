package Tavi007.ElementalCombat.util;

import java.util.HashMap;

import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.init.EnchantmentList;
import net.minecraft.enchantment.Enchantments;

public class DefenseDataHelper {

	/**
	 * Computes the {@link DefenseData} for enchantments. 
	 * Depending on the {@link ServerConfig}, the resulting DefenseData will use emojies or not.
	 * @param enchantments A map with the enchantment names and their level.
	 * @return The DefenseData of all the enchantment summed up.
	 */
	public static DefenseData getEnchantmentData(HashMap<String, Integer> enchantments) {
		HashMap<String, Integer> defElement = new HashMap<String, Integer>();
		HashMap<String, Integer> defStyle = new HashMap<String, Integer>();
		enchantments.forEach( (key, level) -> {
			if (level != 0) {
				// elemental enchantments
				if(key == Enchantments.FIRE_PROTECTION.getName()) {
					defElement.put( "fire", level*ServerConfig.getEnchantmentScaling());
					defElement.put( "ice", -level*ServerConfig.getEnchantmentScaling()/2);
				}
				else if(key == EnchantmentList.ICE_PROTECTION.get().getName()) {
					defElement.put("ice", level*ServerConfig.getEnchantmentScaling());
					defElement.put("fire", -level*ServerConfig.getEnchantmentScaling()/2);
				}
				else if(key == EnchantmentList.WATER_PROTECTION.get().getName()) {
					defElement.put( "water", level*ServerConfig.getEnchantmentScaling());
					defElement.put( "thunder", -level*ServerConfig.getEnchantmentScaling()/2);
				}
				else if(key == EnchantmentList.THUNDER_PROTECTION.get().getName()) {
					defElement.put( "thunder", level*ServerConfig.getEnchantmentScaling());
					defElement.put( "water", -level*ServerConfig.getEnchantmentScaling()/2);
				}
				else if(key == EnchantmentList.DARKNESS_PROTECTION.get().getName()) {
					defElement.put( "darkness", level*ServerConfig.getEnchantmentScaling());
					defElement.put( "light", -level*ServerConfig.getEnchantmentScaling()/2);
				}
				else if(key == EnchantmentList.LIGHT_PROTECTION.get().getName()) {
					defElement.put( "light", level*ServerConfig.getEnchantmentScaling());
					defElement.put( "darkness", -level*ServerConfig.getEnchantmentScaling()/2);
				}
				else if(key == EnchantmentList.ELEMENT_PROTECTION.get().getName()) {
					defElement.put( "fire", level*ServerConfig.getEnchantmentScaling()/5);
					defElement.put( "water", level*ServerConfig.getEnchantmentScaling()/5);
					defElement.put( "ice", level*ServerConfig.getEnchantmentScaling()/5);
					defElement.put( "thunder", level*ServerConfig.getEnchantmentScaling()/5);
				}

				// style enchantments
				if(key == Enchantments.BLAST_PROTECTION.getName()) {
					defStyle.put("explosion", level*ServerConfig.getEnchantmentScaling());
				}
				else if(key == Enchantments.PROJECTILE_PROTECTION.getName()) {
					defStyle.put("projectile", level*ServerConfig.getEnchantmentScaling());
				}
				else if(key == EnchantmentList.MAGIC_PROTECTION.get().getName()) {
					defStyle.put("magic", level*ServerConfig.getEnchantmentScaling());
				}
			}
		});
		return new DefenseData(defStyle, defElement);
	}


	/**
	 * Merges an additional Map into the base map. If both maps contains the same key, the highest value will persist.
	 * @param baseMap The base mapping. Additional values will be written into this one.
	 * @param additionalMap The additional mapping.
	 */
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
	
	/**
	 * Merges an additional Map into the base map. If both maps contains the same key, the value will be summed up.
	 * @param baseMap The base mapping. Additional values will be written into this one.
	 * @param additionalMap The additional mapping.
	 */
	public static void sumMaps(HashMap<String, Integer> baseMap, HashMap<String, Integer> additionalMap){
		additionalMap.forEach((key, value)->{
			if(!baseMap.containsKey(key)) {
				baseMap.put(key, value);
			}
			else {
				int newValue = baseMap.get(key) + value;
				if (newValue == 0) {
					baseMap.remove(key);
				}
				else{
					baseMap.put(key, newValue);
				}
			}
		});	
	}
	
	/**
	 * Merges an additional Map into the base map. The additional values will be subtracted from the base map.
	 * @param baseMap The base mapping. The additional value will be written into this one.
	 * @param additionalMap The additional mapping.
	 */
	public static void substractMaps(HashMap<String, Integer> baseMap, HashMap<String, Integer> additionalMap){
		additionalMap.forEach((key, value)->{
			if(!baseMap.containsKey(key)) {
				baseMap.put(key, -value);
			}
			else {
				int newValue = baseMap.get(key) - value;
				if (newValue == 0) {
					baseMap.remove(key);
				}
				else{
					baseMap.put(key, newValue);
				}
			}
		});	
	}

	/**
	 * Computes the scaling factor for giving defense map and given key.
	 * @param map Should either be the element or style defense mapping.
	 * @param key The key, which should be checked for.
	 * @return The resulting scaling factor. It can be any decimal number.
	 */
	public static float getScaling(HashMap<String, Integer> map, String key) {
		Integer factor = map.getOrDefault(key, 0);
		return 1.0f - getPercentage(factor);
	}

	/**
	 * Computes the reduction percentage of any given factor. 
	 * The resulting value also depends on the maximal Factor, which is defined in {@link ServerConfig}.
	 * @param factor The value from the element or style defense mapping.
	 * @return A percentage, which represents the reduction of the damage. If the percentage is greater than 1, then the damage will be absorbed.
	 */
	public static float getPercentage(Integer factor) {
		return ((float) factor)/ServerConfig.getMaxFactor();
	}
}
