package Tavi007.ElementalCombat.capabilities;

import java.util.HashSet;
import java.util.Set;

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
    	Set<String> atckSet = instance.getAttackSet();
    	
    	//fill nbt with data
    	CompoundNBT nbt = new CompoundNBT();
    	nbt.put("elem_atck", fromSetToNBT(atckSet));
    	
    	return nbt;
    }

    @Override
    public void readNBT(Capability<IElementalAttackData> capability, IElementalAttackData instance, Direction side, INBT nbt) 
    {
        if (!(instance instanceof ElementalAttackData))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        
        //fill lists with nbt data
        CompoundNBT nbtCompound = (CompoundNBT)nbt;
        instance.setAttackSet(  fromNBTToSet(nbtCompound.getList("elem_atck", nbt.getId())));
    }
    
    private Set<String> fromNBTToSet(ListNBT nbt)
    {
    	Set<String> set = new HashSet<String>();
    	if(nbt!=null)
    	{
	    	for (INBT item : nbt)
	    	{
	    		set.add(item.toString());
	    	}
    	}
    	return set;
    }
    
    private ListNBT fromSetToNBT(Set<String> set)
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
}