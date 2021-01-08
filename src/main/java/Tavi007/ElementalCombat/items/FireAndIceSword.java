package Tavi007.ElementalCombat.items;


import javax.annotation.Nullable;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.util.NBTHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FireAndIceSword extends SwordItem{

	public FireAndIceSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties p_i48460_4_) {
		super(tier, attackDamageIn, attackSpeedIn, p_i48460_4_);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		AttackData atckData = ElementalCombatAPI.getAttackData(stack);
		if (atckData.getElement().equals("fire")) {
			atckData.setElement("ice");
		}
		else {
			atckData.setElement("fire");
		}
		return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
	}
	

    @Override
    public CompoundNBT getShareTag(ItemStack stack)
    {
        CompoundNBT nbt = stack.getTag();
        NBTHelper.writeAttackDataToNBT(nbt, ElementalCombatAPI.getAttackData(stack));
        return nbt;
    }

    @Override
	public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        stack.setTag(nbt);
        ElementalCombatAPI.getAttackData(stack).set(NBTHelper.readAttackDataFromNBT(nbt));
    }
}
