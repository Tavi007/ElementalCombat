package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import org.apache.commons.lang3.text.WordUtils;

@SuppressWarnings("deprecation")
public class CombatPropertiesTextMapping {
	private final HashMap<String, String> mapping;

	public CombatPropertiesTextMapping() {
		this.mapping = new HashMap<String, String>();
	}

	public CombatPropertiesTextMapping(HashMap<String, String> mapping) {
		this.mapping = mapping;
	}
	
	public String getValue(String key) {
		String value = mapping.get(key);
		if(value == null) {
			value = WordUtils.capitalize(key);
		}
		return value;
	}
}
