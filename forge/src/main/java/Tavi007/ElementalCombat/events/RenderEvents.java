package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.client.ClientConfig;
import Tavi007.ElementalCombat.client.CombatDataLayerComponent;
import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.capabilities.ImmersionDataCapability;
import Tavi007.ElementalCombat.common.data.DatapackDataAccessor;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
import Tavi007.ElementalCombat.common.util.DamageCalculationHelper;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.List;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Bus.FORGE)
public class RenderEvents {

    private static int ticks = 0;

    @SubscribeEvent
    public static void onRenderLivingEventPre(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
        LivingEntity entityIn = event.getEntity();
        if (entityIn == null) {
            return;
        }
        ImmersionData data = entityIn.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null).orElse(new ImmersionData());
        if (entityIn.hurtTime > 0) {
            if (data.disableFlag) {
                data.setHurtTime(entityIn.hurtTime);
                entityIn.hurtTime = 0; // desync client and server hurtTime.
            }
        } else {
            data.disableFlag = false;
        }
    }

    @SubscribeEvent
    public static void onRenderLivingEventPost(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
        LivingEntity entityIn = event.getEntity();
        if (entityIn == null) {
            return;
        }
        ImmersionData data = entityIn.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null).orElse(new ImmersionData());
        if (data.disableFlag && data.getHurtTime() > 0) {
            entityIn.hurtTime = data.getHurtTime();
            data.setHurtTime(0);
        }
    }

    @SubscribeEvent
    public static void onEntityViewRenderEvent(ComputeCameraAngles event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            if (mc.player.hurtTime > 0) {
                ImmersionData data = mc.player.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null)
                        .orElse(new ImmersionData());
                if (data.disableFlag) {
                    // Use the same calculation as in GameRenderer#hurtCameraEffect.
                    float f = (float) (mc.player.hurtTime - event.getPartialTick());
                    f = f / mc.player.hurtDuration;
                    f = Mth.sin(f * f * f * f * (float) Math.PI);
                    event.setRoll(f * 14.0F); // counter acts the screen shake. Only the hand is moving now.
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlaySoundEvent(PlaySoundEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            if (mc.player.hurtTime == mc.player.hurtDuration) {
                ImmersionData data = mc.player.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null)
                        .orElse(new ImmersionData());
                if (data.disableFlag) {
                    // What if other mods implements their own version of an hurt sound?
                    // Also what if the on_fire sound gets disabled, even so I still took fire damage?
                    if (event.getSound().getLocation().equals(SoundEvents.PLAYER_HURT.getLocation()) ||
                            event.getSound().getLocation().equals(SoundEvents.PLAYER_HURT_DROWN.getLocation()) ||
                            event.getSound().getLocation().equals(SoundEvents.PLAYER_HURT_ON_FIRE.getLocation()) ||
                            event.getSound().getLocation().equals(SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH.getLocation())) {
                        event.setResult(null);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onGatherTooltip(RenderTooltipEvent.GatherComponents event) {
        List<Either<FormattedText, TooltipComponent>> tooltip = event.getTooltipElements();
        ItemStack stack = event.getItemStack();
        AttackData attackData = DatapackDataAccessor.get(stack);
        DefenseData defenseData = DamageCalculationHelper.get(stack);

        tooltip.add(Either.right(new CombatDataLayerComponent(
                attackData.toLayer(),
                defenseData.toLayer(),
                false,
                false,
                ClientConfig.isDoubleRowDefenseTooltip())));
    }

    @SubscribeEvent
    public static void onClientTickEvent(TickEvent event) {
        if (TickEvent.Type.CLIENT.equals(event.type)) {
            ticks++;
            if (ticks >= ClientConfig.iterationSpeed()) {
                CombatDataLayerComponent.increaseIteratorCounter();
                ticks = 0;
            }
        }
    }
}
