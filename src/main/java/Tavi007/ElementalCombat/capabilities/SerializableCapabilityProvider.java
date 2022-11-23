package Tavi007.ElementalCombat.capabilities;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

//copied from Choonster TestMod3 (https://github.com/Choonster-Minecraft-Mods/TestMod3)
public class SerializableCapabilityProvider<HANDLER> extends SimpleCapabilityProvider<HANDLER> implements INBTSerializable<Tag> {

    private final INBTSerializable<Tag> serializableInstance;

    public SerializableCapabilityProvider(final Capability<HANDLER> capability, @Nullable final Direction facing, @Nullable final HANDLER instance) {
        super(capability, facing, instance);
        Preconditions.checkArgument(instance instanceof INBTSerializable, "instance must implement INBTSerializable");
        serializableInstance = (INBTSerializable<Tag>) instance;
    }

    @Override
    public Tag serializeNBT() {
        return serializableInstance.serializeNBT();
    }

    @Override
    public void deserializeNBT(final Tag tag) {
        serializableInstance.deserializeNBT(tag);
    }
}
