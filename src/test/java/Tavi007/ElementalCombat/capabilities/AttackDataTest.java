package Tavi007.ElementalCombat.capabilities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.config.ServerConfig;
import net.minecraft.resources.ResourceLocation;

@Ignore
public class AttackDataTest {

    @Before
    public void init() {
        System.getProperties().put("production", "1");
    }

    private static final ResourceLocation baseRL = new ResourceLocation("base");
    private AttackLayer baseLayer = new AttackLayer("style1", "element1");

    private static final ResourceLocation secondRL = new ResourceLocation("second");
    private AttackLayer secondLayer = new AttackLayer("style2", "element2");

    private static final ResourceLocation thirdRL = new ResourceLocation("third");
    private AttackLayer thirdLayer = new AttackLayer("style3", "element3");

    @Test
    public void testIsDefault() {
        AttackData data = new AttackData();
        assertEquals("isDefault not returning true", true, data.isDefault());
    }

    @Test
    public void testLayerOrdering() {
        AttackData data = new AttackData();
        data.putLayer(baseRL, baseLayer);
        assertEquals("style with base layer is incorrect", "style1", data.getStyle());
        assertEquals("element with base layer is incorrect", "element1", data.getElement());

        data.putLayer(secondRL, secondLayer); // newest layer has highest priority. Rest follows in descending order.
        assertEquals("style with second layer is incorrect", "style2", data.getStyle());
        assertEquals("element with second layer is incorrect", "element2", data.getElement());

        data.putLayer(thirdRL, thirdLayer); // now layer 3 was added last and therefor has highest priority
        assertEquals("style with third layer is incorrect", "style3", data.getStyle());
        assertEquals("element with third layer is incorrect", "element3", data.getElement());

        data.putLayer(secondRL, secondLayer); // now layer 2 was updated and has highest priority again. Now the order must be base, third, second
        assertEquals("style with updated second layer is incorrect", "style2", data.getStyle());
        assertEquals("element with updated second layer is incorrect", "element2", data.getElement());
    }

    @Test
    public void testNullInLayer() {
        AttackData data = new AttackData();
        assertEquals("style without layer is incorrect", ServerConfig.getDefaultStyle(), data.getStyle());
        assertEquals("element without layer is incorrect", ServerConfig.getDefaultElement(), data.getElement());

        data.putLayer(baseRL, new AttackLayer(null, "element1")); // instead of style being null, return value from server config
        assertEquals("style with base layer is incorrect", ServerConfig.getDefaultStyle(), data.getStyle());
        assertEquals("element with base layer is incorrect", "element1", data.getElement());

        data.putLayer(secondRL, new AttackLayer("style2", null)); // this null value will be ignored and instead the value of the next layer will be checked
        assertEquals("style with second layer is incorrect", "style2", data.getStyle());
        assertEquals("element with second layer is incorrect", "element1", data.getElement());

        data.putLayer(secondRL, new AttackLayer("style3", ServerConfig.getDefaultElement())); // default values are handled the same as null
        assertEquals("style with third layer is incorrect", "style3", data.getStyle());
        assertEquals("element with third layer is incorrect", "element1", data.getElement());
    }
}
