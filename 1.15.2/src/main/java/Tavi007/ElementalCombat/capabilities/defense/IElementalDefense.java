package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashSet;

public interface IElementalDefense {
	HashSet<String> getElementalWeakness();
	void setElementalWeakness(HashSet<String> set);
	boolean isElementalWeakness(String key);
	
	HashSet<String> getElementalResistance();
	void setElementalResistance(HashSet<String> set);
	boolean isElementalResistance(String key);
	
	HashSet<String> getElementalImmunity();
	void setElementalImmunity(HashSet<String> set);
	boolean isElementalImmunity(String key);
	
	HashSet<String> getElementalAbsorption();
	void setElementalAbsorption(HashSet<String> set);
	boolean isElementalAbsorption(String key);

}
