package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import Tavi007.ElementalCombat.common.util.ElementalCombatNBTHelper;
import net.minecraft.nbt.CompoundTag;

public class DefenseDataNBT extends CompoundTag {

    public DefenseDataNBT() {
        super();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DefenseDataNBT) {
            DefenseData data = ElementalCombatNBTHelper.readDefenseDataFromNBT(this);
            return data.equals(ElementalCombatNBTHelper.readDefenseDataFromNBT((DefenseDataNBT) object));
        }
        return false;
    }
}
