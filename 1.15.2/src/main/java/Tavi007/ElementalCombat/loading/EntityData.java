package Tavi007.ElementalCombat.loading;

import java.util.Set;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.util.ResourceLocation;

public class EntityData extends GeneralData
{
	   public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
	   public static final EntityData EMPTY = new EntityData();

	   private final boolean biomeDependency; 
	   
	   public EntityData(Set<String> weak, Set<String> resi, Set<String> immunity, Set<String> abso, Set<AttackFormat> atck, boolean biomeDependency)
	   {
		   super(weak, resi, immunity, abso, atck);
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
