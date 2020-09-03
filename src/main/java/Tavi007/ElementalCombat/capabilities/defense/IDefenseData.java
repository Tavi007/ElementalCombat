package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;

public interface IDefenseData {
	HashMap<String, Integer> getStyleFactor();
	void setStyleFactor(HashMap<String, Integer> set);
	
	HashMap<String, Integer> getElementFactor();
	void setElementFactor(HashMap<String, Integer> set);

	public boolean areEnchantmentsApplied();
	public void applyEnchantments(Map<Enchantment, Integer> enchantments);
}
