package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.util.ResourceLocation;

public class BiomeCombatProperties {
	   public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
	   public static final BiomeCombatProperties EMPTY = new BiomeCombatProperties();
	
	private final HashMap<String, Double> defense_element;
	
	public BiomeCombatProperties() {
		this.defense_element = new HashMap<String, Double>();
	}

	public BiomeCombatProperties(HashMap<String, Double> defense_element) {
		this.defense_element = defense_element;
	}
	
	public HashMap<String, Double> getDefenseElement() {return this.defense_element;}
}
