package Tavi007.ElementalCombat.loading;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.util.ResourceLocation;

public class DamageSourceCombatProperties {
	   public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
	   public static final DamageSourceCombatProperties EMPTY = new DamageSourceCombatProperties();

	private final String attack_style;
	private final String attack_element;
	
	public DamageSourceCombatProperties() {
		this.attack_style = "basic";
		this.attack_element = "natural";
	}
	
	public DamageSourceCombatProperties(String attack_style, String attack_element) {
		this.attack_style = attack_style;
		this.attack_element = attack_element;
	}

	public String getAttackStyle() {return this.attack_style;}
	public String getAttackElement() {return this.attack_element;}
}
