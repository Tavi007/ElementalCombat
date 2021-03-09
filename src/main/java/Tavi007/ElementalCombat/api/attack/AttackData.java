package Tavi007.ElementalCombat.api.attack;

import java.util.Map;

import Tavi007.ElementalCombat.api.DefaultPropertiesAPI;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.init.EnchantmentList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;

public class AttackData {

	private String style = ServerConfig.getDefaultStyle();
	private String element = ServerConfig.getDefaultElement();

	private boolean isInitialized = false;
	
	// for itemstack
	private boolean areEnchantmentChangesApplied = false;

	public AttackData() {
	}

	public AttackData(String style, String element) {
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

	public AttackData(AttackData data) {
		this.style = data.getStyle();
		this.element = data.getElement();
	}

	public void set(AttackData data) {
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

	public String getElement() {return this.element;}

	public String getStyle() {return this.style;}

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

	public boolean areEnchantmentChangesApplied() {
		return this.areEnchantmentChangesApplied;
	}

	public void applyEnchantmentChanges(Map<Enchantment, Integer> currentEnchantments) {
		currentEnchantments.forEach((ench, value) -> {
			//sword
			if(ench.getName().equals(Enchantments.FIRE_ASPECT.getName())) {this.setElement("fire");}
			else if(ench.getName().equals(EnchantmentList.ICE_ASPECT.get().getName())) {this.setElement("ice");}
			else if(ench.getName().equals(EnchantmentList.WATER_ASPECT.get().getName())) {this.setElement("water");}
			else if(ench.getName().equals(EnchantmentList.THUNDER_ASPECT.get().getName())) {this.setElement("thunder");}
			else if(ench.getName().equals(EnchantmentList.DARKNESS_ASPECT.get().getName())) {this.setElement("darkness");}
			else if(ench.getName().equals(EnchantmentList.LIGHT_ASPECT.get().getName())) {this.setElement("light");}
			//bow
			else if(ench.getName().equals(Enchantments.FLAME.getName())) {this.setElement("fire");}
			//trident
			else if(ench.getName().equals(Enchantments.CHANNELING.getName())) {this.setElement("thunder");}
		});

		this.areEnchantmentChangesApplied = true;
	}

	public boolean isEmpty() {
		return (element.isEmpty() || element.equals(ServerConfig.getDefaultElement())) && (style.isEmpty() || style.equals(ServerConfig.getDefaultStyle()));
	}
	
	public void initialize(ItemStack stack) {
		isInitialized = true;
		this.set(DefaultPropertiesAPI.getAttackData(stack));
	}
	
	public void initialize(LivingEntity entity) {
		isInitialized = true;
		this.set(DefaultPropertiesAPI.getAttackData(entity));
	}
	
	public void initialize(ProjectileEntity entity) {
		isInitialized = true;
		this.set(DefaultPropertiesAPI.getAttackData(entity));
	}
	
	public boolean isInitialized() {return isInitialized;}
}
