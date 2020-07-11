package Tavi007.ElementalCombat.capabilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class ElementalAttackStorage implements Capability.IStorage<ElementalAttack> 
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<ElementalAttack> capability, ElementalAttack instance, Direction side) 
    {
    	Map<String, Integer> atckMap = instance.getAttackMap();
    	
    	//fill nbt with data
    	CompoundNBT nbt = new CompoundNBT();
    	nbt.put("elem_atck", fromMapToNBT(atckMap));
    	
    	return nbt;
    }

    @Override
    public void readNBT(Capability<ElementalAttack> capability, ElementalAttack instance, Direction side, INBT nbt) 
    {
        if (!(instance instanceof ElementalAttack))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        
        //fill lists with nbt data
        CompoundNBT nbtCompound = (CompoundNBT)nbt;
        instance.setAttackMap(  fromNBTToMap(nbtCompound.getCompound("elem_atck")));
    }
    
    private Map<String, Integer> fromNBTToMap(CompoundNBT nbt)
    {
    	Map<String, Integer> map = new HashMap<String,Integer>();
    	if(nbt!=null)
    	{
    		Set<String> keySet=nbt.keySet();
	    	for (String key : keySet)
	    	{ 
	    		int value=nbt.getInt(key);
	    		map.put(key, value);
	    	}
    	}
    	return map;
    }
    
    private CompoundNBT fromMapToNBT(Map<String, Integer> map)
    {
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