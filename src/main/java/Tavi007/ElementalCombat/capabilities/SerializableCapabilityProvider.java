package Tavi007.ElementalCombat.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

//copied from Choonster TestMod3 (https://github.com/Choonster-Minecraft-Mods/TestMod3)
public class SerializableCapabilityProvider<HANDLER> extends SimpleCapabilityProvider<HANDLER> implements INBTSerializable<INBT> {

	public SerializableCapabilityProvider(final Capability<HANDLER> capability, @Nullable final Direction facing) {
		this(capability, facing, capability.getDefaultInstance());
	}

	public SerializableCapabilityProvider(final Capability<HANDLER> capability, @Nullable final Direction facing, @Nullable final HANDLER instance) {
		super(capability, facing, instance);
	}

	@Nullable
	@Override
	public INBT serializeNBT() {
		final HANDLER instance = getInstance();

		if (instance == null) {
			return null;
		}

		return getCapability().writeNBT(instance, getFacing());
	}

	@Override
	public void deserializeNBT(final INBT nbt) {
		final HANDLER instance = getInstance();

		if (instance == null) {
			return;
		}

		getCapability().readNBT(instance, getFacing(), nbt);
	}

}