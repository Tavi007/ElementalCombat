package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.util.ResourceLocation;

public class EntityData extends GeneralData
{
	   public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
	   public static final EntityData EMPTY = new EntityData();

	   private final boolean biomeDependency; 
	   
	   public EntityData(HashMap<String, Double> defenseStyle, HashMap<String, Double> defenseElement, String attackStyle, String attackElement, boolean biomeDependency)
	   {
		   super(defenseStyle, defenseElement, attackStyle, attackElement);
		   this.biomeDependency = biomeDependency;
	   }
	   
	   public EntityData()
	   {
		   super();
		   this.biomeDependency = false;
	   }
	   
	   public boolean getBiomeDependency()
	   {
		   return this.biomeDependency;
	   }
}
