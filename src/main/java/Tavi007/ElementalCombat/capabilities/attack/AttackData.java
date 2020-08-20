package Tavi007.ElementalCombat.capabilities.attack;

import java.util.Map;

import Tavi007.ElementalCombat.enchantments.ElementalEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

public class AttackData implements IAttackData{

	private String style = "basic";
	private String element = "natural";
	private boolean areEnchantmentsApplied = false;
	
	public AttackData() {
	}
	
	public AttackData(String style, String element) {
		this.style = style;
		this.element = element;
	}

	@Override
	public void setAttackData(String style, String element) {
		this.style = style;
		this.element = element;
	}
	
	@Override
	public String getElement() {return this.element;}

	@Override
	public String getStyle() {return this.style;}

	@Override
	public void setElement(String element) {this.element = element;}

	@Override
	public void setStyle(String style) {this.style = style;}
	
	
	@Override
	public boolean areEnchantmentsApplied() {return this.areEnchantmentsApplied;}

	@Override
	public void applyEnchantments(Map<Enchantment, Integer> enchantments) {
		
		enchantments.forEach((key, value) -> {
			
			//currently only comparing strings.
			//maybe change to resourceLocation later, so other mods can interact with this as well.
			//sword
			if(key.getName() == Enchantments.FIRE_ASPECT.getName()) {this.element = "fire";}
			if(key.getName() == ElementalEnchantments.ICE_ASPECT.getName()) {this.element = "ice";}
			if(key.getName() == ElementalEnchantments.WATER_ASPECT.getName()) {this.element = "water";}
			if(key.getName() == ElementalEnchantments.THUNDER_ASPECT.getName()) {this.element = "thunder";}
			//bow
			if(key.getName() == Enchantments.FLAME.getName()) {this.element = "fire";}
			//trident
			if(key.getName() == Enchantments.CHANNELING.getName()) {this.element = "thunder";}
		});
		this.areEnchantmentsApplied = true;
		}
}
