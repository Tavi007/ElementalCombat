package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.capabilities.ElementalAttack;
import Tavi007.ElementalCombat.capabilities.ElementalAttackCapability;
import Tavi007.ElementalCombat.capabilities.ElementalDefense;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseCapability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ItemStack;

public class ElementalCombatAPI 
{
	//LivingEntity
	public static ElementalAttack getElementalAttackData(LivingEntity entity){
		return entity.getCapability(ElementalAttackCapability.CAPABILITY_ELEMENTAL_ATTACK, null).orElse(new ElementalAttack());
	}
	
	public static ElementalDefense getElementalDefenseData(LivingEntity entity){
		return entity.getCapability(ElementalDefenseCapability.CAPABILITY_ELEMENTAL_DEFENSE, null).orElse(new ElementalDefense());
	}
	
	//ItemStack
	public static ElementalAttack getElementalAttackData(ItemStack item){
		return item.getCapability(ElementalAttackCapability.CAPABILITY_ELEMENTAL_ATTACK, null).orElse(new ElementalAttack());
	}
	
	public static ElementalDefense getElementalDefenseData(ItemStack item){
		return item.getCapability(ElementalDefenseCapability.CAPABILITY_ELEMENTAL_DEFENSE, null).orElse(new ElementalDefense());
	}
	
	//Projectiles
	public static ElementalAttack getElementalAttackData(ProjectileItemEntity entity){
		return entity.getCapability(ElementalAttackCapability.CAPABILITY_ELEMENTAL_ATTACK, null).orElse(new ElementalAttack());
	}
}
