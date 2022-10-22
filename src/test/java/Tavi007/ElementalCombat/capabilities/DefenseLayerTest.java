package Tavi007.ElementalCombat.capabilities;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;

public class DefenseLayerTest {

    @Test
    public void newObjectDefault() {
        DefenseLayer layer = new DefenseLayer();
        assertEquals("isEmpty not returning true", true, layer.isEmpty());
        assertEquals("element factor map is not empty", new HashMap<String, Integer>(), layer.getElementFactor());
        assertEquals("style factor map is not empty", new HashMap<String, Integer>(), layer.getElementFactor());
    }

    @Test
    public void newObjectWithNull() {
        DefenseLayer layer = new DefenseLayer(null, null);
        assertEquals("isEmpty not returning true", true, layer.isEmpty());
        assertEquals("element factor map is not empty", new HashMap<String, Integer>(), layer.getElementFactor());
        assertEquals("style factor map is not empty", new HashMap<String, Integer>(), layer.getElementFactor());
    }

    @Test
    public void newObjectWithEmpty() {
        DefenseLayer layer = new DefenseLayer(new HashMap<String, Integer>(), new HashMap<String, Integer>());
        assertEquals("isEmpty not returning true", true, layer.isEmpty());
        assertEquals("element factor map is not empty", new HashMap<String, Integer>(), layer.getElementFactor());
        assertEquals("style factor map is not empty", new HashMap<String, Integer>(), layer.getElementFactor());
    }

    @Test
    public void newObjectWithNonEmpty() {
        HashMap<String, Integer> style = new HashMap<String, Integer>();
        style.put("style", 1);
        HashMap<String, Integer> element = new HashMap<String, Integer>();
        element.put("element", 1);
        DefenseLayer layer = new DefenseLayer(style, element);
        assertEquals("isEmpty not returning false", false, layer.isEmpty());
        assertEquals("element factor map is incorrect ", element, layer.getElementFactor());
        assertEquals("style factor map is incorrect", style, layer.getStyleFactor());
    }

    @Test
    public void testEquals() {
        DefenseLayer layer1 = new DefenseLayer();
        DefenseLayer layer2 = new DefenseLayer(null, null);
        DefenseLayer layer3 = new DefenseLayer(new HashMap<String, Integer>(), new HashMap<String, Integer>());

        assertEquals("layer1 does not equal layer2", true, layer1.equals(layer2));
        assertEquals("layer2 does not equal layer1", true, layer2.equals(layer1));

        assertEquals("layer1 does not equal layer3", true, layer1.equals(layer3));
        assertEquals("layer3 does not equal layer1", true, layer3.equals(layer1));

        assertEquals("layer2 does not equal layer3", true, layer2.equals(layer3));
        assertEquals("layer3 does not equal layer2", true, layer3.equals(layer2));
    }

    @Test
    public void testAddLayer() {
        HashMap<String, Integer> style1 = new HashMap<String, Integer>();
        style1.put("style1", 1);
        HashMap<String, Integer> element1 = new HashMap<String, Integer>();
        element1.put("element1", 2);
        DefenseLayer layer1 = new DefenseLayer(style1, element1);

        HashMap<String, Integer> style2 = new HashMap<String, Integer>();
        style2.put("style1", 10);
        style2.put("style2", 10);
        HashMap<String, Integer> element2 = new HashMap<String, Integer>();
        element2.put("element1", 20);
        element2.put("element2", 20);
        DefenseLayer layer2 = new DefenseLayer(style2, element2);

        layer1.addLayer(layer2);
        assertEquals("layer1-style1 is not 11", 11, layer1.getStyleFactor().get("style1").intValue());
        assertEquals("layer1-style2 is not 10", 10, layer1.getStyleFactor().get("style2").intValue());
        assertEquals("layer1-element1 is not 22", 22, layer1.getElementFactor().get("element1").intValue());
        assertEquals("layer1-element2 is not 20", 20, layer1.getElementFactor().get("element2").intValue());

        // layer2 should not change
        assertEquals("layer2-style1 is not 11", 10, layer2.getStyleFactor().get("style1").intValue());
        assertEquals("layer2-style2 is not 10", 10, layer2.getStyleFactor().get("style2").intValue());
        assertEquals("layer2-element1 is not 22", 20, layer2.getElementFactor().get("element1").intValue());
        assertEquals("layer2-element2 is not 20", 20, layer2.getElementFactor().get("element2").intValue());
    }

    @Test
    public void testSubtractLayer() {
        HashMap<String, Integer> style1 = new HashMap<String, Integer>();
        style1.put("style1", 1);
        HashMap<String, Integer> element1 = new HashMap<String, Integer>();
        element1.put("element1", 2);
        DefenseLayer layer1 = new DefenseLayer(style1, element1);

        HashMap<String, Integer> style2 = new HashMap<String, Integer>();
        style2.put("style1", 10);
        style2.put("style2", 10);
        HashMap<String, Integer> element2 = new HashMap<String, Integer>();
        element2.put("element1", 20);
        element2.put("element2", 20);
        DefenseLayer layer2 = new DefenseLayer(style2, element2);

        layer1.subtractLayer(layer2);
        assertEquals("layer1-style1 is not -9", -9, layer1.getStyleFactor().get("style1").intValue());
        assertEquals("layer1-style2 is not -10", -10, layer1.getStyleFactor().get("style2").intValue());
        assertEquals("layer1-element1 is not -18", -18, layer1.getElementFactor().get("element1").intValue());
        assertEquals("layer1-element2 is not -20", -20, layer1.getElementFactor().get("element2").intValue());

        // layer2 should not change
        assertEquals("layer2-style1 is not 11", 10, layer2.getStyleFactor().get("style1").intValue());
        assertEquals("layer2-style2 is not 10", 10, layer2.getStyleFactor().get("style2").intValue());
        assertEquals("layer2-element1 is not 22", 20, layer2.getElementFactor().get("element1").intValue());
        assertEquals("layer2-element2 is not 20", 20, layer2.getElementFactor().get("element2").intValue());
    }

    @Test
    public void testMergeLayer() {
        HashMap<String, Integer> style1 = new HashMap<String, Integer>();
        style1.put("style1", 1);
        HashMap<String, Integer> element1 = new HashMap<String, Integer>();
        element1.put("element1", 2);
        DefenseLayer layer1 = new DefenseLayer(style1, element1);

        HashMap<String, Integer> style2 = new HashMap<String, Integer>();
        style2.put("style1", 10);
        style2.put("style2", 10);
        HashMap<String, Integer> element2 = new HashMap<String, Integer>();
        element2.put("element1", 20);
        element2.put("element2", 20);
        DefenseLayer layer2 = new DefenseLayer(style2, element2);

        layer1.mergeLayer(layer2); // merging only adds new/unknown values to the mapping
        assertEquals("layer1-style1 is not 1", 1, layer1.getStyleFactor().get("style1").intValue());
        assertEquals("layer1-style2 is not 10", 10, layer1.getStyleFactor().get("style2").intValue());
        assertEquals("layer1-element1 is not 2", 2, layer1.getElementFactor().get("element1").intValue());
        assertEquals("layer1-element2 is not 20", 20, layer1.getElementFactor().get("element2").intValue());

        // layer2 should not change
        assertEquals("layer2-style1 is not 11", 10, layer2.getStyleFactor().get("style1").intValue());
        assertEquals("layer2-style2 is not 10", 10, layer2.getStyleFactor().get("style2").intValue());
        assertEquals("layer2-element1 is not 22", 20, layer2.getElementFactor().get("element1").intValue());
        assertEquals("layer2-element2 is not 20", 20, layer2.getElementFactor().get("element2").intValue());
    }
}
