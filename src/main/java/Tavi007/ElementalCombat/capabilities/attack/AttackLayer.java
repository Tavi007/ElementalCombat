package Tavi007.ElementalCombat.capabilities.attack;

import Tavi007.ElementalCombat.config.ServerConfig;

public class AttackLayer {
	private String style = ServerConfig.getDefaultStyle();
	private String element = ServerConfig.getDefaultElement();

	public AttackLayer() {}
	
	public AttackLayer(String style, String element) {
		if(style == null || style.isEmpty()) {
			this.style = ServerConfig.getDefaultStyle();
		}
		else {
			this.style = style;
		}
		if(element == null || element.isEmpty()) {
			this.element = ServerConfig.getDefaultElement();
		}
		else {
			this.element = element;
		}
	}

	public AttackLayer(AttackLayer data) {
		this(data.getStyle(), data.getElement());
	}

	public void set(AttackLayer data) {
		this.style = data.getStyle();
		this.element = data.getElement();
	}

	public void set(String style, String element) {
		AttackLayer layer = new AttackLayer(style, element);
		set(layer);
	}

	public void setElement(String element) {
		if(element == null || element.isEmpty()) {
			this.element = ServerConfig.getDefaultElement();
		}
		else {
			this.element = element;
		}
	}

	public void setStyle(String style) {
		if(style == null || style.isEmpty()) {
			this.style = ServerConfig.getDefaultStyle();
		}
		else {
			this.style = style;
		}
	}

	public String getElement() {return this.element;}
	public String getStyle() {return this.style;}
	
	public boolean isDefault() {
		return (element.isEmpty() || element.equals(ServerConfig.getDefaultElement())) && (style.isEmpty() || style.equals(ServerConfig.getDefaultStyle()));
	}
}
