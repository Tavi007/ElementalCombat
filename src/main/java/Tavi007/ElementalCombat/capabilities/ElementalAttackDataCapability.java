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

public class ElementalAttackDataCapability implements ICapabilitySerializable<CompoundNBT> 
{

    @CapabilityInject(IElementalAttackData.class)
    public static final Capability<IElementalAttackData> ATK_DATA_CAPABILITY = null;
    
    private LazyOptional<IElementalAttackData> instance = LazyOptional.of(ATK_DATA_CAPABILITY::getDefaultInstance);

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IElementalAttackData.class, new ElementalAttackDataStorage(), ElementalAttackData::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) 
    {
        return ATK_DATA_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public CompoundNBT serializeNBT() 
    {
        return (CompoundNBT) ATK_DATA_CAPABILITY.getStorage().writeNBT(ATK_DATA_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) 
    {
    	ATK_DATA_CAPABILITY.getStorage().readNBT(ATK_DATA_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
    }
}