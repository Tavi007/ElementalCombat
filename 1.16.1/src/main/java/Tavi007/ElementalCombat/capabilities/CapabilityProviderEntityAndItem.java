package Tavi007.ElementalCombat.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityProviderEntityAndItem implements ICapabilitySerializable<CompoundNBT> 
{
	ElementalAttack elementalAttack = new ElementalAttack();
	private final static String ATTACK_NBT = "attack";
	
	ElementalDefense elementalDefense = new ElementalDefense();
	private final static String DEFENSE_NBT = "defense";
    
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) 
    {
    	if(capability == ElementalAttackCapability.CAPABILITY_ELEMENTAL_ATTACK) {
    		return (LazyOptional<T>) LazyOptional.of(() -> elementalAttack );
    	}
    	if(capability == ElementalDefenseCapability.CAPABILITY_ELEMENTAL_DEFENSE) {
    		return (LazyOptional<T>) LazyOptional.of(() -> elementalDefense );
    	}
    	
    	return LazyOptional.empty();
    }

    
    
    @Override
    public CompoundNBT serializeNBT() 
    {
    	CompoundNBT nbt = new CompoundNBT();
    	INBT attackNBT = ElementalAttackCapability.CAPABILITY_ELEMENTAL_ATTACK.writeNBT(elementalAttack, null);
    	INBT defenseNBT = ElementalDefenseCapability.CAPABILITY_ELEMENTAL_DEFENSE.writeNBT(elementalDefense, null);
    	
    	nbt.put(ATTACK_NBT, attackNBT);
    	nbt.put(DEFENSE_NBT, defenseNBT);
    	
    	return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) 
    {	
    	CompoundNBT compoundNBT = (CompoundNBT) nbt;
    	INBT attackNBT = compoundNBT.get(ATTACK_NBT);
    	INBT defenseNBT = compoundNBT.get(DEFENSE_NBT);
    	
    	ElementalAttackCapability.CAPABILITY_ELEMENTAL_ATTACK.readNBT(elementalAttack, null, attackNBT);
    	ElementalDefenseCapability.CAPABILITY_ELEMENTAL_DEFENSE.readNBT(elementalDefense, null, defenseNBT);
    }
}