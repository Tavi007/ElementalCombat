package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.Map;

import Tavi007.ElementalCombat.enchantments.ElementalEnchantments;
import net.minecraft.enchantment.Enchantment;

public class DefenseData implements IDefenseData{

	private HashMap<String, Double> styleScaling = new HashMap<String, Double>();
	private HashMap<String, Double> elementScaling = new HashMap<String, Double>();
	
	private boolean areEnchantmentsApplied = false;
	
	public DefenseData() {
	}
	
	public DefenseData(HashMap<String, Double> styleScaling, HashMap<String, Double> elementScaling) {
		this.styleScaling = styleScaling;
		this.elementScaling = elementScaling;
	}

	@Override
	public HashMap<String, Double> getStyleScaling() {return this.styleScaling;}

	@Override
	public void setStyleScaling(HashMap<String, Double> set) {this.styleScaling = set;}

	@Override
	public HashMap<String, Double> getElementScaling() {return this.elementScaling;}

	@Override
	public void setElementScaling(HashMap<String, Double> set) {this.elementScaling = set;}
	

	@Override
	public boolean areEnchantmentsApplied() {return this.areEnchantmentsApplied;}

	@Override
	public void applyEnchantments(Map<Enchantment, Integer> enchantments) {
		enchantments.forEach((key, value) -> {
			
			Double scaling = 1.0 - value*0.2;
			
			//currently only comparing strings.
			//maybe change to resourceLocation later, so other mods can interact with this as well.
			if(key.getName() == ElementalEnchantments.ICE_RESISTANCE.getName()) {this.elementScaling.put("ice", scaling);}
			if(key.getName() == ElementalEnchantments.FIRE_RESISTANCE.getName()) {this.elementScaling.put("fire", scaling);}
			if(key.getName() == ElementalEnchantments.WATER_RESISTANCE.getName()) {this.elementScaling.put("water", scaling);}
			if(key.getName() == ElementalEnchantments.THUNDER_RESISTANCE.getName()) {this.elementScaling.put("thunder", scaling);}
		});
		this.areEnchantmentsApplied = true;
	}

}
