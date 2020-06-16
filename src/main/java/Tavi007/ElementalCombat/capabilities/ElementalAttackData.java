package Tavi007.ElementalCombat.capabilities;
import java.util.HashSet;
import java.util.Set;

public class ElementalAttackData implements IElementalAttackData
{
	private Set<String> attackSet = new HashSet<String>();
	
	// Setter
	@Override
	public void setAttackSet(Set<String> elementSet)
	{
		this.attackSet = elementSet;
	}
	
	// Getter
	@Override
	public Set<String> getAttackSet()
	{
		return this.attackSet;
	}
}
