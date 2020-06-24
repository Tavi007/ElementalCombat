package Tavi007.ElementalCombat;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

public class EntityData
{
	   public static final ResourceLocation EMPTY_RESOURCELOCATION = new ResourceLocation(ElementalCombat.MOD_ID, "empty");
	   public static final EntityData EMPTY = new EntityData();
	   
	   private final Set<String> weakness;
	   private final Set<String> resistance;
	   private final Set<String> immunity;
	   private final Set<String> absorb;
	   private final Set<AttackFormat> attack;
	   private final boolean biomeDependency; 
	   
	   public EntityData(Set<String> weak, Set<String> resi, Set<String> immunity, Set<String> abso, Set<AttackFormat> atck, boolean biomeDependency)
	   {
		   this.weakness = weak;
		   this.resistance = resi;
		   this.immunity = immunity;
		   this.absorb = abso;
		   this.attack = atck;
		   this.biomeDependency = biomeDependency;
	   }
	   
	   public EntityData()
	   {
		   this.weakness = new HashSet<String>();
		   this.resistance = new HashSet<String>();
		   this.immunity = new HashSet<String>();
		   this.absorb = new HashSet<String>();
		   this.attack = new HashSet<AttackFormat>();
		   this.biomeDependency = false;
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
	   
	   public boolean getBiomeDependency()
	   {
		   return this.biomeDependency;
	   }
	   
	   public class AttackFormat
	   {
	   	   private final String name;
	   	   private final Integer value;
	   	   
	   	   public AttackFormat()
	   	   {
	   		   this.name = "";
	   		   this.value = 0;
	   	   }
	   	   
	   	   public AttackFormat(String name, Integer value)
	   	   {
	   		   this.name = name;
	   		   this.value = value;
	   	   }
	   	   
	   	   public String getName()
	   	   {
	   		   return this.name;
	   	   }
	   	   
	   	   public Integer getValue()
	   	   {
	   		   return this.value;
	   	   }
	   }
}
