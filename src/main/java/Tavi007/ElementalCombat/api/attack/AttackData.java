package Tavi007.ElementalCombat.api.attack;

import java.util.HashMap;
import java.util.Map;

import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.init.EnchantmentList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AttackData {

	private HashMap<ResourceLocation, AttackLayer> attackLayers = new HashMap<>();
	private boolean isInitialized = false;
	
	// for itemstack
	private boolean areEnchantmentChangesApplied = false;

	public AttackData() {
	}	
	
	public void set(AttackData data) {
		attackLayers = data.getLayers();
	}
	
	public String getElement() {
		for(ResourceLocation rl : attackLayers.keySet()) {
			AttackLayer layer = attackLayers.get(rl);
			String element = layer.getElement();
			if(!element.equals(ServerConfig.getDefaultElement())) {
				return element;
			}
		}
		return ServerConfig.getDefaultElement();
	}
	
	public String getStyle() {
		for(ResourceLocation rl : attackLayers.keySet()) {
			AttackLayer layer = attackLayers.get(rl);
			String style = layer.getStyle();
			if(!style.equals(ServerConfig.getDefaultStyle())) {
				return style;
			}
		}
		return ServerConfig.getDefaultStyle();
	}
	
	public AttackLayer toLayer() {
		return new AttackLayer(getStyle(), getElement());
	}
	
	public AttackLayer getLayer(ResourceLocation rl) {
		return attackLayers.get(rl);
	}
	
	public void putLayer(ResourceLocation rl, AttackLayer layer) {
		attackLayers.put(rl, layer);
	}

	public boolean isDefault() {
		return getElement().equals(ServerConfig.getDefaultElement())
			&& getStyle().equals(ServerConfig.getDefaultStyle());
	}
	
	public void applyEnchantmentChanges(Map<Enchantment, Integer> currentEnchantments) {
		AttackLayer layer = new AttackLayer();
		for (Enchantment ench : currentEnchantments.keySet()) {
			if(ench.getName().equals(Enchantments.FIRE_ASPECT.getName())) {
				layer.setElement("fire");
				break;
			} else if(ench.getName().equals(EnchantmentList.ICE_ASPECT.get().getName())) {
				layer.setElement("ice");
				break;
			} else if(ench.getName().equals(EnchantmentList.WATER_ASPECT.get().getName())) {
				layer.setElement("water");
				break;
			} else if(ench.getName().equals(EnchantmentList.THUNDER_ASPECT.get().getName())) {
				layer.setElement("thunder");
				break;
			} else if(ench.getName().equals(EnchantmentList.DARKNESS_ASPECT.get().getName())) {
				layer.setElement("darkness");
				break;
			} else if(ench.getName().equals(EnchantmentList.LIGHT_ASPECT.get().getName())) {
				layer.setElement("light");
				break;
			} else if(ench.getName().equals(Enchantments.FLAME.getName())) {
				layer.setElement("fire");
				break;
			} else if(ench.getName().equals(Enchantments.CHANNELING.getName())) {
				layer.setElement("thunder");
				break;
			}
		}
		attackLayers.put(new ResourceLocation("enchantment"), layer);
		areEnchantmentChangesApplied = true;
	}
	
	public void initialize(ItemStack stack) {
		AttackLayer base = BasePropertiesAPI.getAttackData(stack);
		attackLayers.put(new ResourceLocation("base"), base);
		isInitialized = true;
	}
	
	public void initialize(LivingEntity entity) {
		AttackLayer base = BasePropertiesAPI.getAttackData(entity);
		attackLayers.put(new ResourceLocation("base"), base);
		isInitialized = true;
	}
	
	public void initialize(ProjectileEntity entity) {
		AttackLayer base = BasePropertiesAPI.getAttackData(entity);
		attackLayers.put(new ResourceLocation("base"), base);
		isInitialized = true;
	}
	
	public boolean isInitialized() {return isInitialized;}
	public boolean areEnchantmentChangesApplied() {return areEnchantmentChangesApplied;}
	public HashMap<ResourceLocation, AttackLayer> getLayers() {return attackLayers;}
}
