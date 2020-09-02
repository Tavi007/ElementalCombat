package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

public class ItemCombatProperties 
{
	   public static final ItemCombatProperties EMPTY = new ItemCombatProperties();

	   private final HashMap<String, Double> defense_style;
	   private final HashMap<String, Double> defense_element;

	   private final String attack_style;
	   private final String attack_element;
	   
	   public ItemCombatProperties(HashMap<String, Double> defenseStyle, HashMap<String, Double> defenseElement, String attackStyle, String attackElement)
	   {
		   this.defense_style = defenseStyle;
		   this.defense_element = defenseElement;
		   if (attackStyle.isEmpty()) {
			   this.attack_style = "basic";
		   }
		   else {
			   this.attack_style = attackStyle;
		   }
		   if (attackElement.isEmpty()) {
			   this.attack_element = "natural";
		   }
		   else {
			   this.attack_element = attackElement;
		   }
	   }
	   
	   public ItemCombatProperties()
	   {
		   this.defense_style = new HashMap<String, Double>();
		   this.defense_element = new HashMap<String, Double>();
		   this.attack_style = "basic";
		   this.attack_element = "natural";
	   }
	   
	   public HashMap<String, Double> getDefenseStyle()
	   {
		   return this.defense_style;
	   }
	   
	   public HashMap<String, Double> getDefenseElement()
	   {
		   return this.defense_element;
	   }
	   
	   public String getAttackStyle()
	   {
		   return this.attack_style;
	   }
	   
	   public String getAttackElement()
	   {
		   return this.attack_element;
	   }
}
