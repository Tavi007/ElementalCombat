package Tavi007.ElementalCombat.capabilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class ElementalDefenceDataStorage implements Capability.IStorage<IElementalDefenceData> 
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IElementalDefenceData> capability, IElementalDefenceData instance, Direction side) 
    {
    	List<String> weaknessList = instance.getWeaknessList();
    	List<String> resistanceList = instance.getResistanceList();
    	List<String> wallList = instance.getWallList();
    	List<String> absorbList = instance.getAbsorbList();
    	
    	//fill nbt with data
    	CompoundNBT nbt = new CompoundNBT();
    	nbt.put("elem_weakness", fromListToNBT(weaknessList));
    	nbt.put("elem_resistance", fromListToNBT(resistanceList));
    	nbt.put("elem_wall", fromListToNBT(wallList));
    	nbt.put("elem_absorb", fromListToNBT(absorbList));
    	
    	return nbt;
    }

    @Override
    public void readNBT(Capability<IElementalDefenceData> capability, IElementalDefenceData instance, Direction side, INBT nbt) 
    {
        if (!(instance instanceof ElementalDefenceData))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        
        //fill lists with nbt data
        CompoundNBT nbtCompound = (CompoundNBT)nbt;
        ListNBT weaknessNBT = nbtCompound.getList("elem_weakness", ????);
       
        
        instance.setWeaknessList(fromNBTToList(weaknessNBT));
        instance.setResistanceList(fromNBTToList(resistanceNBT));
        instance.setWallList(fromNBTToList(wallNBT));
        instance.setAbsorbList(fromNBTToList(absorbNBT));
    }
    
    private List<String> fromNBTToList(ListNBT nbt)
    {
    	List<String> list = new ArrayList<String>();
    	Iterator<INBT> iterator = nbt.iterator(); 
    	while (iterator.hasNext()) 
    	{
    		list.add(iterator.toString());
    	}
    	return list;
    }
    
    private ListNBT fromListToNBT(List<String> list)
    {
    	ListNBT nbt = new ListNBT();
    	Iterator<String> iterator = list.iterator(); 
    	while (iterator.hasNext()) 
    	{
    		String s = iterator.toString();
    		nbt.add(StringNBT(s));
    	}
    }
}