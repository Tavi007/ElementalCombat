package Tavi007.ElementalCombat.capabilities;

import java.util.ArrayList;
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
    	List<String> weakList = instance.getWeaknessList();
    	List<String> resiList = instance.getResistanceList();
    	List<String> wallList = instance.getWallList();
    	List<String> absoList = instance.getAbsorbList();
    	
    	//fill nbt with data
    	CompoundNBT nbt = new CompoundNBT();
    	nbt.put("elem_weak", fromListToNBT(weakList));
    	nbt.put("elem_resi", fromListToNBT(resiList));
    	nbt.put("elem_wall", fromListToNBT(wallList));
    	nbt.put("elem_abso", fromListToNBT(absoList));
    	
    	return nbt;
    }

    @Override
    public void readNBT(Capability<IElementalDefenceData> capability, IElementalDefenceData instance, Direction side, INBT nbt) 
    {
        if (!(instance instanceof ElementalDefenceData))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        
        //fill lists with nbt data
        CompoundNBT nbtCompound = (CompoundNBT)nbt;
        instance.setWeaknessList(  fromNBTToList(nbtCompound.getList("elem_weak", nbt.getId())));
        instance.setResistanceList(fromNBTToList(nbtCompound.getList("elem_resi", nbt.getId())));
        instance.setWallList(      fromNBTToList(nbtCompound.getList("elem_wall", nbt.getId())));
        instance.setAbsorbList(    fromNBTToList(nbtCompound.getList("elem_abso", nbt.getId())));
    }
    
    private List<String> fromNBTToList(ListNBT nbt)
    {
    	List<String> list = new ArrayList<String>();
    	if(nbt!=null)
    	{
	    	for (INBT item : nbt)
	    	{
	    		list.add(item.toString());
	    	}
    	}
    	return list;
    }
    
    private ListNBT fromListToNBT(List<String> list)
    {
    	ListNBT nbt = new ListNBT();
    	if(list != null)
    	{
	    	for (String item : list) 
	    	{	
	    		nbt.add(StringNBT.valueOf(item));
	    	}
    	}
    	return nbt;
    }
}