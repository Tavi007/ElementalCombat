package Tavi007.ElementalCombat.capabilities;

import javax.annotation.Nullable;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

//copied from Choonster TestMod3 (https://github.com/Choonster-Minecraft-Mods/TestMod3)
public class SimpleCapabilityProvider<HANDLER> implements ICapabilityProvider {

    protected final Capability<HANDLER> capability;

    protected final Direction facing;

    protected final HANDLER instance;

    protected final LazyOptional<HANDLER> lazyOptional;

    public SimpleCapabilityProvider(final Capability<HANDLER> capability, @Nullable final Direction facing, @Nullable final HANDLER instance) {
        this.capability = capability;
        this.facing = facing;

        this.instance = instance;

        if (this.instance != null) {
            lazyOptional = LazyOptional.of(() -> this.instance);
        } else {
            lazyOptional = LazyOptional.empty();
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(final Capability<T> capability, @Nullable final Direction facing) {
        return getCapability().orEmpty(capability, lazyOptional);
    }

    public final Capability<HANDLER> getCapability() {
        return capability;
    }

    @Nullable
    public Direction getFacing() {
        return facing;
    }

    @Nullable
    public final HANDLER getInstance() {
        return instance;
    }
}
