package Tavi007.ElementalCombat.items;

import java.util.List;

import javax.annotation.Nullable;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LensItem extends Item {

    public LensItem(Properties builderIn) {
        super(builderIn);
    }

    // @Override
    // public CompoundNBT getShareTag(ItemStack stack) {
    // CompoundNBT nbt = stack.getTag();
    // DefenseDataAPI.writeToNBT(nbt, stack);
    // return nbt;
    // }
    //
    // @Override
    // public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
    // DefenseDataAPI.readFromNBT(nbt, stack);
    // stack.setTag(nbt);
    // }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        DefenseLayer layer = DefenseDataAPI.getFullDataAsLayer(target);
        DefenseDataAPI.putLayer(stack, layer, new ResourceLocation(ElementalCombat.MOD_ID, "lens"));
        return ActionResultType.PASS;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("" + TextFormatting.GRAY
            + "Dev Tool. Attack mob to see its defense properties. "
            + "Take damage while holding the mirror to see the attack properties." + TextFormatting.RESET));
    }

}
