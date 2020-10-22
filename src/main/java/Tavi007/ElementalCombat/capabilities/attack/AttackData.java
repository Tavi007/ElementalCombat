package Tavi007.ElementalCombat.capabilities.attack;

import java.util.Map;

import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.enchantments.CombatEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

public class AttackData implements IAttackData{

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

	@Override
	public boolean areEnchantmentChangesApplied() {
		return this.areEnchantmentChangesApplied;
	}

	@Override
	public void applyEnchantmentChanges(Map<Enchantment, Integer> currentEnchantments) {
		currentEnchantments.forEach((ench, value) -> {
			//currently only comparing strings.
			//maybe change to resourceLocation later, so other mods can interact with this as well.
			if(ServerConfig.isEmojiEnabled()) {
				//sword
				if(ench.getName() == Enchantments.FIRE_ASPECT.getName()) {this.setElement("🔥");}
				if(ench.getName() == CombatEnchantments.ICE_ASPECT.getName()) {this.setElement("❄");}
				if(ench.getName() == CombatEnchantments.WATER_ASPECT.getName()) {this.setElement("💧");}
				if(ench.getName() == CombatEnchantments.THUNDER_ASPECT.getName()) {this.setElement("⚡");}
				//bow
				if(ench.getName() == Enchantments.FLAME.getName()) {this.setElement("🔥");}
				//trident
				if(ench.getName() == Enchantments.CHANNELING.getName()) {this.setElement("⚡");}
			}
			else {
				//sword
				if(ench.getName() == Enchantments.FIRE_ASPECT.getName()) {this.setElement("fire");}
				if(ench.getName() == CombatEnchantments.ICE_ASPECT.getName()) {this.setElement("ice");}
				if(ench.getName() == CombatEnchantments.WATER_ASPECT.getName()) {this.setElement("water");}
				if(ench.getName() == CombatEnchantments.THUNDER_ASPECT.getName()) {this.setElement("thunder");}
				//bow
				if(ench.getName() == Enchantments.FLAME.getName()) {this.setElement("fire");}
				//trident
				if(ench.getName() == Enchantments.CHANNELING.getName()) {this.setElement("thunder");}
			}
		});

		this.areEnchantmentChangesApplied = true;
	}
}
