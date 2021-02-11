package Tavi007.ElementalCombat.items;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.util.CollectionUtil;
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

public class ElementSwitchingSword extends SwordItem {

	private Set<String> elements = new HashSet<String>();
	
	public ElementSwitchingSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Set<String> elements, Properties p_i48460_4_) {
		super(tier, attackDamageIn, attackSpeedIn, p_i48460_4_);
		if(elements.isEmpty()) {
			this.elements.add(ServerConfig.getDefaultElement());
		}
		else {
			this.elements = elements;
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		AttackData atckData = ElementalCombatAPI.getAttackData(stack);
		String nextElement = CollectionUtil.getNext(elements, atckData.getElement(), true);
		if(nextElement != null) {
			atckData.setElement(nextElement);
			return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
		}
		else {
			atckData.setElement(ServerConfig.getDefaultElement());
			return ActionResult.resultFail(playerIn.getHeldItem(handIn));
		}
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
    	tooltip.add(new StringTextComponent("" + TextFormatting.GRAY + "Right-click to switch element" + TextFormatting.RESET));
    }

    @Override
	public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        stack.setTag(nbt);
        ElementalCombatAPI.getAttackData(stack).set(ElementalCombatNBTHelper.readAttackDataFromNBT(nbt));
    }
}
