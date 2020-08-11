package Tavi007.ElementalCombat.capabilities.attack;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;

public interface IElementalAttack {
	HashMap<String, Integer> getElementalAttack();
	void setElementalAttack(HashMap<String, Integer> map);
	
	boolean areEnchantmentsApplied();
	void applyEnchantments(Map<Enchantment, Integer> enchantments);
}
