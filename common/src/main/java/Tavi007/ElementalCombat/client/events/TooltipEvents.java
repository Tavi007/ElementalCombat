package Tavi007.ElementalCombat.client.events;

import Tavi007.ElementalCombat.client.ClientConfig;
import Tavi007.ElementalCombat.client.gui.CombatDataLayerComponent;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import com.mojang.datafixers.util.Either;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TooltipEvents {

    public static void onGatherTooltip(List<Either<FormattedText, TooltipComponent>> tooltip, ItemStack stack) {
        AttackData attackData = CapabilitiesAccessors.getAttackData(stack);
        DefenseData defenseData = CapabilitiesAccessors.getDefenseData(stack);

        tooltip.add(Either.right(new CombatDataLayerComponent(
                attackData.toLayer(),
                defenseData.toLayer(),
                false,
                false,
                ClientConfig.isDoubleRowDefenseTooltip())));
    }

}
