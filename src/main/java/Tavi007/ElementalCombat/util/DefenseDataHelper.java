package Tavi007.ElementalCombat.util;

import java.util.HashMap;

import org.apache.commons.lang3.text.WordUtils;

import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.enchantments.CombatEnchantments;
import net.minecraft.util.text.TextFormatting;

@SuppressWarnings("deprecation")
public class DefenseDataHelper {

	public static DefenseData getEnchantmentData(HashMap<String, Integer> enchantments) {
		HashMap<String, Integer> defElement = new HashMap<String, Integer>();
		HashMap<String, Integer> defStyle = new HashMap<String, Integer>();
		enchantments.forEach( (key, level) -> {
			if (level != 0) {
				if(ServerConfig.isEmojiEnabled()) {
					// elemental enchantments
					if(key == CombatEnchantments.ICE_RESISTANCE.getName()) {
						defElement.put("❄", level*ServerConfig.getEnchantmentScaling());
						defElement.put("🔥", -level*ServerConfig.getEnchantmentScaling());
					}
					else if(key == CombatEnchantments.FIRE_RESISTANCE.getName()) {
						defElement.put( "🔥", level*ServerConfig.getEnchantmentScaling());
						defElement.put( "❄", -level*ServerConfig.getEnchantmentScaling());
					}
					else if(key == CombatEnchantments.WATER_RESISTANCE.getName()) {
						defElement.put( "💧", level*ServerConfig.getEnchantmentScaling());
						defElement.put( "⚡", -level*ServerConfig.getEnchantmentScaling());
					}
					else if(key == CombatEnchantments.THUNDER_RESISTANCE.getName()) {
						defElement.put( "⚡", level*ServerConfig.getEnchantmentScaling());
						defElement.put( "💧", -level*ServerConfig.getEnchantmentScaling());
					}

					// style enchantments
					if(key == CombatEnchantments.BLAST_PROTECTION.getName()) {
						defStyle.put("💣", level*ServerConfig.getEnchantmentScaling());
					}
					else if(key == CombatEnchantments.PROJECTILE_PROTECTION.getName()) {
						defStyle.put("➹", level*ServerConfig.getEnchantmentScaling());
					}
				}
				else {
					// elemental enchantments
					if(key == CombatEnchantments.ICE_RESISTANCE.getName()) {
						defElement.put("ice", level*ServerConfig.getEnchantmentScaling());
						defElement.put("fire", -level*ServerConfig.getEnchantmentScaling());
					}
					else if(key == CombatEnchantments.FIRE_RESISTANCE.getName()) {
						defElement.put( "fire", level*ServerConfig.getEnchantmentScaling());
						defElement.put( "ice", -level*ServerConfig.getEnchantmentScaling());
					}
					else if(key == CombatEnchantments.WATER_RESISTANCE.getName()) {
						defElement.put( "water", level*ServerConfig.getEnchantmentScaling());
						defElement.put( "thunder", -level*ServerConfig.getEnchantmentScaling());
					}
					else if(key == CombatEnchantments.THUNDER_RESISTANCE.getName()) {
						defElement.put( "thunder", level*ServerConfig.getEnchantmentScaling());
						defElement.put( "water", -level*ServerConfig.getEnchantmentScaling());
					}

					// style enchantments
					if(key == CombatEnchantments.BLAST_PROTECTION.getName()) {
						defStyle.put("explosion", level*ServerConfig.getEnchantmentScaling());
					}
					else if(key == CombatEnchantments.PROJECTILE_PROTECTION.getName()) {
						defStyle.put("projectile", level*ServerConfig.getEnchantmentScaling());
					}
				}
			}
		});
		return new DefenseData(defStyle, defElement);
	}



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

	//merge the @param additionalMap into the @param baseMap, so the values of the same key get summed up.
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

	public static float getScaling(HashMap<String, Integer> map, String key) {
		Integer factor = map.getOrDefault(key, 0);
		return 1.0f - getPercentage(factor);
	}

	public static float getPercentage(Integer factor) {
		return ((float) factor)/ServerConfig.getMaxFactor();
	}

	public static String toPercentageString(String key, Integer factor) {
		//get color
		Integer percentage = Math.round(DefenseDataHelper.getPercentage(factor)*100);
		TextFormatting textFormatting = TextFormatting.GRAY;
		if (percentage < 0) {textFormatting = TextFormatting.RED;}
		if (percentage > 0 && percentage < 100) {textFormatting = TextFormatting.BLUE;}
		if (percentage == 100) {textFormatting = TextFormatting.YELLOW;}
		if (percentage > 100) {textFormatting = TextFormatting.GREEN;}

		//make string
		return "" + TextFormatting.GRAY + " - " + WordUtils.capitalize(key) + " " + textFormatting + String.valueOf(percentage)+ "%" + TextFormatting.RESET;
	}
}
