package Tavi007.ElementalCombat.common.events;

import Tavi007.ElementalCombat.common.api.AttackAPI;
import Tavi007.ElementalCombat.common.api.DefenseAPI;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackBranch;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseProperty;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

import java.util.LinkedList;
import java.util.Queue;

public class EquipmentEvents {

    static private final ResourceLocation MAIN_HAND = new ResourceLocation("mainhand");

    public static void livingEquipmentChange(LivingEntity entity, EquipmentSlot slot, ItemStack to) {
        if (entity == null) {
            return;
        }

        if (EquipmentSlot.MAINHAND.equals(slot)) {
            updateMainHandItemLayer(entity);
        }
//TODO: fix
//        GainDefenseFromEquipmentEvent gainDefenseEvent = new GainDefenseFromEquipmentEvent(
//                event.getTo(),
//                event.getSlot());
//        MinecraftForge.EVENT_BUS.post(gainDefenseEvent);
        boolean isCanceled = false;
        if (!isCanceled) {
            changeEquipment(entity, new ResourceLocation(slot.getName()), to);
        }
    }


    public static void updateMainHandItemLayer(LivingEntity entity) {
        if (entity == null) {
            return;
        }
        AttackConfig config = CapabilitiesAccessors.getAttackConfig(entity.getMainHandItem());

        Queue<ResourceLocation> treePath = new LinkedList<>();
        treePath.add(MAIN_HAND);

        AttackBranch branch = new AttackBranch(config.tree(), 25);
        AttackAPI.put(entity, treePath, branch);
    }

    public static void changeEquipment(LivingEntity entity, ResourceLocation location, ItemStack newEquipment) {
        DefenseConfig config = CapabilitiesAccessors.getDefenseConfig(newEquipment);
        DefenseProperty property = config.getSummedProperty(entity, newEquipment, entity.level());
        DefenseLayer layer = new DefenseLayer(property);
        DefenseAPI.put(entity, layer, location);
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
