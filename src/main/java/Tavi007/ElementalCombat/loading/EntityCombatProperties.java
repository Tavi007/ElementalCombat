package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

public class EntityCombatProperties extends ItemCombatProperties
{
	   public static final EntityCombatProperties EMPTY = new EntityCombatProperties();

	   private final boolean biomeDependency; 
	   
	   public EntityCombatProperties(HashMap<String, Double> defenseStyle, HashMap<String, Double> defenseElement, String attackStyle, String attackElement, boolean biomeDependency)
	   {
		   super(defenseStyle, defenseElement, attackStyle, attackElement);
		   this.biomeDependency = biomeDependency;
	   }
	   
	   public EntityCombatProperties()
	   {
		   super();
		   this.biomeDependency = false;
	   }
	   
	   public boolean getBiomeDependency()
	   {
		   return this.biomeDependency;
	   }
}
