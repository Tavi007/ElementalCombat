package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

public class EntityCombatProperties extends ItemCombatProperties
{
	   public static final EntityCombatProperties EMPTY = new EntityCombatProperties();

	   private final boolean biome_dependency; 
	   
	   public EntityCombatProperties(HashMap<String, Integer> defenseStyle, HashMap<String, Integer> defenseElement, String attackStyle, String attackElement, boolean biomeDependency)
	   {
		   super(defenseStyle, defenseElement, attackStyle, attackElement);
		   this.biome_dependency = biomeDependency;
	   }
	   
	   public EntityCombatProperties()
	   {
		   super();
		   this.biome_dependency = false;
	   }
	   
	   public boolean getBiomeDependency()
	   {
		   return this.biome_dependency;
	   }
}
