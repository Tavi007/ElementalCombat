package Tavi007.ElementalCombat.potions;

import java.util.Arrays;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;

public class SpecificPotionIngredient extends Ingredient {

    private Potion potion;

    public SpecificPotionIngredient(ItemStack stack) {
        super(Arrays.stream(new IItemList[] { new SingleItemList(stack) }));
        this.potion = PotionUtils.getPotion(stack);
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        return super.test(stack) && PotionUtils.getPotion(stack).equals(potion);
    }

}
