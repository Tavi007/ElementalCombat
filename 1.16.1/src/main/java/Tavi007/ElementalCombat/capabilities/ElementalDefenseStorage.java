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

public class ElementalDefenseStorage implements Capability.IStorage<ElementalDefense> 
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<ElementalDefense> capability, ElementalDefense instance, Direction side) 
    {
    	Set<String> weakSet = instance.getWeaknessSet();
    	Set<String> resiSet = instance.getResistanceSet();
    	Set<String> immuSet = instance.getImmunitySet();
    	Set<String> absoSet = instance.getAbsorbSet();
    	
    	//fill nbt with data
    	CompoundNBT nbt = new CompoundNBT();
    	nbt.put("elem_weak", fromSetToNBT(weakSet));
    	nbt.put("elem_resi", fromSetToNBT(resiSet));
    	nbt.put("elem_immu", fromSetToNBT(immuSet));
    	nbt.put("elem_abso", fromSetToNBT(absoSet));
    	
    	return nbt;
    }

    @Override
    public void readNBT(Capability<ElementalDefense> capability, ElementalDefense instance, Direction side, INBT nbt) 
    {
        if (!(instance instanceof ElementalDefense))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        
        //fill lists with nbt data
        CompoundNBT nbtCompound = (CompoundNBT)nbt;
        
        //Set<String> test = fromNBTToSet(nbtCompound.getList("elem_weak", nbt.getId()));
        //System.out.println(test);
        
        
        instance.setWeaknessSet(  fromNBTToSet(nbtCompound.getList("elem_weak", nbt.getId())));
        instance.setResistanceSet(fromNBTToSet(nbtCompound.getList("elem_resi", nbt.getId())));
        instance.setImmunitySet(  fromNBTToSet(nbtCompound.getList("elem_immu", nbt.getId())));
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