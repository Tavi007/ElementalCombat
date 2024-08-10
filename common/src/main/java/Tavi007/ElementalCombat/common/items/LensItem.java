package Tavi007.ElementalCombat.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LensItem extends Item {

    public LensItem(Properties builderIn) {
        super(builderIn);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal(ChatFormatting.GRAY
                + "Dev Tool. Click on an mob to see its properties. "
                + "Take damage while holding the lens to see the attack properties of the damage source." + ChatFormatting.RESET));
    }

}
