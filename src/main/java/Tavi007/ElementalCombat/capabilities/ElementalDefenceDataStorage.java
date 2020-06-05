package Tavi007.ElementalCombat.capabilities;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class ElementalDefenceDataStorage implements Capability.IStorage<IElementalDefenceData> 
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IElementalDefenceData> capability, IElementalDefenceData instance, Direction side) 
    {
    	CompoundNBT tag = JsonToNBT.getTagFromJson("hi");
    	
    	return tag;
    }

    @Override
    public void readNBT(Capability<IElementalDefenceData> capability, IElementalDefenceData instance, Direction side, INBT nbt) 
    {
        if (!(instance instanceof ElementalDefenceData))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");

        
    }
}