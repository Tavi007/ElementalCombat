package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;

import Tavi007.ElementalCombat.util.DefenseDataHelper;

public class DefenseData implements IDefenseData{

	private HashMap<String, Integer> styleFactor = new HashMap<String, Integer>();
	private HashMap<String, Integer> elementFactor = new HashMap<String, Integer>();
	
	public DefenseData() {
	}
	
	public DefenseData(HashMap<String, Integer> styleFactor, HashMap<String, Integer> elementFactor) {
		this.styleFactor = styleFactor;
		this.elementFactor = elementFactor;
	}

	public DefenseData(DefenseData data) {
		this.styleFactor = data.getStyleFactor();
		this.elementFactor = data.getElementFactor();
	}
	@Override
	public HashMap<String, Integer> getStyleFactor() {return this.styleFactor;}

	@Override
	public void setStyleFactor(HashMap<String, Integer> set) {this.styleFactor = set;}

	@Override
	public HashMap<String, Integer> getElementFactor() {return this.elementFactor;}

	@Override
	public void setElementFactor(HashMap<String, Integer> set) {this.elementFactor = set;}
	
	public void sum(DefenseData data) {
		DefenseDataHelper.sumMaps(this.styleFactor, data.getStyleFactor());
		DefenseDataHelper.sumMaps(this.elementFactor, data.getElementFactor());
	}
}
