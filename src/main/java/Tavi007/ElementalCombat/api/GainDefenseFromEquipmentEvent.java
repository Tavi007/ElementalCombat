package Tavi007.ElementalCombat.api;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * {@link GainDefenseFromEquipmentEvent} is fired when the Equipment of a LivingEntity changes. <br>
 * This event is fired whenever <br>
 * {@link net.minecraftforge.event.entity.living.LivingEvent.LivingEquipmentChangeEvent} <br>
 * happens.
 * This also includes entities joining the World, as well as being cloned. <br>
 * This event is fired on server-side only. <br>
 * <br>
 * {@link #slot} contains the affected {@link EquipmentSlotType}. <br>
 * {@link #stack} contains the {@link ItemStack} that is equipped now.
 * <br>
 * This event is {@link Cancelable} and canceling means, <br>
 * that the defense values of the item won't be added to the entity. <br>
 * For example a potion should not add its values by holding the item. <br>
 * <br>
 * This event does not have a result. {@link HasResult} <br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
@Cancelable
public class GainDefenseFromEquipmentEvent extends Event {

    private ItemStack stack;
    private EquipmentSlotType slot;

    public GainDefenseFromEquipmentEvent(ItemStack stack, EquipmentSlotType slot) {
        this.stack = stack;
        this.slot = slot;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public EquipmentSlotType getEquipmentSlot() {
        return slot;
    }

}
