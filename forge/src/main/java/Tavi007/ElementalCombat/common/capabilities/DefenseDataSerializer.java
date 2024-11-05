package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class DefenseDataSerializer implements INBTSerializable<Tag> {

    private final DefenseData data;

    public DefenseDataSerializer() {
        data = new DefenseData();
    }

    public DefenseDataSerializer(DefenseData data) {
        this.data = data;
    }

    public DefenseData getData() {
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
