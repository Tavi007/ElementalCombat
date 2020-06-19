package Tavi007.ElementalCombat;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.util.ResourceLocation;

public class ElementalData
{
	   public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
	   public static final ElementalData EMPTY = new ElementalData();
	   
	   private final Set<String> weakness;
	   private final Set<String> resistance;
	   private final Set<String> wall;
	   private final Set<String> absorb;
	   private final Set<String> attack;
	   
	   public ElementalData(Set<String> weak, Set<String> resi, Set<String> wall, Set<String> abso, Set<String> atck)
	   {
		   this.weakness = weak;
		   this.resistance = resi;
		   this.wall = wall;
		   this.absorb = abso;
		   this.attack = atck;
	   }
	   
	   public ElementalData()
	   {
		   this.weakness = Sets.newHashSet();
		   this.resistance = Sets.newHashSet();
		   this.wall = Sets.newHashSet();
		   this.absorb = Sets.newHashSet();
		   this.attack = Sets.newHashSet();
	   }
	   
	   public Set<String> getWeaknessSet()
	   {
		   return this.weakness;
	   }
	   
	   public Set<String> getResistanceSet()
	   {
		   return this.resistance;
	   }
	   
	   public Set<String> getWallSet()
	   {
		   return this.wall;
	   }
	   
	   public Set<String> getAbsorbSet()
	   {
		   return this.absorb;
	   }
	   
	   public Set<String> getAttackSet()
	   {
		   return this.attack;
	   }
}
