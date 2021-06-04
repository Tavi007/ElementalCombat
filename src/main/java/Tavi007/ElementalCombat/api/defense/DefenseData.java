package Tavi007.ElementalCombat.api.defense;

import java.util.HashMap;

public class DefenseData {

	private HashMap<String, DefenseLayer> defenseLayers = new HashMap<String, DefenseLayer>();

	public DefenseData() {
	}
	
	public DefenseLayer getLayer(String name) {
		return defenseLayers.get(name);
	}
	
	public void addLayer(DefenseLayer layer, String name) {
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
}
