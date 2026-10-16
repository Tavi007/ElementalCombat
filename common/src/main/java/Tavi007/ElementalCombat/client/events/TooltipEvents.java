package Tavi007.ElementalCombat.client.events;

import com.mojang.datafixers.util.Either;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static Tavi007.ElementalCombat.client.data.ClientDataHandler.getTooltipData;

public class TooltipEvents {

    public static void onGatherTooltip(List<Either<FormattedText, TooltipComponent>> tooltip, ItemStack stack) {
//        AttackData attackData = CapabilitiesAccessors.getAttackData(stack);
//        DefenseData defenseData = CapabilitiesAccessors.getDefenseData(stack);

        // TODO: get correct values
        tooltip.add(Either.right(getTooltipData(null, null)));
    }

}
