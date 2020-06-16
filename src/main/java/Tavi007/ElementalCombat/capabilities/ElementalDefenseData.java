package Tavi007.ElementalCombat.capabilities;

import java.util.HashSet;
import java.util.Set;

public class ElementalDefenseData implements IElementalDefenseData
{
	private Set<String> weaknessSet = new HashSet<String>();
	private Set<String> resistanceSet = new HashSet<String>();
	private Set<String> wallSet = new HashSet<String>();
	private Set<String> absorbSet = new HashSet<String>();
	
	
	// Setter
	@Override
	public void setWeaknessSet(Set<String> elementSet)
	{
		this.weaknessSet = elementSet;
	}
	@Override
	public void setResistanceSet(Set<String> elementSet)
	{
		this.resistanceSet = elementSet;
	}
	@Override
	public void setWallSet(Set<String> elementSet)
	{
		this.wallSet = elementSet;
	}
	@Override
	public void setAbsorbSet(Set<String> elementSet)
	{
		this.absorbSet = elementSet;
	}
	
	
	//Getter
	@Override
	public Set<String> getWeaknessSet()
	{
		return this.weaknessSet;
	}
	@Override
	public Set<String> getResistanceSet()
	{
		return this.resistanceSet;
	}
	@Override
	public Set<String> getWallSet()
	{
		return this.wallSet;
	}
	@Override
	public Set<String> getAbsorbSet()
	{
		return this.absorbSet;
	}
}
