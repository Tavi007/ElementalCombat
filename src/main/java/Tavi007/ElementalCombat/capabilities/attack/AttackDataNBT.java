package Tavi007.ElementalCombat.capabilities.attack;

import Tavi007.ElementalCombat.util.ElementalCombatNBTHelper;
import net.minecraft.nbt.CompoundNBT;

public class AttackDataNBT extends CompoundNBT {

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
