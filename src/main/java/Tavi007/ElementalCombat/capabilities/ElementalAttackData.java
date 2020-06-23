package Tavi007.ElementalCombat.capabilities;
import java.util.HashMap;
import java.util.Map;

public class ElementalAttackData implements IElementalAttackData
{
	private Map<String, Integer> attackMap = new HashMap<String, Integer>();
	
	// Setter
	@Override
	public void setAttackMap(Map<String, Integer> elementMap)
	{
		this.attackMap = elementMap;
	}
	
	// Getter
	@Override
	public Map<String, Integer> getAttackMap()
	{
		return this.attackMap;
	}
}
