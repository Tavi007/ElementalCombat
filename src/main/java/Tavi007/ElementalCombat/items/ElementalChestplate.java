package Tavi007.ElementalCombat.items;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ElementalChestplate extends  ArmorItem {

	String[] elementMode = {"fire", "ice"};
	int counter = 0;

	public ElementalChestplate(IArmorMaterial materialIn, EquipmentSlotType slot, Properties p_i48534_3_) {
		super(materialIn, slot, p_i48534_3_);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(!worldIn.isRemote()) {
			counter = counter +1;
			if (counter == elementMode.length) {counter = 0;}

			ItemStack itemstack = playerIn.getHeldItem(Hand.MAIN_HAND);
			DefenseData data = ElementalCombatAPI.getDefenseData(itemstack);
			HashMap<String, Integer> elemFactor = data.getElementFactor();
			elemFactor.clear();
			elemFactor.put(elementMode[counter], 1);
			data.setElementFactor(elemFactor);
		}
		return ActionResult.resultPass(playerIn.getHeldItem(handIn));
	}
}
