package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class DefenseConfigSerializer implements INBTSerializable<Tag> {

    private DefenseConfig data;

    public DefenseConfigSerializer() {
        data = new DefenseConfig();
    }

    public DefenseConfigSerializer(DefenseConfig data) {
        this.data = data;
    }

    public DefenseConfig getData() {
        return data;
    }

    @Override
    public Tag serializeNBT() {
        return DefenseConfig.writeTag(data);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        data = DefenseConfig.readTag((CompoundTag) nbt);
    }
}
