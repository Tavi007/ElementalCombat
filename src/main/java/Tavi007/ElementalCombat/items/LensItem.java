package Tavi007.ElementalCombat.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LensItem extends Item {

    public LensItem(Properties builderIn) {
        super(builderIn);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TextComponent("" + ChatFormatting.GRAY
            + "Dev Tool. Click on an mob to see its properties. "
            + "Take damage while holding the lens to see the attack properties of the damage source." + ChatFormatting.RESET));
    }

}
