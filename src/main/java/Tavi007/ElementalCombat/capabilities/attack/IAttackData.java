package Tavi007.ElementalCombat.capabilities.attack;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;

public interface IAttackData {
	void setAttackData(String style, String element);
	
	void setElement(String element);
	String getElement();
	
	void setStyle(String style);
	String getStyle();
	
	boolean areEnchantmentsApplied();
	void applyEnchantments(Map<Enchantment, Integer> enchantments);
}
