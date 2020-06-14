package Tavi007.ElementalCombat.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ElementalDefenceDataCapability implements ICapabilitySerializable<CompoundNBT> 
{

    @CapabilityInject(IElementalDefenceData.class)
    public static final Capability<IElementalDefenceData> DEF_DATA_CAPABILITY = null;
    
    private LazyOptional<IElementalDefenceData> instance = LazyOptional.of(DEF_DATA_CAPABILITY::getDefaultInstance);

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IElementalDefenceData.class, new ElementalDefenceDataStorage(), ElementalDefenceData::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) 
    {
        return DEF_DATA_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public CompoundNBT serializeNBT() 
    {
        return (CompoundNBT) DEF_DATA_CAPABILITY.getStorage().writeNBT(DEF_DATA_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) 
    {
    	DEF_DATA_CAPABILITY.getStorage().readNBT(DEF_DATA_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
    }
}