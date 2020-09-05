package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackDataCapability;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseDataCapability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;

public class ElementalCombatAPI 
{

	//LivingEntity
	public static AttackData getAttackData(LivingEntity entity){
		return (AttackData) entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
	}

	public static DefenseData getDefenseData(LivingEntity entity){
		return (DefenseData) entity.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
	}

	//ItemStack
	public static AttackData getAttackData(ItemStack item){
		AttackData attackData = (AttackData) item.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
		if(!attackData.areEnchantmentsApplied() && !(item.getItem() instanceof EnchantedBookItem)) {
			attackData.applyEnchantments(EnchantmentHelper.getEnchantments(item));
		}
		return attackData;
	}

	public static DefenseData getDefenseData(ItemStack item){
		DefenseData defenseData = (DefenseData) item.getCapability(DefenseDataCapability.ELEMENTAL_DEFENSE_CAPABILITY, null).orElse(new DefenseData());
		if(!defenseData.areEnchantmentsApplied() && !(item.getItem() instanceof EnchantedBookItem)) {
			defenseData.applyEnchantments(EnchantmentHelper.getEnchantments(item));
			}
		return defenseData;
	}

	//Projectiles
	public static AttackData getAttackData(ProjectileEntity entity){
		return (AttackData) entity.getCapability(AttackDataCapability.ELEMENTAL_ATTACK_CAPABILITY, null).orElse(new AttackData());
	}
}
