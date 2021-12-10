package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import net.minecraft.enchantment.Enchantment;
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
		DefenseData data = new DefenseData();
		enchantments.forEach( (ench, level) -> {
			data.putLayer(new ResourceLocation(ench.getName()), BasePropertiesAPI.getDefenseLayer(ench, level));
		});
		if (!data.isEmpty()) {
			defenseLayers.put(new ResourceLocation("enchantment"), data.toLayer());
		}
		areEnchantmentChangesApplied = true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("isInitialized: " + isInitialized + "\n");
		builder.append("areEnchantmentChangesApplied: " + areEnchantmentChangesApplied + "\n");
		builder.append("as layer: " + toLayer().toString() + "\n");
		builder.append("layers: \n");
		defenseLayers.forEach((rl, layer) -> {
			builder.append(rl.toString() + ":" + layer.toString() + "\n");
		});
		return builder.toString();
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
