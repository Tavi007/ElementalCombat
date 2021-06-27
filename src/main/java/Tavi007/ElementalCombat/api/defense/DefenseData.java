package Tavi007.ElementalCombat.api.defense;

import java.util.HashMap;
import java.util.Map;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.init.EnchantmentList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DefenseData {

	private HashMap<ResourceLocation, DefenseLayer> defenseLayers = new HashMap<ResourceLocation, DefenseLayer>();
	private boolean isInitialized = false;

	// for itemstack
	private boolean areEnchantmentChangesApplied = false;
	
	public DefenseData() {
	}
	
	public void set(DefenseData data) {
		this.defenseLayers = data.defenseLayers;
		this.isInitialized = data.isInitialized;
	}
	
	public HashMap<ResourceLocation, DefenseLayer> getLayers() {
		return defenseLayers;
	}
	
	public DefenseLayer getLayer(ResourceLocation name) {
		return defenseLayers.get(name);
	}
	
	public void putLayer(DefenseLayer layer, ResourceLocation name) {
		defenseLayers.put(name, layer);
	}
	
	public boolean areEnchantmentChangesApplied() {
		return areEnchantmentChangesApplied;
	}

	public void applyEnchantmentChanges(Map<Enchantment, Integer> enchantments) {

		HashMap<String, Integer> defElement = new HashMap<String, Integer>();
		HashMap<String, Integer> defStyle = new HashMap<String, Integer>();
		enchantments.forEach( (key, level) -> {
			if (level != 0) {
				// elemental enchantments
				if(key == Enchantments.FIRE_PROTECTION) {
					defElement.put( "fire", level*ServerConfig.getEnchantmentScaling());
					defElement.put( "ice", -level*ServerConfig.getEnchantmentScaling()/2);
				}
				else if(key == EnchantmentList.ICE_PROTECTION.get()) {
					defElement.put("ice", level*ServerConfig.getEnchantmentScaling());
					defElement.put("fire", -level*ServerConfig.getEnchantmentScaling()/2);
				}
				else if(key == EnchantmentList.WATER_PROTECTION.get()) {
					defElement.put( "water", level*ServerConfig.getEnchantmentScaling());
					defElement.put( "thunder", -level*ServerConfig.getEnchantmentScaling()/2);
				}
				else if(key == EnchantmentList.THUNDER_PROTECTION.get()) {
					defElement.put( "thunder", level*ServerConfig.getEnchantmentScaling());
					defElement.put( "water", -level*ServerConfig.getEnchantmentScaling()/2);
				}
				else if(key == EnchantmentList.DARKNESS_PROTECTION.get()) {
					defElement.put( "darkness", level*ServerConfig.getEnchantmentScaling());
					defElement.put( "light", -level*ServerConfig.getEnchantmentScaling()/2);
				}
				else if(key == EnchantmentList.LIGHT_PROTECTION.get()) {
					defElement.put( "light", level*ServerConfig.getEnchantmentScaling());
					defElement.put( "darkness", -level*ServerConfig.getEnchantmentScaling()/2);
				}
				else if(key == EnchantmentList.ELEMENT_PROTECTION.get()) {
					defElement.put( "fire", level*ServerConfig.getEnchantmentScaling()/5);
					defElement.put( "water", level*ServerConfig.getEnchantmentScaling()/5);
					defElement.put( "ice", level*ServerConfig.getEnchantmentScaling()/5);
					defElement.put( "thunder", level*ServerConfig.getEnchantmentScaling()/5);
				}

				// style enchantments
				if(key == Enchantments.BLAST_PROTECTION) {
					defStyle.put("explosion", level*ServerConfig.getEnchantmentScaling());
				}
				else if(key == Enchantments.PROJECTILE_PROTECTION) {
					defStyle.put("projectile", level*ServerConfig.getEnchantmentScaling());
				}
				else if(key == EnchantmentList.MAGIC_PROTECTION.get()) {
					defStyle.put("magic", level*ServerConfig.getEnchantmentScaling());
				}
			}
		});
		DefenseLayer layer = new DefenseLayer(defStyle, defElement);
		if (!layer.isEmpty()) {
			defenseLayers.put(new ResourceLocation("minecraft","enchantment"), layer);
		}
		areEnchantmentChangesApplied = true;
	}
	
	public DefenseLayer toLayer() {
		DefenseLayer layer = new DefenseLayer();
		layer.addElement(getElementFactor());
		layer.addStyle(getStyleFactor());
		return layer;
	}

	public HashMap<String, Integer> getStyleFactor() {
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		defenseLayers.forEach((name, layer) -> {
			ret.putAll(layer.getStyleFactor());
		});
		return ret;
	}
	
	public HashMap<String, Integer> getElementFactor() {
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		defenseLayers.forEach((name, layer) -> {
			ret.putAll(layer.getElementFactor());
		});
		return ret;
	}
	
	public boolean isEmpty() {
		return getStyleFactor().isEmpty() && getElementFactor().isEmpty();
	}
	
	public String toString() {
		return toLayer().toString();
	}
	

	public void initialize(ItemStack stack) {
		isInitialized = true;
		putLayer(BasePropertiesAPI.getDefenseLayer(stack), new ResourceLocation(ElementalCombat.MOD_ID, "base"));
	}
	
	public void initialize(LivingEntity entity) {
		isInitialized = true;
		putLayer(BasePropertiesAPI.getDefenseLayer(entity), new ResourceLocation(ElementalCombat.MOD_ID, "base"));
	}
	
	public boolean isInitialized() {return isInitialized;}

	public void clear() {
		defenseLayers.clear();
	}
}
