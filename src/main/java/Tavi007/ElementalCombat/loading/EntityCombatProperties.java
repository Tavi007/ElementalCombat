package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.util.ResourceLocation;

public class EntityCombatProperties extends GeneralCombatProperties
{
	   public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
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
