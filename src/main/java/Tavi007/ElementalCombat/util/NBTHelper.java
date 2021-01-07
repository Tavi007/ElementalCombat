package Tavi007.ElementalCombat.util;

import java.util.HashMap;
import java.util.Set;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;

public class NBTHelper {
	
	public static void writeAttackDataToNBT(CompoundNBT nbt, AttackData data) {
		nbt.put("attack_element", StringNBT.valueOf(data.getElement()));
		nbt.put("attack_style", StringNBT.valueOf(data.getStyle()));
	}

	public static AttackData readAttackDataFromNBT(CompoundNBT nbt) {
		AttackData data = new AttackData();
		StringNBT styleNBT = (StringNBT) ((CompoundNBT) nbt).get("attack_style");
		StringNBT elementNBT = (StringNBT) ((CompoundNBT) nbt).get("attack_element");
		data.setStyle(styleNBT.getString());
		data.setElement(elementNBT.getString());
		return data;
	}

	public static void writeDefenseDataToNBT(CompoundNBT nbt, DefenseData data) {
		nbt.put("defense_style", fromMapToNBT(data.getStyleFactor()));
		nbt.put("defense_element", fromMapToNBT(data.getElementFactor()));
		nbt.put("enchantment_data", fromMapToNBT(data.getEnchantmentData()));
	}

	public static DefenseData readDefenseDataFromNBT(CompoundNBT nbt) {
		DefenseData data = new DefenseData();
		data.setStyleFactor(fromNBTToMap(nbt.getCompound("defense_style")));
		data.setElementFactor(fromNBTToMap(nbt.getCompound("defense_element")));
		data.setEnchantmentData(fromNBTToMap(nbt.getCompound("enchantment_data")));
		return data;
	}
	
	public static HashMap<String, Integer> fromNBTToMap(CompoundNBT nbtCompound) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		if(nbtCompound!=null) {
			Set<String> keySet = nbtCompound.keySet();
			for (String key : keySet) { 
				Integer value = nbtCompound.getInt(key);
				map.put(key, value);
			}
		}
		return map;
	}
	
	public static CompoundNBT fromMapToNBT(HashMap<String, Integer> map) {
		CompoundNBT nbt = new CompoundNBT();
		if(map != null) {
			map.forEach((elemString, value) -> {
				nbt.putInt(elemString, value);
			});
		}
		return nbt; 
	}

}
