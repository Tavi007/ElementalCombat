package Tavi007.ElementalCombat.capabilities;

import java.util.List;

public interface  IElementalDefenceData 
{
	public void setWeaknessList(List<String> elementList);
	public void setResistanceList(List<String> elementList);
	public void setWallList(List<String> elementList);
	public void setAbsorbList(List<String> elementList);

	public List<String> getWeaknessList();
	public List<String> getResistanceList();
	public List<String> getWallList();
	public List<String> getAbsorbList();
}
