package Tavi007.ElementalCombat.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityProviderProjectile implements ICapabilitySerializable<CompoundNBT> 
{
	ElementalAttack elementalAttack = new ElementalAttack();
	private final static String ATTACK_NBT = "attack";
    
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) 
    {
    	if(capability == ElementalAttackCapability.CAPABILITY_ELEMENTAL_ATTACK) {
    		return (LazyOptional<T>) LazyOptional.of(() -> elementalAttack );
    	}
    	
    	return LazyOptional.empty();
    }

    
    
    @Override
    public CompoundNBT serializeNBT() 
    {
    	CompoundNBT nbt = new CompoundNBT();
    	INBT attackNBT = ElementalAttackCapability.CAPABILITY_ELEMENTAL_ATTACK.writeNBT(elementalAttack, null);
    	
    	nbt.put(ATTACK_NBT, attackNBT);
    	
    	return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) 
    {	
    	CompoundNBT compoundNBT = (CompoundNBT) nbt;
    	INBT attackNBT = compoundNBT.get(ATTACK_NBT);
    	
    	ElementalAttackCapability.CAPABILITY_ELEMENTAL_ATTACK.readNBT(elementalAttack, null, attackNBT);
    }
}