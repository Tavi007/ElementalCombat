package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;

public interface IDefenseData {
	HashMap<String, Double> getStyleScaling();
	void setStyleScaling(HashMap<String, Double> set);
	
	HashMap<String, Double> getElementScaling();
	void setElementScaling(HashMap<String, Double> set);

	public boolean areEnchantmentsApplied();
	public void applyEnchantments(Map<Enchantment, Integer> enchantments);
}
