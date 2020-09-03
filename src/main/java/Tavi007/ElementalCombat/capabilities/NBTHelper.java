package Tavi007.ElementalCombat.capabilities;

import java.util.HashMap;
import java.util.Set;

import net.minecraft.nbt.CompoundNBT;

/*
import java.util.HashSet;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
*/

public class NBTHelper {

	/*
	public static HashSet<String> fromNBTToSet(ListNBT nbt)
	{
		HashSet<String> set = new HashSet<String>();
		if(nbt!=null)
		{
			for (INBT item : nbt)
			{
				set.add(item.toString());
			}
		}
		return set;
	}

	public static ListNBT fromSetToNBT(HashSet<String> set)
	{
		ListNBT nbt = new ListNBT();
		if(set != null)
		{
			for (String item : set) 
			{	
				nbt.add(StringNBT.valueOf(item));
			}
		}
		return nbt;
	}
	*/
	
	public static HashMap<String, Integer> fromNBTToMap(CompoundNBT nbtCompound) {

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		if(nbtCompound!=null)
		{
			Set<String> keySet = nbtCompound.keySet();
			for (String key : keySet)
			{ 
				Integer value = nbtCompound.getInt(key);
				map.put(key, value);
			}
		}
		return map;
	}
	
	public static CompoundNBT fromMapToNBT(HashMap<String, Integer> map) {
		CompoundNBT nbt = new CompoundNBT();
		if(map != null)
		{
			map.forEach((elemString, value) ->
			{
				nbt.putInt(elemString, value);
			});
		}
		return nbt; 
	}

}
