package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

public class CombatPropertiesTextMapping {
	private final HashMap<String, String> mapping;

	public CombatPropertiesTextMapping() {
		this.mapping = new HashMap<String, String>();
	}

	public CombatPropertiesTextMapping(HashMap<String, String> mapping) {
		this.mapping = mapping;
	}
	
	public String getValue(String key) {
		return mapping.get(key);
	}
}
