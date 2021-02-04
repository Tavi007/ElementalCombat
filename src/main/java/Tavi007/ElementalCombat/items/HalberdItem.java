package Tavi007.ElementalCombat.items;

import javax.annotation.Nullable;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.util.ElementalCombatNBTHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class HalberdItem extends SwordItem {

	public HalberdItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		AttackData atckData = ElementalCombatAPI.getAttackData(stack);
		if (atckData.getStyle().equals("slash")) {
			atckData.setStyle("stab");
		}
		else {
			atckData.setStyle("slash");
		}
		return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
	}
	

    @Override
    public CompoundNBT getShareTag(ItemStack stack)
    {
        CompoundNBT nbt = stack.getTag();
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, ElementalCombatAPI.getAttackData(stack));
        return nbt;
    }

    @Override
	public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        stack.setTag(nbt);
        ElementalCombatAPI.getAttackData(stack).set(ElementalCombatNBTHelper.readAttackDataFromNBT(nbt));
    }

}