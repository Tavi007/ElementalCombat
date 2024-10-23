package Tavi007.ElementalCombat.common.events;

import Tavi007.ElementalCombat.common.api.DefenseDataAPI;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

public class EquipmentEvents {

    public static void livingEquipmentChange(LivingEntity entity, EquipmentSlot slot, ItemStack to) {
        if (entity == null) {
            return;
        }

        if (EquipmentSlot.MAINHAND.equals(slot)) {
            CapabilitiesHelper.updateMainHandItemLayer(entity);
        }
//TODO: fix
//        GainDefenseFromEquipmentEvent gainDefenseEvent = new GainDefenseFromEquipmentEvent(
//                event.getTo(),
//                event.getSlot());
//        MinecraftForge.EVENT_BUS.post(gainDefenseEvent);
        boolean isCanceled = false;
        if (!isCanceled) {
            DefenseDataAPI.putLayer(
                    entity,
                    CapabilitiesAccessors.getDefenseData(to).toLayer(),
                    new ResourceLocation(slot.name().toLowerCase()));
        }
    }

    public static boolean shouldItemGiveDefenseData(ItemStack stack, EquipmentSlot slot) {
        if (!Potions.EMPTY.equals(PotionUtils.getPotion(stack))) {
            return true;
        }
        if (EquipmentSlot.Type.HAND.equals(slot.getType())) {
            Item item = stack.getItem();
            return item instanceof ArmorItem || item instanceof EnchantedBookItem;
        }
        return true;
    }
}
