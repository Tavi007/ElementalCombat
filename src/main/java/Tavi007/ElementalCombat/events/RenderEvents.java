package Tavi007.ElementalCombat.events;

import java.util.List;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.datafixers.util.Either;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.immersion.ImmersionData;
import Tavi007.ElementalCombat.capabilities.immersion.ImmersionDataCapability;
import Tavi007.ElementalCombat.client.CombatDataLayerClientComponent;
import Tavi007.ElementalCombat.client.CombatDataLayerComponent;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.util.AttackDataHelper;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, value = Dist.CLIENT, bus = Bus.FORGE)
public class RenderEvents {

    @SubscribeEvent
    public static void onRenderLivingEventPre(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
        LivingEntity entityIn = event.getEntity();
        ImmersionData data = (ImmersionData) entityIn.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null).orElse(new ImmersionData());
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
        ImmersionData data = (ImmersionData) entityIn.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null).orElse(new ImmersionData());
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
                ImmersionData data = (ImmersionData) mc.player.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null)
                    .orElse(new ImmersionData());
                if (data.disableFlag) {
                    // Use the same calculation as in GameRenderer#hurtCameraEffect.
                    float f = (float) (mc.player.hurtTime - event.getPartialTick());
                    f = f / (float) mc.player.hurtDuration;
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
                ImmersionData data = (ImmersionData) mc.player.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null)
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
        AttackData attackData = AttackDataHelper.get(stack);
        DefenseData defenseData = DefenseDataHelper.get(stack);

        tooltip.add(Either.right(new CombatDataLayerComponent(
            attackData.toLayer(),
            defenseData.toLayer(),
            false,
            false,
            ClientConfig.isDoubleRowDefenseTooltip())));
    }

    private static int ticks = 0;

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

    @SubscribeEvent
    public static void displayElementalCombatHUD(RenderGuiOverlayEvent.Post event) {
        if (ClientConfig.isHUDEnabled()) {
            // see Screen#renderToolTips in client.gui.screen
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                float scale = (float) ClientConfig.scale();
                AttackData attackData = AttackDataHelper.get(mc.player);
                DefenseData defenseData = DefenseDataHelper.get(mc.player);

                CombatDataLayerClientComponent clientComponent = new CombatDataLayerClientComponent(
                    new CombatDataLayerComponent(
                        attackData.toLayer(),
                        defenseData.toLayer(),
                        true,
                        false,
                        ClientConfig.isDoubleRowDefenseHUD()));

                // the width of the box.
                int listWidth = clientComponent.getWidth(mc.font);
                int listHeight = clientComponent.getHeight();

                // moves the coords so the text and box appear correct
                int posX = 0;
                int maxPosX = (int) (event.getWindow().getGuiScaledWidth() / scale) - listWidth - 12;
                if (ClientConfig.isLeft()) {
                    posX = Math.min(12 + ClientConfig.getXOffset(), maxPosX);
                } else {
                    posX = Math.max(12, maxPosX - ClientConfig.getXOffset());
                }
                int posY = 0;
                int maxPosY = (int) (event.getWindow().getGuiScaledHeight() / scale) - listHeight - 12;
                if (ClientConfig.isTop()) {
                    posY = Math.min(12 + ClientConfig.getYOffset(), maxPosY);
                } else {
                    posY = Math.max(12, maxPosY - ClientConfig.getYOffset());
                }

                // rendering starts here
                PoseStack poseStack = event.getPoseStack();
                poseStack.pushPose();
                poseStack.scale(scale, scale, scale);

                renderHUDBox(poseStack, posX, posY, listWidth, listHeight);

                // render component
                clientComponent.renderText(mc.font, poseStack, posX, posY);
                clientComponent.renderImage(mc.font, posX, posY, poseStack, null, 0);

                poseStack.popPose();
            }
        }
    }

    // stolen from Screen
    private static void renderHUDBox(PoseStack poseStack, int posX, int posY, int width, int height) {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        TooltipRenderUtil.renderTooltipBackground((p_262872_, p_262873_, p_262874_, p_262875_, p_262876_, p_262877_, p_262878_, p_262879_, p_262880_) -> {
            fillGradient(p_262872_, p_262873_, p_262874_, p_262875_, p_262876_, p_262877_, p_262878_, p_262879_, p_262880_);
        }, poseStack.last().pose(), bufferbuilder, posX, posY, width, height, 400);
    }

    // copied from GuiComponent
    protected static void fillGradient(Matrix4f p_254526_, BufferBuilder p_93125_, int p_93126_, int p_93127_, int p_93128_, int p_93129_, int p_93130_,
            int p_93131_, int p_93132_) {
        float f = (float) (p_93131_ >> 24 & 255) / 255.0F;
        float f1 = (float) (p_93131_ >> 16 & 255) / 255.0F;
        float f2 = (float) (p_93131_ >> 8 & 255) / 255.0F;
        float f3 = (float) (p_93131_ & 255) / 255.0F;
        float f4 = (float) (p_93132_ >> 24 & 255) / 255.0F;
        float f5 = (float) (p_93132_ >> 16 & 255) / 255.0F;
        float f6 = (float) (p_93132_ >> 8 & 255) / 255.0F;
        float f7 = (float) (p_93132_ & 255) / 255.0F;
        p_93125_.vertex(p_254526_, (float) p_93128_, (float) p_93127_, (float) p_93130_).color(f1, f2, f3, f).endVertex();
        p_93125_.vertex(p_254526_, (float) p_93126_, (float) p_93127_, (float) p_93130_).color(f1, f2, f3, f).endVertex();
        p_93125_.vertex(p_254526_, (float) p_93126_, (float) p_93129_, (float) p_93130_).color(f5, f6, f7, f4).endVertex();
        p_93125_.vertex(p_254526_, (float) p_93128_, (float) p_93129_, (float) p_93130_).color(f5, f6, f7, f4).endVertex();
    }
}
