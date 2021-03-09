package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import Tavi007.ElementalCombat.config.ServerConfig;

public class ItemCombatProperties 
{
	   private final HashMap<String, Integer> defense_style;
	   private final HashMap<String, Integer> defense_element;

	   private final String attack_style;
	   private final String attack_element;
	   
	   public ItemCombatProperties(HashMap<String, Integer> defenseStyle, HashMap<String, Integer> defenseElement, String attackStyle, String attackElement)
	   {
		   this.defense_style = defenseStyle;
		   this.defense_element = defenseElement;
		   if (attackStyle.isEmpty()) {
			   this.attack_style = ServerConfig.getDefaultStyle();
		   }
		   else {
			   this.attack_style = attackStyle;
		   }
		   if (attackElement.isEmpty()) {
			   this.attack_element = ServerConfig.getDefaultElement();
		   }
		   else {
			   this.attack_element = attackElement;
		   }
	   }
	   
	   public ItemCombatProperties()
	   {
		   this.defense_style = new HashMap<String, Integer>();
		   this.defense_element = new HashMap<String, Integer>();
		   this.attack_style = ServerConfig.getDefaultStyle();
		   this.attack_element = ServerConfig.getDefaultElement();
	   }

	   public ItemCombatProperties(ItemCombatProperties properties)
	   {
		   this.defense_style = properties.getDefenseStyle();
		   this.defense_element = properties.getDefenseElement();
		   this.attack_style = properties.getAttackStyle();
		   this.attack_element = properties.getAttackElement();
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
