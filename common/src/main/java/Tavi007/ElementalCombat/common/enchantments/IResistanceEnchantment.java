package Tavi007.ElementalCombat.common.enchantments;

import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseProperty;

public interface IResistanceEnchantment {

    DefenseProperty getDefenseProperty(int level);
}
