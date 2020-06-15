package Tavi007.ElementalCombat.capabilities;

import java.util.ArrayList;
import java.util.List;

public class ElementalDefenseData implements IElementalDefenseData
{
	private List<String> weaknessList = new ArrayList<String>();
	private List<String> resistanceList = new ArrayList<String>();
	private List<String> wallList = new ArrayList<String>();
	private List<String> absorbList = new ArrayList<String>();
	
	
	// Setter
	@Override
	public void setWeaknessList(List<String> elementList)
	{
		this.weaknessList = elementList;
	}
	@Override
	public void setResistanceList(List<String> elementList)
	{
		this.resistanceList = elementList;
	}
	@Override
	public void setWallList(List<String> elementList)
	{
		this.wallList = elementList;
	}
	@Override
	public void setAbsorbList(List<String> elementList)
	{
		this.absorbList = elementList;
	}
	
	
	//Getter
	@Override
	public List<String> getWeaknessList()
	{
		return this.weaknessList;
	}
	@Override
	public List<String> getResistanceList()
	{
		return this.resistanceList;
	}
	@Override
	public List<String> getWallList()
	{
		return this.wallList;
	}
	@Override
	public List<String> getAbsorbList()
	{
		return this.absorbList;
	}
}
