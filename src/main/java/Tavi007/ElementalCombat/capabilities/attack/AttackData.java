package Tavi007.ElementalCombat.capabilities.attack;

import java.util.Map;

import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.enchantments.CombatEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

public class AttackData {

	private String style = ServerConfig.getDefaultStyle();
	private String element = ServerConfig.getDefaultElement();

	// for itemstack
	private boolean areEnchantmentChangesApplied = false;

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

	public void set(AttackData data) {
		this.style = data.getStyle();
		this.element = data.getElement();
	}

	public void set(String style, String element) {
		this.style = style;
		this.element = element;
	}

	public String getElement() {return this.element;}

	public String getStyle() {return this.style;}

	public void setElement(String element) {this.element = element;}

	public void setStyle(String style) {this.style = style;}

	public boolean areEnchantmentChangesApplied() {
		return this.areEnchantmentChangesApplied;
	}
	
	public void applyEnchantmentChanges(Map<Enchantment, Integer> currentEnchantments) {
		currentEnchantments.forEach((ench, value) -> {
			//currently only comparing strings.
			//maybe change to resourceLocation later, so other mods can interact with this as well.
			if(ServerConfig.isEmojiEnabled()) {
				//sword
				if(ench.getName().equals(Enchantments.FIRE_ASPECT.getName())) {this.setElement("🔥");}
				if(ench.getName().equals(CombatEnchantments.ICE_ASPECT.getName())) {this.setElement("❄");}
				if(ench.getName().equals(CombatEnchantments.WATER_ASPECT.getName())) {this.setElement("💧");}
				if(ench.getName().equals(CombatEnchantments.THUNDER_ASPECT.getName())) {this.setElement("⚡");}
				//bow
				if(ench.getName().equals(Enchantments.FLAME.getName())) {this.setElement("🔥");}
				//trident
				if(ench.getName().equals(Enchantments.CHANNELING.getName())) {this.setElement("⚡");}
			}
			else {
				//sword
				if(ench.getName().equals(Enchantments.FIRE_ASPECT.getName())) {this.setElement("fire");}
				if(ench.getName().equals(CombatEnchantments.ICE_ASPECT.getName())) {this.setElement("ice");}
				if(ench.getName().equals(CombatEnchantments.WATER_ASPECT.getName())) {this.setElement("water");}
				if(ench.getName().equals(CombatEnchantments.THUNDER_ASPECT.getName())) {this.setElement("thunder");}
				//bow
				if(ench.getName().equals(Enchantments.FLAME.getName())) {this.setElement("fire");}
				//trident
				if(ench.getName().equals(Enchantments.CHANNELING.getName())) {this.setElement("thunder");}
			}
		});

		this.areEnchantmentChangesApplied = true;
	}

	public boolean isEmpty() {
		return (element.isEmpty() || element.equals(ServerConfig.getDefaultElement())) && (style.isEmpty() || style.equals(ServerConfig.getDefaultStyle()));
	}
}
