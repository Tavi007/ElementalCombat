package Tavi007.ElementalCombat.common.potions;

import java.util.Arrays;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;

public class SpecificPotionIngredient extends Ingredient {

    private final Potion potion;

    public SpecificPotionIngredient(ItemStack stack) {
        super(Stream.of(new Ingredient.ItemValue(stack)));
        this.potion = PotionUtils.getPotion(stack);
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        if(stack == null) {
            return false;
        }
        return super.test(stack) && PotionUtils.getPotion(stack).equals(potion);
    }

}
