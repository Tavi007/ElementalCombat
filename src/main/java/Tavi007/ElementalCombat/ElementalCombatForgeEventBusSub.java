package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.capabilities.ElementalDefenceDataCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ElementalCombatForgeEventBusSub 
{
	private static final ResourceLocation DEFENCE = new ResourceLocation(ElementalCombat.MOD_ID, "elemental_combat_defence");
	
	@SubscribeEvent
    public static void onAttachEntity(AttachCapabilitiesEvent<Entity> e)
    {
        if(e.getObject().getEntity() instanceof LivingEntity) e.addCapability(DEFENCE, new ElementalDefenceDataCapability());
    }
}
