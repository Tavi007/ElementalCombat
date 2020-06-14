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

public class ElementalAttackDataStorage implements Capability.IStorage<IElementalAttackData> 
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IElementalAttackData> capability, IElementalAttackData instance, Direction side) 
    {
    	List<String> atckList = instance.getAttackList();
    	
    	//fill nbt with data
    	CompoundNBT nbt = new CompoundNBT();
    	nbt.put("elem_atck", fromListToNBT(atckList));
    	
    	return nbt;
    }

    @Override
    public void readNBT(Capability<IElementalAttackData> capability, IElementalAttackData instance, Direction side, INBT nbt) 
    {
        if (!(instance instanceof ElementalAttackData))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        
        //fill lists with nbt data
        CompoundNBT nbtCompound = (CompoundNBT)nbt;
        instance.setAttackList(  fromNBTToList(nbtCompound.getList("elem_atck", nbt.getId())));
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