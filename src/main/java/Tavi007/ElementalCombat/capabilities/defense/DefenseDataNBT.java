package Tavi007.ElementalCombat.capabilities.defense;

import Tavi007.ElementalCombat.util.ElementalCombatNBTHelper;
import net.minecraft.nbt.CompoundNBT;

public class DefenseDataNBT extends CompoundNBT {

	public DefenseDataNBT () {
		super();
	}

	@Override
	public boolean equals(Object object) {
		if(object instanceof DefenseDataNBT) {
			DefenseData data = ElementalCombatNBTHelper.readDefenseDataFromNBT(this);
			return data.equals(ElementalCombatNBTHelper.readDefenseDataFromNBT((DefenseDataNBT) object));
		}
		return false;
	}
}
