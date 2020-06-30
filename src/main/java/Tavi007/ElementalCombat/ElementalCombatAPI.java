package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.capabilities.ElementalAttackData;
import Tavi007.ElementalCombat.capabilities.ElementalAttackDataCapability;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseData;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseDataCapability;
import Tavi007.ElementalCombat.capabilities.IElementalAttackData;
import Tavi007.ElementalCombat.capabilities.IElementalDefenseData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ElementalCombatAPI 
{
	//LivingEntity
	public static IElementalAttackData getElementalAttackData(LivingEntity entity){
		return entity.getCapability(ElementalAttackDataCapability.ATK_DATA_CAPABILITY, null).orElse(new ElementalAttackData());
	}
	
	public static IElementalDefenseData getElementalDefenseData(LivingEntity entity){
		return entity.getCapability(ElementalDefenseDataCapability.DEF_DATA_CAPABILITY, null).orElse(new ElementalDefenseData());
	}
	
	//ItemStack
	public static IElementalAttackData getElementalAttackData(ItemStack item){
		return item.getCapability(ElementalAttackDataCapability.ATK_DATA_CAPABILITY, null).orElse(new ElementalAttackData());
	}
	
	public static IElementalDefenseData getElementalDefenseData(ItemStack item){
		return item.getCapability(ElementalDefenseDataCapability.DEF_DATA_CAPABILITY, null).orElse(new ElementalDefenseData());
	}
	
	//Projectiles
}
