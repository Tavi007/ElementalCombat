package Tavi007.ElementalCombat.items;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ElementalSword extends SwordItem{

	String[] elementMode = {"fire", "ice"};
	int counter = 0;

	public ElementalSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties p_i48460_4_) {
		super(tier, attackDamageIn, attackSpeedIn, p_i48460_4_);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(!worldIn.isRemote()) {
			counter = counter + 1;
			if (counter == elementMode.length) {counter = 0;}

			ItemStack itemstack = playerIn.getHeldItem(Hand.MAIN_HAND);
			AttackData data = ElementalCombatAPI.getAttackData(itemstack);
			data.setElement(elementMode[counter]);
		}
		return ActionResult.resultPass(playerIn.getHeldItem(handIn));
	}

}
