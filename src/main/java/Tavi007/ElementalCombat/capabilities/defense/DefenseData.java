package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.Map;

import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraft.enchantment.Enchantment;

public class DefenseData {

	private HashMap<String, Integer> styleFactor = new HashMap<String, Integer>();
	private HashMap<String, Integer> elementFactor = new HashMap<String, Integer>();

	// for itemstack
	private HashMap<String, Integer> enchantmentData = new HashMap<String, Integer>();
	private boolean areEnchantmentChangesApplied = false;

	public DefenseData() {
	}

	public DefenseData(HashMap<String, Integer> styleFactor, HashMap<String, Integer> elementFactor) {
		this.styleFactor = styleFactor;
		this.elementFactor = elementFactor;
	}

	public DefenseData(HashMap<String, Integer> styleFactor, HashMap<String, Integer> elementFactor, HashMap<String, Integer> enchantmentData) {
		this.styleFactor = styleFactor;
		this.elementFactor = elementFactor;
		this.enchantmentData = enchantmentData;
	}

	public DefenseData(DefenseData data) {
		this.styleFactor = data.getStyleFactor();
		this.elementFactor = data.getElementFactor();
		this.enchantmentData = data.getEnchantmentData();
		this.areEnchantmentChangesApplied = data.areEnchantmentChangesApplied();
	}

	public void clear() {
		this.styleFactor = new HashMap<String, Integer>();
		this.elementFactor = new HashMap<String, Integer>();
		this.enchantmentData = new HashMap<String, Integer>();
		this.areEnchantmentChangesApplied = false;
	}
	
	public HashMap<String, Integer> getEnchantmentData() {
		return this.enchantmentData;
	}
	
	public void setEnchantmentData(HashMap<String, Integer> data) {
		this.enchantmentData = data;
	}
	
	public boolean areEnchantmentChangesApplied() {return this.areEnchantmentChangesApplied;}

	public HashMap<String, Integer> getStyleFactor() {
		return this.styleFactor;
	}

	public void setStyleFactor(HashMap<String, Integer> set) {this.styleFactor = set;}

	public HashMap<String, Integer> getElementFactor() {return this.elementFactor;}

	public void setElementFactor(HashMap<String, Integer> set) {this.elementFactor = set;}

	public void set(DefenseData data) {
		this.styleFactor = data.getStyleFactor();
		this.elementFactor = data.getElementFactor();
	}

	public void add(DefenseData data) {
		DefenseDataHelper.sumMaps(this.styleFactor, data.getStyleFactor());
		DefenseDataHelper.sumMaps(this.elementFactor, data.getElementFactor());
	}

	public void substract(DefenseData data) {
		DefenseDataHelper.substractMaps(this.styleFactor, data.getStyleFactor());
		DefenseDataHelper.substractMaps(this.elementFactor, data.getElementFactor());
	}

	public boolean isEmpty() {
		return (this.styleFactor.isEmpty() && this.elementFactor.isEmpty());
	}

	public String toString() {
		return "ElementFactor=" + this.elementFactor.toString() + "; " + "StyleFactor=" + this.styleFactor.toString();
	}

	public void applyEnchantmentChanges(Map<Enchantment, Integer> currentEnchantments) {
		// change map
		HashMap<String, Integer> newEnchantments = new HashMap<String, Integer>();		
		currentEnchantments.forEach((ench, value) ->{
			newEnchantments.put(ench.getName(), value);
		});

		//compute difference
		if(!newEnchantments.equals(this.enchantmentData) && !newEnchantments.isEmpty()) {
			DefenseData diffData = DefenseDataHelper.getEnchantmentData(newEnchantments);
			DefenseData oldData = DefenseDataHelper.getEnchantmentData(this.enchantmentData);
			diffData.substract(oldData);
			
			//apply
			this.add(diffData);
			this.enchantmentData = newEnchantments;
		}
		this.areEnchantmentChangesApplied = true;
	}
}
