package Tavi007.ElementalCombat.items;


import java.util.List;

import javax.annotation.Nullable;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.util.ElementalCombatNBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
        ElementalCombatNBTHelper.writeAttackDataToNBT(nbt, ElementalCombatAPI.getAttackData(stack));
        return nbt;
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    	tooltip.add(new StringTextComponent("" + TextFormatting.GRAY + "Right-click to toggle element" + TextFormatting.RESET));
    }

    @Override
	public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        stack.setTag(nbt);
        ElementalCombatAPI.getAttackData(stack).set(ElementalCombatNBTHelper.readAttackDataFromNBT(nbt));
    }
}
