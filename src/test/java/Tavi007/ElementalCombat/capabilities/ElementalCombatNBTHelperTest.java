package Tavi007.ElementalCombat.capabilities;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.util.ElementalCombatNBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

@Ignore
public class ElementalCombatNBTHelperTest {

    @Before
    public void init() {
        System.getProperties().put("production", "1");
    }

    @Test
    public void testDefaultAttackData() {
        AttackData data = new AttackData();
        CompoundTag nbt = new CompoundTag();
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, data);

        AttackData newData = ElementalCombatNBTHelper.readAttackDataFromNBT(nbt);
        assertEquals("reading or writing default AttackData failed", data, newData);
    }

    @Test
    public void testAttackDataWithSingleLayer() {
        AttackData data = new AttackData();
        data.putLayer(new ResourceLocation("test"), new AttackLayer("style", "element"));
        CompoundTag nbt = new CompoundTag();
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, data);

        AttackData newData = ElementalCombatNBTHelper.readAttackDataFromNBT(nbt);
        assertEquals("reading or writing default AttackData failed", data, newData);
    }

    @Test
    public void testAttackDataWithTwoLayers() {
        AttackData data = new AttackData();
        data.putLayer(new ResourceLocation("test1"), new AttackLayer("style1", "element1"));
        data.putLayer(new ResourceLocation("test2"), new AttackLayer("style2", "element2"));
        CompoundTag nbt = new CompoundTag();
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, data);

        AttackData newData = ElementalCombatNBTHelper.readAttackDataFromNBT(nbt);
        assertEquals("reading or writing default AttackData failed", data, newData);
    }

    @Test
    public void testDefaultDefenseData() {
        DefenseData data = new DefenseData();
        CompoundTag nbt = new CompoundTag();
        ElementalCombatNBTHelper.writeDefenseDataToNBT(nbt, data);

        DefenseData newData = ElementalCombatNBTHelper.readDefenseDataFromNBT(nbt);
        assertEquals("reading or writing default AttackData failed", data, newData);
    }

    @Test
    public void testDefenseDataWithSingleEmptyLayer() {
        DefenseData data = new DefenseData();
        data.putLayer(new ResourceLocation("test"), new DefenseLayer());
        CompoundTag nbt = new CompoundTag();
        ElementalCombatNBTHelper.writeDefenseDataToNBT(nbt, data);

        DefenseData newData = ElementalCombatNBTHelper.readDefenseDataFromNBT(nbt);
        assertEquals("reading or writing default AttackData failed", data, newData);
    }

    @Test
    public void testDefenseDataWithSingleLayer() {
        DefenseData data = new DefenseData();
        HashMap<String, Integer> style = new HashMap<>();
        style.put("style1", 1);
        style.put("style2", 2);
        HashMap<String, Integer> element = new HashMap<>();
        element.put("element1", 10);
        element.put("element2", 20);
        data.putLayer(new ResourceLocation("test"), new DefenseLayer(style, element));
        CompoundTag nbt = new CompoundTag();
        ElementalCombatNBTHelper.writeDefenseDataToNBT(nbt, data);

        DefenseData newData = ElementalCombatNBTHelper.readDefenseDataFromNBT(nbt);
        assertEquals("reading or writing default AttackData failed", data, newData);
    }

    @Test
    public void testDefenseDataWithTwoLayers() {
        DefenseData data = new DefenseData();
        HashMap<String, Integer> style1 = new HashMap<>();
        style1.put("style1", 1);
        style1.put("style2", 2);
        HashMap<String, Integer> element1 = new HashMap<>();
        element1.put("element1", 10);
        element1.put("element2", 20);
        data.putLayer(new ResourceLocation("test1"), new DefenseLayer(style1, element1));

        HashMap<String, Integer> style2 = new HashMap<>();
        style2.put("style12", 11);
        style2.put("style22", 21);
        HashMap<String, Integer> element2 = new HashMap<>();
        element2.put("element12", 101);
        element2.put("element22", 201);
        data.putLayer(new ResourceLocation("test2"), new DefenseLayer(style2, element2));

        CompoundTag nbt = new CompoundTag();
        ElementalCombatNBTHelper.writeDefenseDataToNBT(nbt, data);

        DefenseData newData = ElementalCombatNBTHelper.readDefenseDataFromNBT(nbt);
        assertEquals("reading or writing default AttackData failed", data, newData);
    }
}
