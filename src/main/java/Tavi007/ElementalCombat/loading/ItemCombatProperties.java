package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;

public class ItemCombatProperties 
{
	   public static final ItemCombatProperties EMPTY = new ItemCombatProperties();

	   private final HashMap<String, Integer> defense_style;
	   private final HashMap<String, Integer> defense_element;

	   private final String attack_style;
	   private final String attack_element;
	   
	   public ItemCombatProperties(HashMap<String, Integer> defenseStyle, HashMap<String, Integer> defenseElement, String attackStyle, String attackElement)
	   {
		   this.defense_style = defenseStyle;
		   this.defense_element = defenseElement;
		   if (attackStyle.isEmpty()) {
			   this.attack_style = ElementalCombat.DEFAULT_STYLE;
		   }
		   else {
			   this.attack_style = attackStyle;
		   }
		   if (attackElement.isEmpty()) {
			   this.attack_element = ElementalCombat.DEFAULT_ELEMENT;
		   }
		   else {
			   this.attack_element = attackElement;
		   }
	   }
	   
	   public ItemCombatProperties()
	   {
		   this.defense_style = new HashMap<String, Integer>();
		   this.defense_element = new HashMap<String, Integer>();
		   this.attack_style = ElementalCombat.DEFAULT_STYLE;
		   this.attack_element = ElementalCombat.DEFAULT_ELEMENT;
	   }
	   
	   public HashMap<String, Integer> getDefenseStyle()
	   {
		   return this.defense_style;
	   }
	   
	   public HashMap<String, Integer> getDefenseElement()
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
