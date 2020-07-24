package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashSet;

public class ElementalDefense implements IElementalDefense{

	private HashSet<String> weakness;
	private HashSet<String> resistance;
	private HashSet<String> immunity;
	private HashSet<String> absorption;
	
	public ElementalDefense() {
		this.weakness = new HashSet<String>();
		this.resistance = new HashSet<String>();
		this.immunity = new HashSet<String>();
		this.absorption = new HashSet<String>();
	}
	
	public ElementalDefense(HashSet<String> weakness, HashSet<String> resistance, HashSet<String> immunity, HashSet<String> absorption) {
		this.weakness = weakness;
		this.resistance = resistance;
		this.immunity = immunity;
		this.absorption = absorption;
	}
	
	@Override
	public HashSet<String> getElementalWeakness() {return this.weakness;}

	@Override
	public void setElementalWeakness(HashSet<String> set) {this.weakness = set;}

	@Override
	public boolean isElementalWeakness(String key) {return this.weakness.contains(key);}

	
	
	@Override
	public HashSet<String> getElementalResistance() {return this.resistance;}

	@Override
	public void setElementalResistance(HashSet<String> set) {this.resistance = set;}

	@Override
	public boolean isElementalResistance(String key) {return this.resistance.contains(key);}

	
	
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
