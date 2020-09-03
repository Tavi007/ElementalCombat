package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.Map;

import Tavi007.ElementalCombat.enchantments.CombatEnchantments;
import net.minecraft.enchantment.Enchantment;

public class DefenseData implements IDefenseData{

	private HashMap<String, Integer> styleFactor = new HashMap<String, Integer>();
	private HashMap<String, Integer> elementFactor = new HashMap<String, Integer>();
	
	private boolean areEnchantmentsApplied = false;
	
	public DefenseData() {
	}
	
	public DefenseData(HashMap<String, Integer> styleFactor, HashMap<String, Integer> elementFactor) {
		this.styleFactor = styleFactor;
		this.elementFactor = elementFactor;
	}

	@Override
	public HashMap<String, Integer> getStyleFactor() {return this.styleFactor;}

	@Override
	public void setStyleFactor(HashMap<String, Integer> set) {this.styleFactor = set;}

	@Override
	public HashMap<String, Integer> getElementFactor() {return this.elementFactor;}

	@Override
	public void setElementFactor(HashMap<String, Integer> set) {this.elementFactor = set;}
	

	@Override
	public boolean areEnchantmentsApplied() {return this.areEnchantmentsApplied;}

	@Override
	public void applyEnchantments(Map<Enchantment, Integer> enchantments) {
		enchantments.forEach((key, level) -> {
			//currently only comparing strings.
			//maybe change to resourceLocation later, so other mods can interact with this as well.
			
			// elemental enchantments
			if(key.getName() == CombatEnchantments.ICE_RESISTANCE.getName()) {
				this.elementFactor.put("ice", level*2);
				this.elementFactor.put("fire", -level*2);
			}
			else if(key.getName() == CombatEnchantments.FIRE_RESISTANCE.getName()) {
				this.elementFactor.put("fire", level*2);
				this.elementFactor.put("ice", -level*2);
			}
			else if(key.getName() == CombatEnchantments.WATER_RESISTANCE.getName()) {
				this.elementFactor.put("water", level*2);
				this.elementFactor.put("thunder", -level*2);
			}
			else if(key.getName() == CombatEnchantments.THUNDER_RESISTANCE.getName()) {
				this.elementFactor.put("thunder", level*2);
				this.elementFactor.put("water", -level*2);
			}

			// style enchantments
			if(key.getName() == CombatEnchantments.BLAST_PROTECTION.getName()) {
				this.styleFactor.put("explosion", level*2);
			}
			else if(key.getName() == CombatEnchantments.PROJECTILE_PROTECTION.getName()) {
				this.styleFactor.put("projectile", level*2);
			}
		});
		this.areEnchantmentsApplied = true;
	}

}
