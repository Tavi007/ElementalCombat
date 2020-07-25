package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.HashSet;

public interface IElementalDefense {
	HashMap<String, Integer> getElementalWeakness();
	void setElementalWeakness(HashMap<String, Integer> set);
	boolean isElementalWeakness(String key);
	
	HashMap<String, Integer> getElementalResistance();
	void setElementalResistance(HashMap<String, Integer> set);
	boolean isElementalResistance(String key);
	
	HashSet<String> getElementalImmunity();
	void setElementalImmunity(HashSet<String> set);
	boolean isElementalImmunity(String key);
	
	HashSet<String> getElementalAbsorption();
	void setElementalAbsorption(HashSet<String> set);
	boolean isElementalAbsorption(String key);

}
