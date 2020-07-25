package Tavi007.ElementalCombat.loading;

import java.util.HashMap;
import java.util.HashSet;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.util.ResourceLocation;

public class GeneralData 
{
	   public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
	   public static final GeneralData EMPTY = new GeneralData();
	   
	   private final HashMap<String, Integer> weakness;
	   private final HashMap<String, Integer> resistance;
	   private final HashSet<String> immunity;
	   private final HashSet<String> absorb;
	   private final HashMap<String, Integer> attack;
	   
	   public GeneralData(HashMap<String, Integer> weak, HashMap<String, Integer> resi, HashSet<String> immunity, HashSet<String> abso, HashMap<String, Integer> atck)
	   {
		   this.weakness = weak;
		   this.resistance = resi;
		   this.immunity = immunity;
		   this.absorb = abso;
		   this.attack = atck;
	   }
	   
	   public GeneralData()
	   {
		   this.weakness = new HashMap<String, Integer>();
		   this.resistance = new HashMap<String, Integer>();
		   this.immunity = new HashSet<String>();
		   this.absorb = new HashSet<String>();
		   this.attack = new HashMap<String, Integer>();
		   
	   }
	   
	   public HashMap<String, Integer> getWeaknessMap()
	   {
		   return this.weakness;
	   }
	   
	   public HashMap<String, Integer> getResistanceMap()
	   {
		   return this.resistance;
	   }
	   
	   public HashSet<String> getImmunitySet()
	   {
		   return this.immunity;
	   }
	   
	   public HashSet<String> getAbsorbSet()
	   {
		   return this.absorb;
	   }
	   
	   public HashMap<String, Integer> getAttackMap()
	   {
		   return this.attack;
	   }
}
