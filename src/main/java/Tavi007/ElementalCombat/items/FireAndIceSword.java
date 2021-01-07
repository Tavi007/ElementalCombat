package Tavi007.ElementalCombat.items;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class FireAndIceSword extends SwordItem{

	public FireAndIceSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties p_i48460_4_) {
		super(tier, attackDamageIn, attackSpeedIn, p_i48460_4_);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		String textAttackElement;
		AttackData atckData = ElementalCombatAPI.getAttackData(stack);
		if (atckData.getElement() == "fire") {
			atckData.setElement("ice");
			textAttackElement = "Attack Element: " + TextFormatting.BLUE + "Ice" + TextFormatting.RESET;
		}
		else {
			atckData.setElement("fire");
			textAttackElement = "Attack Element: " + TextFormatting.RED + "Fire" + TextFormatting.RESET;
		}
		if (!worldIn.isRemote()) {
			playerIn.sendMessage(new StringTextComponent(textAttackElement), playerIn.getUniqueID());
		}
		return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
	}
}
