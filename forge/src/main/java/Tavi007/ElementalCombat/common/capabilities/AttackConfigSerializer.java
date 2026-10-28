package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class AttackConfigSerializer implements INBTSerializable<Tag> {

    private AttackConfig data;

    public AttackConfigSerializer() {
        this.data = new AttackConfig();
    }

    public AttackConfigSerializer(AttackConfig data) {
        this.data = data;
    }

    public AttackConfig getData() {
        return data;
    }

    @Override
    public Tag serializeNBT() {
        return AttackConfig.writeTag(data);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        data = AttackConfig.readTag((CompoundTag) nbt);
    }
}
