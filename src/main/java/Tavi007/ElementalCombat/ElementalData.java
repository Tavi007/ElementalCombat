package Tavi007.ElementalCombat;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;

public class ElementalData
{
	   public static final ResourceLocation EMPTY = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
	   
	   private final Set<String> weakSet;
	   private final Set<String> resiSet;
	   private final Set<String> wallSet;
	   private final Set<String> absoSet;
	   private final Set<String> atckSet;
	   
	   public ElementalData(Set<String> weak, Set<String> resi, Set<String> wall, Set<String> abso, Set<String> atck)
	   {
		   this.weakSet = weak;
		   this.resiSet = resi;
		   this.wallSet = wall;
		   this.absoSet = abso;
		   this.atckSet = atck;
	   }
	   
	   public ElementalData()
	   {
		   this.weakSet = Sets.newHashSet();
		   this.resiSet = Sets.newHashSet();
		   this.wallSet = Sets.newHashSet();
		   this.absoSet = Sets.newHashSet();
		   this.atckSet = Sets.newHashSet();
	   }
	   
	   public Set<String> getWeaknessSet()
	   {
		   return this.weakSet;
	   }
	   
	   public Set<String> getResistanceSet()
	   {
		   return this.resiSet;
	   }
	   
	   public Set<String> getWallSet()
	   {
		   return this.wallSet;
	   }
	   
	   public Set<String> getAbsorbSet()
	   {
		   return this.absoSet;
	   }
	   
	   public Set<String> getAttackSet()
	   {
		   return this.atckSet;
	   }
}
