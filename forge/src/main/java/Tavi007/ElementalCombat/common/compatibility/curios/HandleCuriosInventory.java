package Tavi007.ElementalCombat.common.compatibility.curios;

import Tavi007.ElementalCombat.common.api.GainDefenseFromEquipmentEvent;
import Tavi007.ElementalCombat.common.events.EquipmentEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class HandleCuriosInventory {

    @SubscribeEvent
    public static void onCurioChange(CurioChangeEvent event) {
        LivingEntity entity = event.getEntity();
        // create unique rl for the current curio
        ResourceLocation rl = new ResourceLocation(CuriosApi.MODID, String.valueOf(event.getSlotIndex()));
        EquipmentEvents.changeEquipment(entity, rl, event.getTo());
    }

    @SubscribeEvent
    public static void gainDefenseFromEquipmentEvent(GainDefenseFromEquipmentEvent event) {
        if (EquipmentSlot.Type.HAND.equals(event.getEquipmentSlot().getType())) {
            event.setCanceled(event.getItemStack().getItem() instanceof ICurioItem);
        }
    }
}
