package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class AttackDataSerializer implements INBTSerializable<Tag> {

    private final AttackData data;

    public AttackDataSerializer() {
        this.data = new AttackData();
    }

    public AttackDataSerializer(AttackData data) {
        this.data = data;
    }

    public AttackData getData() {
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
