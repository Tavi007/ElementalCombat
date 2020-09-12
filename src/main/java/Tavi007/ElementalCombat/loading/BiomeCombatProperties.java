package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

public class BiomeCombatProperties {
	public static final BiomeCombatProperties EMPTY = new BiomeCombatProperties();
	
	private final HashMap<String, Integer> defense_element;
	
	public BiomeCombatProperties() {
		this.defense_element = new HashMap<String, Integer>();
	}

	public BiomeCombatProperties(HashMap<String, Integer> defense_element) {
		this.defense_element = defense_element;
	}
	
	public BiomeCombatProperties(BiomeCombatProperties biomeData) {
		this.defense_element = biomeData.getDefenseElement();
	}

	public HashMap<String, Integer> getDefenseElement() {return this.defense_element;}
}
