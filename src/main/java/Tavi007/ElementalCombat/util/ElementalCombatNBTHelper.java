package Tavi007.ElementalCombat.util;

import java.util.HashMap;
import java.util.Set;

import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.api.defense.DefenseLayer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;

public class ElementalCombatNBTHelper {


	/**
	 * Writes the attack-combat data {@link AttackData} to the {@link CompoundNBT}.
	 * @param nbt The CompoundNBT.
	 * @param data The AttackData.
	 */
	public static void writeAttackDataToNBT(CompoundNBT nbt, AttackData data) {
		CompoundNBT nbtInner = new CompoundNBT();
		nbtInner.put("attack_element", StringNBT.valueOf(data.getElement()));
		nbtInner.put("attack_style", StringNBT.valueOf(data.getStyle()));
		nbt.put("elementalcombat_attack", nbtInner);
	}
	
	/**
	 * Reads the attack-combat data {@link AttackData} from the {@link CompoundNBT}.
	 * Note: It will read whatever was written into nbt by {@link ElementalCombatNBTHelper#writeAttackDataToNBT}.
	 * @param nbt The CompoundNBT.
	 * @return The AttackData. Default is an empty instance.
	 */
	public static AttackData readAttackDataFromNBT(CompoundNBT nbt) {
		AttackData data = new AttackData();
		CompoundNBT nbtInner = nbt.getCompound("elementalcombat_attack");
		data.setElement(nbtInner.getString("attack_element"));
		data.setStyle(nbtInner.getString("attack_style"));
		return data;
	}

	/**
	 * Writes the defense-combat data {@link DefenseData} to the {@link CompoundNBT}.
	 * @param nbt The CompoundNBT.
	 * @param data The DefenseData.
	 */
	public static void writeDefenseDataToNBT(CompoundNBT nbt, DefenseData data) {
		nbt.put("elementalcombat_defense", fromLayersToNBT(data.getLayers()));
	}

	
	/**
	 * Reads the defense-combat data {@link DefenseData} from the {@link CompoundNBT}.
	 * Note: It will read whatever was written into nbt by {@link ElementalCombatNBTHelper#writeDefenseDataToNBT}.
	 * @param nbt The CompoundNBT.
	 * @return The DefenseData. Default is an empty instance.
	 */
	public static DefenseData readDefenseDataFromNBT(CompoundNBT nbt) {
		DefenseData data = new DefenseData();
		fromNBTToLayers(nbt.getCompound("elementalcombat_defense")).forEach((rl, layer) -> {
			data.putLayer(layer, rl);
		});
		return data;
	}
	

	// read from nbt
	private static HashMap<ResourceLocation, DefenseLayer> fromNBTToLayers(CompoundNBT nbtCompound) {
		HashMap<ResourceLocation, DefenseLayer> map = new HashMap<ResourceLocation, DefenseLayer>();
		if(nbtCompound!=null) {
			Set<String> keySet = nbtCompound.keySet();
			for (String key : keySet) {
				map.put(new ResourceLocation(key), fromNBTToLayer((CompoundNBT) nbtCompound.get(key)));
			}
		}
		return map;
	}
	
	private static DefenseLayer fromNBTToLayer(CompoundNBT nbtCompound) {
		DefenseLayer layer = new DefenseLayer();
		if(nbtCompound!=null) {
			layer.addStyle(fromNBTToMap((CompoundNBT) nbtCompound.get("style")));
			layer.addElement(fromNBTToMap((CompoundNBT) nbtCompound.get("element")));
		}
		return layer;
	}
	
	private static HashMap<String, Integer> fromNBTToMap(CompoundNBT nbtCompound) {
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

	// write to nbt
	private static CompoundNBT fromLayersToNBT(HashMap<ResourceLocation, DefenseLayer> layers) {
		CompoundNBT nbt = new CompoundNBT();
		if(layers != null) {
			layers.forEach((rl, layer) -> {
				nbt.put(rl.toString(), fromLayerToNBT(layer));
			});
		}
		return nbt; 
	}
	
	private static CompoundNBT fromLayerToNBT(DefenseLayer layer) {
		CompoundNBT nbt = new CompoundNBT();
		if(layer != null) {
			nbt.put("style", fromMapToNBT(layer.getStyleFactor()));
			nbt.put("element", fromMapToNBT(layer.getElementFactor()));
		}
		return nbt; 
	}
	
	private static CompoundNBT fromMapToNBT(HashMap<String, Integer> map) {
		CompoundNBT nbt = new CompoundNBT();
		if(map != null) {
			map.forEach((elemString, value) -> {
				nbt.putInt(elemString, value);
			});
		}
		return nbt; 
	}

}
