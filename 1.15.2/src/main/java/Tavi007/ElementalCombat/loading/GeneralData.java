package Tavi007.ElementalCombat.loading;

import java.util.HashSet;
import java.util.Set;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.util.ResourceLocation;

public class GeneralData 
{
	   public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
	   public static final GeneralData EMPTY = new GeneralData();
	   
	   private final Set<String> weakness;
	   private final Set<String> resistance;
	   private final Set<String> immunity;
	   private final Set<String> absorb;
	   private final Set<AttackFormat> attack;
	   
	   public GeneralData(Set<String> weak, Set<String> resi, Set<String> immunity, Set<String> abso, Set<AttackFormat> atck)
	   {
		   this.weakness = weak;
		   this.resistance = resi;
		   this.immunity = immunity;
		   this.absorb = abso;
		   this.attack = atck;
	   }
	   
	   public GeneralData()
	   {
		   this.weakness = new HashSet<String>();
		   this.resistance = new HashSet<String>();
		   this.immunity = new HashSet<String>();
		   this.absorb = new HashSet<String>();
		   this.attack = new HashSet<AttackFormat>();
		   
	   }
	   
	   public Set<String> getWeaknessSet()
	   {
		   return this.weakness;
	   }
	   
	   public Set<String> getResistanceSet()
	   {
		   return this.resistance;
	   }
	   
	   public Set<String> getImmunitySet()
	   {
		   return this.immunity;
	   }
	   
	   public Set<String> getAbsorbSet()
	   {
		   return this.absorb;
	   }
	   
	   public Set<AttackFormat> getAttackSet()
	   {
		   return this.attack;
	   }
}
