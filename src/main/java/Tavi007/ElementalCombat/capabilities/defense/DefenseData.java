package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

	private HashMap<ResourceLocation, DefenseLayer> defenseLayers = new HashMap<>();
	private boolean isInitialized = false;

	// for itemstack
	private boolean areEnchantmentChangesApplied = false;
	
	public DefenseData() {
	}
	
	public void set(DefenseData data) {
		this.defenseLayers = data.defenseLayers;
		this.isInitialized = data.isInitialized;
	}

	public HashMap<String, Integer> getStyleFactor() {
		DefenseLayer sum = new DefenseLayer();
		defenseLayers.forEach((rl, layer) -> {
			sum.addStyle(layer.getStyleFactor());
		});
		return sum.getStyleFactor();
	}
	
	public HashMap<String, Integer> getElementFactor() {
		DefenseLayer sum = new DefenseLayer();
		defenseLayers.forEach((rl, layer) -> {
			sum.addElement(layer.getElementFactor());
		});
		return sum.getElementFactor();
	}
	
	public DefenseLayer toLayer() {
		DefenseLayer layer = new DefenseLayer();
		layer.addElement(getElementFactor());
		layer.addStyle(getStyleFactor());
		return layer;
	}
	
	public DefenseLayer getLayer(ResourceLocation rl) {
		if(defenseLayers.containsKey(rl)) {
			return defenseLayers.get(rl);
		} else {
			return new DefenseLayer();
		}
	}
	
	public void putLayer(ResourceLocation rl, DefenseLayer layer) {
		if(layer.isEmpty()) {
			defenseLayers.remove(rl);
		} else {
			defenseLayers.put(rl, layer);
		}
	}
	
	public boolean isEmpty() {
		return getStyleFactor().isEmpty() && getElementFactor().isEmpty();
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
	
	@Override
	public String toString() {
		return toLayer().toString();
	}
	
	public void initialize(ItemStack stack) {
		isInitialized = true;
		putLayer(new ResourceLocation(ElementalCombat.MOD_ID, "base"), BasePropertiesAPI.getDefenseLayer(stack));
	}
	
	public void initialize(LivingEntity entity) {
		isInitialized = true;
		putLayer(new ResourceLocation(ElementalCombat.MOD_ID, "base"), BasePropertiesAPI.getDefenseLayer(entity));
	}
	
	public boolean isInitialized() {return isInitialized;}
	public HashMap<ResourceLocation, DefenseLayer> getLayers() {return defenseLayers;}
	public boolean areEnchantmentChangesApplied() {return areEnchantmentChangesApplied;}

	public void clear() {
		defenseLayers.clear();
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof DefenseData) {
			DefenseData other = (DefenseData) object;
			Set<ResourceLocation> keysThis = this.defenseLayers.keySet();
			Set<ResourceLocation> keysOther = other.defenseLayers.keySet();
			if(keysThis.size() == keysOther.size()) {
				Iterator<ResourceLocation> iteratorThis = keysThis.iterator();
				while(iteratorThis.hasNext()) {
					ResourceLocation keyThis = iteratorThis.next();
					DefenseLayer layerThis = this.defenseLayers.get(keyThis);
					DefenseLayer layerOther = other.defenseLayers.get(keyThis);
					if(!layerThis.equals(layerOther)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
}
