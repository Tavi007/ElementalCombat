package Tavi007.ElementalCombat.potions;

import java.util.Arrays;

import javax.annotation.Nullable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;

public class SpecificPotionIngredient extends Ingredient {

    private Potion potion;

    public SpecificPotionIngredient(ItemStack stack) {
        super(Arrays.asList(new Ingredient.ItemValue(stack)).stream());
        this.potion = PotionUtils.getPotion(stack);
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        return super.test(stack) && PotionUtils.getPotion(stack).equals(potion);
    }

}
