package Tavi007.ElementalCombat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

public class ElementalData
{
	   public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
	   public static final ElementalData EMPTY = new ElementalData();
	   
	   private final Set<String> weakness;
	   private final Set<String> resistance;
	   private final Set<String> wall;
	   private final Set<String> absorb;
	   private final Map<String,Integer> attack;
	   
	   public ElementalData(Set<String> weak, Set<String> resi, Set<String> wall, Set<String> abso, Map<String,Integer> atck)
	   {
		   this.weakness = weak;
		   this.resistance = resi;
		   this.wall = wall;
		   this.absorb = abso;
		   this.attack = atck;
	   }
	   
	   public ElementalData()
	   {
		   this.weakness = new HashSet<String>();
		   this.resistance = new HashSet<String>();
		   this.wall = new HashSet<String>();
		   this.absorb = new HashSet<String>();
		   this.attack = new HashMap<String,Integer>();
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
	   
	   public Map<String,Integer> getAttackMap()
	   {
		   return this.attack;
	   }
}
