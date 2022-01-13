package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DefenseLayer {

    private DefenseMap styleFactor = new DefenseMap();
    private DefenseMap elementFactor = new DefenseMap();

    public DefenseLayer() {
    }

    public DefenseLayer(HashMap<String, Integer> style, HashMap<String, Integer> element) {
        addStyle(style);
        addElement(element);
    }

    public DefenseLayer(DefenseLayer layer) {
        addStyle(layer.getStyleFactor());
        addElement(layer.getElementFactor());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DefenseLayer) {
            DefenseLayer other = (DefenseLayer) object;
            return this.elementFactor.equals(other.elementFactor)
                && this.styleFactor.equals(other.styleFactor);
        }
        return false;
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

    // Other
    public boolean isEmpty() {
        return styleFactor.isEmpty() && elementFactor.isEmpty();
    }

    public String toString() {
        return elementFactor.toString() + " | " + styleFactor.toString();
    }

    private class DefenseMap {

        private HashMap<String, Integer> map = new HashMap<String, Integer>();

        public HashMap<String, Integer> getMap() {
            return map;
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof DefenseMap) {
                DefenseMap other = (DefenseMap) object;
                Set<String> keysThis = this.map.keySet();
                Set<String> keysOther = other.map.keySet();
                if (keysThis.size() == keysOther.size()) {
                    Iterator<String> iteratorThis = keysThis.iterator();
                    while (iteratorThis.hasNext()) {
                        String keyThis = iteratorThis.next();
                        if (!this.map.get(keyThis).equals(other.map.get(keyThis))) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        /**
         * Merges an additional Map into the base map. If both maps contains the same key, the value will be summed up.
         * 
         * @param baseMap
         *            The base mapping. Additional values will be written into this one.
         * @param additionalMap
         *            The additional mapping.
         */
        public void add(HashMap<String, Integer> otherMap) {
            if (otherMap != null) {
                otherMap.forEach((key, value) -> {
                    if (!map.containsKey(key)) {
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
        }

        /**
         * Merges an additional Map into the base map. If both maps contains the same key, the value will be summed up.
         * 
         * @param baseMap
         *            The base mapping. Additional values will be written into this one.
         * @param additionalMap
         *            The additional mapping.
         */
        public void subtract(HashMap<String, Integer> otherMap) {
            if (otherMap != null) {
                otherMap.forEach((key, value) -> {
                    if (!map.containsKey(key)) {
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
        }

        /**
         * Merges an additional Map into the base map. If both maps contains the same key, the highest value will persist.
         * 
         * @param baseMap
         *            The base mapping. Additional values will be written into this one.
         * @param additionalMap
         *            The additional mapping.
         */
        public void merge(HashMap<String, Integer> otherMap) {
            if (otherMap != null) {
                otherMap.forEach((key, value) -> {
                    if (!map.containsKey(key)) {
                        map.put(key, value);
                    } else if (map.get(key) > value) {
                        map.put(key, value);
                    }
                });
            }
        }

        public boolean isEmpty() {
            return map.isEmpty();
        }

        public String toString() {
            return map.toString();
        }
    }
}
