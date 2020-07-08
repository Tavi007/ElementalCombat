package Tavi007.ElementalCombat.capabilities;
import java.util.HashMap;
import java.util.Map;

public class ElementalAttack
{
	private Map<String, Integer> attackMap = new HashMap<String, Integer>();
	
	// Setter
	public void setAttackMap(Map<String, Integer> elementMap)
	{
		this.attackMap = elementMap;
	}
	
	// Getter
	public Map<String, Integer> getAttackMap()
	{
		return this.attackMap;
	}
}
