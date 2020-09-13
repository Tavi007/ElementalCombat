package Tavi007.ElementalCombat.capabilities.attack;

public class AttackData implements IAttackData{

	private String style = "basic";
	private String element = "natural";
	
	public AttackData() {
	}
	
	public AttackData(String style, String element) {
		this.style = style;
		this.element = element;
	}
	
	public AttackData(AttackData data) {
		this.style = data.getStyle();
		this.element = data.getElement();
	}

	@Override
	public void setAttackData(String style, String element) {
		this.style = style;
		this.element = element;
	}
	
	@Override
	public String getElement() {return this.element;}

	@Override
	public String getStyle() {return this.style;}

	@Override
	public void setElement(String element) {this.element = element;}

	@Override
	public void setStyle(String style) {this.style = style;}
}
