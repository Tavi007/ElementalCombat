package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class ImmersionDataSerializer implements INBTSerializable<Tag> {

    private final ImmersionData data;

    public ImmersionDataSerializer() {
        this.data = new ImmersionData();
    }

    public ImmersionDataSerializer(ImmersionData data) {
        this.data = data;
    }

    public ImmersionData getData() {
        return data;
    }

    @Override
    public Tag serializeNBT() {
        return data.serializeNBT();
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        data.deserializeNBT((CompoundTag) nbt);
    }
}
