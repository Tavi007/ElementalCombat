package Tavi007.ElementalCombat;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.util.DefenseDataHelper;

public class TestDefenseData {

	@Test
	public void testDefenseDataHelperSum() {
		HashMap<String, Integer> baseMap = new HashMap<String, Integer>();
		HashMap<String, Integer> additional = new HashMap<String, Integer>();
		baseMap.put("test", 1);
		additional.put("test", -1);
		DefenseDataHelper.sumMaps(baseMap, additional);
		Assert.assertTrue(baseMap.size() == 0);
	}

	@Test
	public void testDefenseDataHelperSub() {
		HashMap<String, Integer> baseMap = new HashMap<String, Integer>();
		HashMap<String, Integer> additional = new HashMap<String, Integer>();
		baseMap.put("test", 1);
		additional.put("test", 1);
		DefenseDataHelper.substractMaps(baseMap, additional);
		Assert.assertTrue(baseMap.size() == 0);
	}

	@Test
	public void testDefenseData() {
		HashMap<String, Integer> eleme = new HashMap<String, Integer>();
		HashMap<String, Integer> style = new HashMap<String, Integer>();
		eleme.put("test", 1);
		style.put("test", 1);
		DefenseData data = new DefenseData(style, eleme);
		eleme.put("test2", 2);
		Assert.assertTrue(data.getElementFactor().size() == 2);
	}
}
