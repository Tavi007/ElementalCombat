package Tavi007.ElementalCombat.capabilities.attack;

import Tavi007.ElementalCombat.config.ServerConfig;

public class AttackLayer {
	private String style = ServerConfig.getDefaultStyle();
	private String element = ServerConfig.getDefaultElement();

	public AttackLayer() {}
	
	public AttackLayer(String style, String element) {
		if(style.isEmpty()) {
			this.style = ServerConfig.getDefaultStyle();
		}
		else {
			this.style = style;
		}
		if(element.isEmpty()) {
			this.element = ServerConfig.getDefaultElement();
		}
		else {
			this.element = element;
		}
	}

	public AttackLayer(AttackLayer data) {
		this.style = data.getStyle();
		this.element = data.getElement();
	}

	public void set(AttackLayer data) {
		if(data.getStyle().isEmpty()) {
			this.style = ServerConfig.getDefaultStyle();
		}
		else {
			this.style = data.getStyle();
		}
		if(data.getElement().isEmpty()) {
			this.element = ServerConfig.getDefaultElement();
		}
		else {
			this.element = data.getElement();
		}
	}

	public void set(String style, String element) {
		this.style = style;
		this.element = element;
	}

	public void setElement(String element) {
		if(element.isEmpty()) {
			this.element = ServerConfig.getDefaultElement();
		}
		else {
			this.element = element;
		}
	}

	public void setStyle(String style) {
		if(style.isEmpty()) {
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
