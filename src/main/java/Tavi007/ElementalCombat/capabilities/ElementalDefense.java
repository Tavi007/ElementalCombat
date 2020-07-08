package Tavi007.ElementalCombat.capabilities;

import java.util.HashSet;
import java.util.Set;

public class ElementalDefense
{
	private Set<String> weaknessSet = new HashSet<String>();
	private Set<String> resistanceSet = new HashSet<String>();
	private Set<String> immunitySet = new HashSet<String>();
	private Set<String> absorbSet = new HashSet<String>();
	
	
	// Setter
	public void setWeaknessSet(Set<String> elementSet)
	{
		this.weaknessSet = elementSet;
	}
	public void setResistanceSet(Set<String> elementSet)
	{
		this.resistanceSet = elementSet;
	}
	public void setImmunitySet(Set<String> elementSet)
	{
		this.immunitySet = elementSet;
	}
	public void setAbsorbSet(Set<String> elementSet)
	{
		this.absorbSet = elementSet;
	}
	
	
	//Getter
	public Set<String> getWeaknessSet()
	{
		return this.weaknessSet;
	}
	public Set<String> getResistanceSet()
	{
		return this.resistanceSet;
	}
	public Set<String> getImmunitySet()
	{
		return this.immunitySet;
	}
	public Set<String> getAbsorbSet()
	{
		return this.absorbSet;
	}
}
