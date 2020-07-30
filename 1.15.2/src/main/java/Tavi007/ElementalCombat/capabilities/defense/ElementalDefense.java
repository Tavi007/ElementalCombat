package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import Tavi007.ElementalCombat.enchantments.ElementalEnchantments;
import net.minecraft.enchantment.Enchantment;

public class ElementalDefense implements IElementalDefense{

	private HashMap<String, Integer> weakness;
	private HashMap<String, Integer> resistance;
	private HashSet<String> immunity;
	private HashSet<String> absorption;
	private boolean areEnchantmentsApplied = false;
	
	public ElementalDefense() {
		this.weakness = new HashMap<String, Integer>();
		this.resistance = new HashMap<String, Integer>();
		this.immunity = new HashSet<String>();
		this.absorption = new HashSet<String>();
	}
	
	public ElementalDefense(HashMap<String, Integer> weakness, HashMap<String, Integer> resistance, HashSet<String> immunity, HashSet<String> absorption) {
		this.weakness = weakness;
		this.resistance = resistance;
		this.immunity = immunity;
		this.absorption = absorption;
	}
	

	@Override
	public boolean areEnchantmentsApplied() {return this.areEnchantmentsApplied;}

	@Override
	public void applyEnchantments(Map<Enchantment, Integer> enchantments) {
		enchantments.forEach((key, value) -> {
			if(key.getName() == ElementalEnchantments.ICE_RESISTANCE.getName()) {putHighestValueIntoResistance("ice", value);}
			if(key.getName() == ElementalEnchantments.FIRE_RESISTANCE.getName()) {putHighestValueIntoResistance("fire", value);}
			if(key.getName() == ElementalEnchantments.WATER_RESISTANCE.getName()) {putHighestValueIntoResistance("water", value);}
			if(key.getName() == ElementalEnchantments.THUNDER_RESISTANCE.getName()) {putHighestValueIntoResistance("thunder", value);}
		});
		this.areEnchantmentsApplied = true;
		}
	
	private void putHighestValueIntoResistance(String key, Integer newValue) {
		if(this.resistance.get(key) == null) {resistance.put(key, newValue);}
		else if(this.resistance.get(key) < newValue) {resistance.put(key, newValue);}
	}
	
	
	
	@Override
	public HashMap<String, Integer> getElementalWeakness() {return this.weakness;}

	@Override
	public void setElementalWeakness(HashMap<String, Integer> map) {this.weakness = map;}

	@Override
	public boolean isElementalWeakness(String key) {return this.weakness.containsKey(key);}

	
	
	@Override
	public HashMap<String, Integer> getElementalResistance() {return this.resistance;}

	@Override
	public void setElementalResistance(HashMap<String, Integer> map) {this.resistance = map;}

	@Override
	public boolean isElementalResistance(String key) {return this.resistance.containsKey(key);}

	
	
	@Override
	public HashSet<String> getElementalImmunity() {return this.immunity;}

	@Override
	public void setElementalImmunity(HashSet<String> set) {this.immunity = set;}

	@Override
	public boolean isElementalImmunity(String key) {return this.immunity.contains(key);}

	
	
	@Override
	public HashSet<String> getElementalAbsorption() {return this.absorption;}

	@Override
	public void setElementalAbsorption(HashSet<String> set) {this.absorption = set;}

	@Override
	public boolean isElementalAbsorption(String key) {return this.absorption.contains(key);}

}
