package Tavi007.ElementalCombat.capabilities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;

@Ignore
public class AttackLayerTest {

    @Before
    public void init() {
        System.getProperties().put("production", "1");
    }

    @Test
    public void newObjectDefault() {
        AttackLayer layer = new AttackLayer();
        assertEquals("isDefault not returning true", true, layer.isDefault());
        assertEquals("incorrect Element", "normal", layer.getElement());
        assertEquals("incorrect Style", "hit", layer.getStyle());
    }

    @Test
    public void newObjectWithNull() {
        AttackLayer layer = new AttackLayer(null, null);
        assertEquals("isDefault not returning true", true, layer.isDefault());
        assertEquals("incorrect Element", "normal", layer.getElement());
        assertEquals("incorrect Style", "hit", layer.getStyle());
    }

    @Test
    public void newObjectWithEmpty() {
        AttackLayer layer = new AttackLayer("", " ");
        assertEquals("isDefault not returning true", true, layer.isDefault());
        assertEquals("incorrect Element", "normal", layer.getElement());
        assertEquals("incorrect Style", "hit", layer.getStyle());
    }

    @Test
    public void newObjectWithNonEmpty() {
        AttackLayer layer = new AttackLayer("style", "element");
        assertEquals("isDefault not returning false", false, layer.isDefault());
        assertEquals("incorrect Element", "element", layer.getElement());
        assertEquals("incorrect Style", "style", layer.getStyle());
    }

    @Test
    public void testEquals() {
        AttackLayer layer1 = new AttackLayer();
        AttackLayer layer2 = new AttackLayer(null, null);
        AttackLayer layer3 = new AttackLayer("", "");

        assertEquals("layer1 does not equal layer2", true, layer1.equals(layer2));
        assertEquals("layer2 does not equal layer1", true, layer2.equals(layer1));

        assertEquals("layer1 does not equal layer3", true, layer1.equals(layer3));
        assertEquals("layer3 does not equal layer1", true, layer3.equals(layer1));

        assertEquals("layer2 does not equal layer3", true, layer2.equals(layer3));
        assertEquals("layer3 does not equal layer2", true, layer3.equals(layer2));
    }
}
