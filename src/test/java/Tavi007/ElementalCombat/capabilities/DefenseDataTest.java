package Tavi007.ElementalCombat.capabilities;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.init.EnchantmentList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.ResourceLocation;

public class DefenseDataTest {

    private static final ResourceLocation baseRL = new ResourceLocation("base");
    private DefenseLayer baseLayer;

    private static final ResourceLocation secondRL = new ResourceLocation("second");
    private DefenseLayer secondLayer;

    @Before
    public void before() {
        HashMap<String, Integer> style = new HashMap<String, Integer>();
        style.put("style1", 1);
        HashMap<String, Integer> element = new HashMap<String, Integer>();
        element.put("element1", 2);
        baseLayer = new DefenseLayer(style, element);

        HashMap<String, Integer> style2 = new HashMap<String, Integer>();
        style2.put("style1", 10);
        style2.put("style2", 10);
        HashMap<String, Integer> element2 = new HashMap<String, Integer>();
        element2.put("element1", 20);
        element2.put("element2", 20);
        secondLayer = new DefenseLayer(style2, element2);
    }

    @Test
    public void testIsEmpty() {
        DefenseData data = new DefenseData();
        assertEquals("isEmpty not returning true", true, data.isEmpty());
    }

    @Test
    public void testPutLayer() {
        DefenseData data = new DefenseData();
        HashMap<String, Integer> style = new HashMap<String, Integer>();
        HashMap<String, Integer> element = new HashMap<String, Integer>();

        data.putLayer(baseRL, baseLayer);
        style.put("style1", 1);
        element.put("element1", 2);
        assertEquals("base style is incorrect", style, data.getStyleFactor());
        assertEquals("base element is incorrect", element, data.getElementFactor());

        data.putLayer(secondRL, secondLayer);
        style.put("style1", 11);
        element.put("element1", 22);
        style.put("style2", 10);
        element.put("element2", 20);
        assertEquals("base+second style is incorrect", style, data.getStyleFactor());
        assertEquals("base+second element is incorrect", element, data.getElementFactor());
    }

    @Test
    public void testEquals() {
        DefenseData data1 = new DefenseData();
        DefenseData data2 = new DefenseData();
        HashMap<String, Integer> style = new HashMap<String, Integer>();
        HashMap<String, Integer> element = new HashMap<String, Integer>();

        data1.putLayer(baseRL, baseLayer);
        style.put("style1", 1);
        element.put("element1", 2);
        data2.putLayer(baseRL, new DefenseLayer(style, element));
        assertEquals("data1 equals data2 fails with base layer", true, data1.equals(data2));
        assertEquals("data2 equals data1 fails with base layer", true, data2.equals(data1));

        data1.putLayer(secondRL, secondLayer);
        style.put("style1", 11);
        element.put("element1", 22);
        style.put("style2", 10);
        element.put("element2", 20);
        data2.putLayer(baseRL, new DefenseLayer(style, element));
        assertEquals("data1 equals data2 is correct, although they have different layers", false, data1.equals(data2));
        assertEquals("data2 equals data1 is correct, although they have different layers", false, data2.equals(data1));

        style.put("style1", 1);
        element.put("element1", 2);
        HashMap<String, Integer> style2 = new HashMap<String, Integer>();
        HashMap<String, Integer> element2 = new HashMap<String, Integer>();
        style2.put("style1", 10);
        style2.put("style2", 10);
        element2.put("element1", 20);
        element2.put("element2", 20);
        data2.putLayer(baseRL, new DefenseLayer(style, element));
        data2.putLayer(secondRL, new DefenseLayer(style2, element2));
        assertEquals("data1 equals data2 fails with 2 layers", false, data1.equals(data2));
        assertEquals("data2 equals data1 fails with 2 layers", false, data2.equals(data1));
    }

    @Test
    public void testToLayer() {
        DefenseData data = new DefenseData();
        HashMap<String, Integer> style = new HashMap<String, Integer>();
        HashMap<String, Integer> element = new HashMap<String, Integer>();

        data.putLayer(baseRL, baseLayer);
        style.put("style1", 1);
        element.put("element1", 2);
        assertEquals("toLayer is not equal with baseLayer", new DefenseLayer(style, element), data.toLayer());

        data.putLayer(secondRL, secondLayer);
        style.put("style1", 11);
        element.put("element1", 22);
        style.put("style2", 10);
        element.put("element2", 20);
        assertEquals("toLayer is not equal with baseLayer + secondLayer", new DefenseLayer(style, element), data.toLayer());
    }

    @Test
    @Ignore
    public void testApplyVanillaEnchantments() {
        applyAndAssertElementEnchantment(Enchantments.FIRE_PROTECTION, "fire", "ice");
        applyAndAssertStyleEnchantment(Enchantments.BLAST_PROTECTION, "explosion");
        applyAndAssertStyleEnchantment(Enchantments.PROJECTILE_PROTECTION, "projectile");
    }

    @Test
    @Ignore
    public void testApplyCustomEnchantments() {
        applyAndAssertElementEnchantment(EnchantmentList.ICE_PROTECTION.get(), "ice", "fire");
        applyAndAssertElementEnchantment(EnchantmentList.THUNDER_PROTECTION.get(), "thunder", "water");
        applyAndAssertElementEnchantment(EnchantmentList.WATER_PROTECTION.get(), "water", "thunder");
        applyAndAssertElementEnchantment(EnchantmentList.DARKNESS_PROTECTION.get(), "darkness", "light");
        applyAndAssertElementEnchantment(EnchantmentList.LIGHT_PROTECTION.get(), "light", "darkness");
        applyAndAssertStyleEnchantment(EnchantmentList.ELEMENT_PROTECTION.get(), "magic");
    }

    private void applyAndAssertElementEnchantment(Enchantment ench, String elementPlus, String elementMinus) {
        DefenseData data = new DefenseData();
        HashMap<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
        DefenseData dataExpected = new DefenseData();
        HashMap<String, Integer> style = new HashMap<String, Integer>();
        HashMap<String, Integer> element = new HashMap<String, Integer>();

        enchantments.put(ench, 1);
        data.applyEnchantmentChanges(enchantments);
        element.put(elementPlus, 5);
        element.put(elementMinus, -2);
        data.putLayer(new ResourceLocation("enchantment"), new DefenseLayer(style, element));
        assertEquals("DefenseData is incorrect, after applying " + ench.toString() + " enchantment", dataExpected, data);
    }

    private void applyAndAssertStyleEnchantment(Enchantment ench, String styleName) {
        DefenseData data = new DefenseData();
        HashMap<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
        DefenseData dataExpected = new DefenseData();
        HashMap<String, Integer> style = new HashMap<String, Integer>();
        HashMap<String, Integer> element = new HashMap<String, Integer>();

        enchantments.put(ench, 1);
        data.applyEnchantmentChanges(enchantments);
        style.put(styleName, 5);
        data.putLayer(new ResourceLocation("enchantment"), new DefenseLayer(style, element));
        assertEquals("DefenseData is incorrect, after applying " + ench.toString() + " enchantment", dataExpected, data);
    }
}
