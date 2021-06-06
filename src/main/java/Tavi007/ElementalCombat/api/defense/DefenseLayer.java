package Tavi007.ElementalCombat.api.defense;

import java.util.HashMap;

public class DefenseLayer {
	private DefenseMap styleFactor = new DefenseMap();
	private DefenseMap elementFactor = new DefenseMap();

	public DefenseLayer() {}
	public DefenseLayer(HashMap<String, Integer> style, HashMap<String, Integer> element) {
		addStyle(style);
		addElement(element);
	}
	
	public HashMap<String, Integer> getStyleFactor() {
		return styleFactor.getMap();
	}
	public HashMap<String, Integer> getElementFactor() {
		return elementFactor.getMap();
	}

	// Adders
	public void addLayer(DefenseLayer layer) {
		addStyle(layer.getStyleFactor());
		addElement(layer.getElementFactor());
	}

	public void addStyle(HashMap<String, Integer> map) {
		styleFactor.add(map);
	}

	public void addElement(HashMap<String, Integer> map) {
		elementFactor.add(map);
	}

	// Subtractor
	public void subtractLayer(DefenseLayer layer) {
		subtractStyle(layer.getStyleFactor());
		subtractElement(layer.getElementFactor());
	}
	
	public void subtractStyle(HashMap<String, Integer> map) {
		styleFactor.subtract(map);
	}

	public void subtractElement(HashMap<String, Integer> map) {
		elementFactor.subtract(map);
	}

	// Merger
	public void mergeLayer(DefenseLayer layer) {
		mergeStyle(layer.getStyleFactor());
		mergeElement(layer.getElementFactor());
	}
	public void mergeStyle(HashMap<String, Integer> map) {
		styleFactor.merge(map);
	}

	public void mergeElement(HashMap<String, Integer> map) {
		elementFactor.merge(map);
	}
	
	//Other
	public boolean isEmpty() {
		return styleFactor.isEmpty() && elementFactor.isEmpty();
	}
	
	private class DefenseMap {
		private HashMap<String, Integer> map;

		public HashMap<String, Integer> getMap() {
			return map;
		}

		/**
		 * Merges an additional Map into the base map. If both maps contains the same key, the value will be summed up.
		 * @param baseMap The base mapping. Additional values will be written into this one.
		 * @param additionalMap The additional mapping.
		 */
		public void add(HashMap<String, Integer> otherMap){
			otherMap.forEach((key, value)->{
				if(!map.containsKey(key)) {
					map.put(key, value);
				} else {
					int newValue = map.get(key) + value;
					if (newValue == 0) {
						map.remove(key);
					} else {
						map.put(key, newValue);
					}
				}
			});
		}

		/**
		 * Merges an additional Map into the base map. If both maps contains the same key, the value will be summed up.
		 * @param baseMap The base mapping. Additional values will be written into this one.
		 * @param additionalMap The additional mapping.
		 */
		public void subtract(HashMap<String, Integer> otherMap){
			otherMap.forEach((key, value)->{
				if(!map.containsKey(key)) {
					map.put(key, value);
				} else {
					int newValue = map.get(key) - value;
					if (newValue == 0) {
						map.remove(key);
					} else {
						map.put(key, newValue);
					}
				}
			});
		}
		
		/**
		 * Merges an additional Map into the base map. If both maps contains the same key, the highest value will persist.
		 * @param baseMap The base mapping. Additional values will be written into this one.
		 * @param additionalMap The additional mapping.
		 */
		public void merge(HashMap<String, Integer> otherMap){
			otherMap.forEach((key, value)->{
				if(!map.containsKey(key)) {
					map.put(key, value);
				} else if(map.get(key) > value) {
					map.put(key, value);
				}
			});	
		}
		
		public boolean isEmpty() {
			return map.isEmpty();
		}
	}
}