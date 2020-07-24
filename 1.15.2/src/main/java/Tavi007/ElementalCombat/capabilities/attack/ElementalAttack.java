package Tavi007.ElementalCombat.capabilities.attack;

import java.util.HashMap;

public class ElementalAttack implements IElementalAttack{

	private HashMap<String, Integer> attack;
	
	public ElementalAttack() {
		this.attack = new HashMap<String, Integer>();
	}
	
	public ElementalAttack(HashMap<String, Integer> attack) {
		this.attack = attack;
	}
	
	@Override
	public HashMap<String, Integer> getElementalAttack() {return this.attack;}

	@Override
	public void setElementalAttack(HashMap<String, Integer> map) {this.attack = map;}

}
