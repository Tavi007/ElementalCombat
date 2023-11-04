package Tavi007.ElementalCombat.api;

import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class GainDefenseFromEquipmentEvent extends Event {

    private ItemStack stack;
    private EquipmentSlotType slotType;
    private DefenseLayer defenseLayer;

    public GainDefenseFromEquipmentEvent(ItemStack stack, EquipmentSlotType slotType, DefenseLayer defenseLayer) {
        this.stack = stack;
        this.slotType = slotType;
        this.defenseLayer = defenseLayer;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public EquipmentSlotType getEquipmentSlotType() {
        return slotType;
    }

    public DefenseLayer getDefenseLayer() {
        return defenseLayer;
    }

    public void setDefenseLayer(DefenseLayer defenseLayer) {
        this.defenseLayer = defenseLayer;
    }

}
