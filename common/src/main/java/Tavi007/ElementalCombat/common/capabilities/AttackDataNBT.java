package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.util.ElementalCombatNBTHelper;
import net.minecraft.nbt.CompoundTag;

public class AttackDataNBT extends CompoundTag {

    public AttackDataNBT() {
        super();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof AttackDataNBT) {
            AttackData data = ElementalCombatNBTHelper.readAttackDataFromNBT(this);
            return data.equals(ElementalCombatNBTHelper.readAttackDataFromNBT((AttackDataNBT) object));
        }
        return false;
    }
}
