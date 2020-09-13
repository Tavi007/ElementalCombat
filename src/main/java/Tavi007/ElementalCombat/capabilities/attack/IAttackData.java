package Tavi007.ElementalCombat.capabilities.attack;

public interface IAttackData {
	void setAttackData(String style, String element);
	
	void setElement(String element);
	String getElement();
	
	void setStyle(String style);
	String getStyle();
}
