package Tavi007.ElementalCombat.enchantments;

import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;

public interface IResistanceEnchantment {

    public DefenseLayer getDefenseLayer(int level);
}
