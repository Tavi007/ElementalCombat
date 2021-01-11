package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

public class CombatPropertiesMapping {
	private final HashMap<String, String> mapping;

	public CombatPropertiesMapping() {
		this.mapping = new HashMap<String, String>();
	}

	public CombatPropertiesMapping(HashMap<String, String> mapping) {
		this.mapping = mapping;
	}
	
	public String getValue(String key) {
		return mapping.get(key);
	}
}
