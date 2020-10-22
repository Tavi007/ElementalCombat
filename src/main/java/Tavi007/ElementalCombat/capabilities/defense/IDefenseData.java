package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;

public interface IDefenseData {
	HashMap<String, Integer> getStyleFactor();
	void setStyleFactor(HashMap<String, Integer> set);
	
	HashMap<String, Integer> getElementFactor();
	void setElementFactor(HashMap<String, Integer> set);

	HashMap<String, Integer> getEnchantmentData();
	void setEnchantmentData(HashMap<String, Integer> data);
	boolean areEnchantmentChangesApplied();
	void applyEnchantmentChanges(Map<Enchantment, Integer> currentEnchantments);
	
	void set(DefenseData data);
	void add(DefenseData data);
	void substract(DefenseData data);
	boolean isEmpty();
}
