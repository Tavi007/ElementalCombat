package Tavi007.ElementalCombat.api.defense;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DefenseData {

	private HashMap<ResourceLocation, DefenseLayer> defenseLayers = new HashMap<ResourceLocation, DefenseLayer>();
	private boolean isInitialized = false;

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
	
	public void addLayer(DefenseLayer layer, ResourceLocation name) {
		defenseLayers.put(name, layer);
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
	

	public void initialize(ItemStack stack) {
		isInitialized = true;
		addLayer(BasePropertiesAPI.getDefenseLayer(stack), new ResourceLocation(ElementalCombat.MOD_ID, "base"));
	}
	
	public void initialize(LivingEntity entity) {
		isInitialized = true;
		addLayer(BasePropertiesAPI.getDefenseLayer(entity), new ResourceLocation(ElementalCombat.MOD_ID, "base"));
	}
	
	public boolean isInitialized() {return isInitialized;}

	public void clear() {
		defenseLayers.clear();
	}
}
