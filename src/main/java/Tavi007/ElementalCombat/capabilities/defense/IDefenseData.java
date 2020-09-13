package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;

public interface IDefenseData {
	HashMap<String, Integer> getStyleFactor();
	void setStyleFactor(HashMap<String, Integer> set);
	
	HashMap<String, Integer> getElementFactor();
	void setElementFactor(HashMap<String, Integer> set);
}
