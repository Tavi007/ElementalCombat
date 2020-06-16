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

public class ElementalDefenseDataStorage implements Capability.IStorage<IElementalDefenseData> 
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IElementalDefenseData> capability, IElementalDefenseData instance, Direction side) 
    {
    	Set<String> weakSet = instance.getWeaknessSet();
    	Set<String> resiSet = instance.getResistanceSet();
    	Set<String> wallSet = instance.getWallSet();
    	Set<String> absoSet = instance.getAbsorbSet();
    	
    	//fill nbt with data
    	CompoundNBT nbt = new CompoundNBT();
    	nbt.put("elem_weak", fromSetToNBT(weakSet));
    	nbt.put("elem_resi", fromSetToNBT(resiSet));
    	nbt.put("elem_wall", fromSetToNBT(wallSet));
    	nbt.put("elem_abso", fromSetToNBT(absoSet));
    	
    	return nbt;
    }

    @Override
    public void readNBT(Capability<IElementalDefenseData> capability, IElementalDefenseData instance, Direction side, INBT nbt) 
    {
        if (!(instance instanceof ElementalDefenseData))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        
        //fill lists with nbt data
        CompoundNBT nbtCompound = (CompoundNBT)nbt;
        instance.setWeaknessSet(  fromNBTToSet(nbtCompound.getList("elem_weak", nbt.getId())));
        instance.setResistanceSet(fromNBTToSet(nbtCompound.getList("elem_resi", nbt.getId())));
        instance.setWallSet(      fromNBTToSet(nbtCompound.getList("elem_wall", nbt.getId())));
        instance.setAbsorbSet(    fromNBTToSet(nbtCompound.getList("elem_abso", nbt.getId())));
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