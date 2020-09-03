package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.Map;

import Tavi007.ElementalCombat.enchantments.ElementalEnchantments;
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
		enchantments.forEach((key, value) -> {
			//currently only comparing strings.
			//maybe change to resourceLocation later, so other mods can interact with this as well.
			if(key.getName() == ElementalEnchantments.ICE_RESISTANCE.getName()) {this.elementFactor.put("ice", value);}
			if(key.getName() == ElementalEnchantments.FIRE_RESISTANCE.getName()) {this.elementFactor.put("fire", value);}
			if(key.getName() == ElementalEnchantments.WATER_RESISTANCE.getName()) {this.elementFactor.put("water", value);}
			if(key.getName() == ElementalEnchantments.THUNDER_RESISTANCE.getName()) {this.elementFactor.put("thunder", value);}
		});
		this.areEnchantmentsApplied = true;
	}

}
