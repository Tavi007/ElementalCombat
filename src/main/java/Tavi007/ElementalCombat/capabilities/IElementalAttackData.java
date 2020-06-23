package Tavi007.ElementalCombat.capabilities;
import java.util.Map;

public interface  IElementalAttackData 
{
	public void setAttackMap(Map<String, Integer> elementMap);
	public Map<String, Integer> getAttackMap();
}