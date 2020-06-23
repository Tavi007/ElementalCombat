package Tavi007.ElementalCombat.capabilities;

import java.util.Set;

public interface  IElementalDefenseData 
{
	public void setWeaknessSet(Set<String> elementSet);
	public void setResistanceSet(Set<String> elementSet);
	public void setImmunitySet(Set<String> elementSet);
	public void setAbsorbSet(Set<String> elementSet);

	public Set<String> getWeaknessSet();
	public Set<String> getResistanceSet();
	public Set<String> getImmunitySet();
	public Set<String> getAbsorbSet();
}
