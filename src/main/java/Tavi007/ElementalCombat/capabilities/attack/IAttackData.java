package Tavi007.ElementalCombat.capabilities.attack;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;

public interface IAttackData {
	void set(String style, String element);
	void set(AttackData data);
	
	void setElement(String element);
	String getElement();
	
	void setStyle(String style);
	String getStyle();

	boolean areEnchantmentChangesApplied();
	void applyEnchantmentChanges(Map<Enchantment, Integer> currentEnchantments);

}
