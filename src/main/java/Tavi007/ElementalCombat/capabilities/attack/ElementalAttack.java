package Tavi007.ElementalCombat.capabilities.attack;

import java.util.HashMap;
import java.util.Map;

import Tavi007.ElementalCombat.enchantments.ElementalEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

public class ElementalAttack implements IElementalAttack{

	private HashMap<String, Integer> attack;
	private boolean areEnchantmentsApplied = false;
	
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

	@Override
	public boolean areEnchantmentsApplied() {return this.areEnchantmentsApplied;}

	@Override
	public void applyEnchantments(Map<Enchantment, Integer> enchantments) {
		enchantments.forEach((key, value) -> {
			
			//currently only comparing strings.
			//maybe change to resourceLocation later, so other mods can interact with this as well.
			//sword
			if(key.getName() == ElementalEnchantments.ICE_ASPECT.getName()) {putHighestValueIntoMap("ice", value);}
			if(key.getName() == Enchantments.FIRE_ASPECT.getName()) {putHighestValueIntoMap("fire", value);}
			if(key.getName() == ElementalEnchantments.WATER_ASPECT.getName()) {putHighestValueIntoMap("water", value);}
			if(key.getName() == ElementalEnchantments.THUNDER_ASPECT.getName()) {putHighestValueIntoMap("thunder", value);}
			//bow
			if(key.getName() == Enchantments.FLAME.getName()) {putHighestValueIntoMap("fire", value);}
			//trident
			if(key.getName() == Enchantments.CHANNELING.getName()) {putHighestValueIntoMap("thunder", value);}
		});
		this.areEnchantmentsApplied = true;
		}
	
	private void putHighestValueIntoMap(String key, Integer newValue) {
		if(this.attack.get(key) == null) {attack.put(key, newValue);}
		else if(this.attack.get(key) < newValue) {attack.put(key, newValue);}
	}
}
