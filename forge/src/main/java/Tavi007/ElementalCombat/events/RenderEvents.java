package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.client.events.ImmersionEvents;
import Tavi007.ElementalCombat.client.events.TooltipEvents;
import Tavi007.ElementalCombat.client.gui.CombatDataLayerComponent;
import Tavi007.ElementalCombat.common.Constants;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;


@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Bus.FORGE)
public class RenderEvents {

    @SubscribeEvent
    public static void onRenderLivingEventPre(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
        ImmersionEvents.onRenderLivingEventPre(event.getEntity());
    }

    @SubscribeEvent
    public static void onRenderLivingEventPost(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
        ImmersionEvents.onRenderLivingEventPost(event.getEntity());
    }

    @SubscribeEvent
    public static void onEntityViewRenderEvent(ComputeCameraAngles event) {
        event.setRoll(ImmersionEvents.getAntiScreenshakeCameraRoll(event.getPartialTick()));
    }

    @SubscribeEvent
    public static void onPlaySoundEvent(PlaySoundEvent event) {
        if (!ImmersionEvents.shouldSoundBePlayed(event.getSound())) {
            event.setResult(null);
        }
    }

    @SubscribeEvent
    public static void onGatherTooltip(RenderTooltipEvent.GatherComponents event) {
        TooltipEvents.onGatherTooltip(event.getTooltipElements(), event.getItemStack());
    }

    @SubscribeEvent
    public static void onClientTickEvent(TickEvent event) {
        if (TickEvent.Type.CLIENT.equals(event.type)) {
            CombatDataLayerComponent.increaseTickCounter();
        }
    }
}
