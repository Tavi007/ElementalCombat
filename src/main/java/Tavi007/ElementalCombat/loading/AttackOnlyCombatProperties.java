package Tavi007.ElementalCombat.loading;

import Tavi007.ElementalCombat.config.ServerConfig;

public class AttackOnlyCombatProperties {
	private final String attack_style;
	private final String attack_element;
	
	public AttackOnlyCombatProperties() {
		this.attack_style = ServerConfig.getDefaultStyle();
		this.attack_element = ServerConfig.getDefaultElement();
	}
	
	public AttackOnlyCombatProperties(String attack_style, String attack_element) {
		   if (attack_style.isEmpty()) {
			   this.attack_style = ServerConfig.getDefaultStyle();
		   }
		   else {
			   this.attack_style = attack_style;
		   }
		   if (attack_element.isEmpty()) {
			   this.attack_element = ServerConfig.getDefaultElement();
		   }
		   else {
			   this.attack_element = attack_element;
		   }
	}
	
	public AttackOnlyCombatProperties(AttackOnlyCombatProperties properties) {
		this.attack_element = properties.getAttackElement();
		this.attack_style = properties.getAttackStyle();
	}

	public String getAttackStyle() {return this.attack_style;}
	public String getAttackElement() {return this.attack_element;}
	
	public boolean isEmpty() {
		return attack_style.equals(ServerConfig.getDefaultStyle()) && attack_element.equals(ServerConfig.getDefaultElement());
	}
}
