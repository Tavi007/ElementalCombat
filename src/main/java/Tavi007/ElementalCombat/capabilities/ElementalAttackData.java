package Tavi007.ElementalCombat.capabilities;
import java.util.ArrayList;
import java.util.List;

public class ElementalAttackData implements IElementalAttackData
{
	private List<String> attackList = new ArrayList<String>();
	
	// Setter
	@Override
	public void setAttackList(List<String> elementList)
	{
		this.attackList = elementList;
	}
	
	// Getter
	@Override
	public List<String> getAttackList()
	{
		return this.attackList;
	}
}
