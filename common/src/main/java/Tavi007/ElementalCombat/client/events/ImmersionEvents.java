package Tavi007.ElementalCombat.client.events;

import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class ImmersionEvents {


    public static void onRenderLivingEventPre(LivingEntity entity) {
        if (entity == null) {
            return;
        }
        ImmersionData data = CapabilitiesAccessors.getImmersionData(entity);
        if (entity.hurtTime > 0) {
            if (data.disableFlag) {
                data.setHurtTime(entity.hurtTime);
                entity.hurtTime = 0; // desync client and server hurtTime.
            }
        } else {
            data.disableFlag = false;
        }
    }


    public static void onRenderLivingEventPost(LivingEntity entity) {
        if (entity == null) {
            return;
        }
        ImmersionData data = CapabilitiesAccessors.getImmersionData(entity);
        if (data.disableFlag && data.getHurtTime() > 0) {
            entity.hurtTime = data.getHurtTime();
            data.setHurtTime(0);
        }
    }


    public static float getAntiScreenshakeCameraRoll(double partialTick) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            if (mc.player.hurtTime > 0) {
                ImmersionData data = CapabilitiesAccessors.getImmersionData(mc.player);
                if (data.disableFlag) {
                    // Use the same calculation as in GameRenderer#hurtCameraEffect.
                    float deltaT = (float) (mc.player.hurtTime - partialTick);
                    deltaT = deltaT / mc.player.hurtDuration;
                    deltaT = Mth.sin(deltaT * deltaT * deltaT * deltaT * (float) Math.PI);
                    return deltaT * 14.0F;
                }
            }
        }
        return 0.0f;
    }


    public static boolean shouldSoundBePlayed(SoundInstance soundInstance) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            if (mc.player.hurtTime == mc.player.hurtDuration) {
                ImmersionData data = CapabilitiesAccessors.getImmersionData(mc.player);
                if (data.disableFlag) {
                    // What if other mods implements their own version of an hurt sound?
                    // Also what if the on_fire sound gets disabled, even so I still took fire damage?
                    return !soundInstance.getLocation().equals(SoundEvents.PLAYER_HURT.getLocation()) &&
                            !soundInstance.getLocation().equals(SoundEvents.PLAYER_HURT_DROWN.getLocation()) &&
                            !soundInstance.getLocation().equals(SoundEvents.PLAYER_HURT_ON_FIRE.getLocation()) &&
                            !soundInstance.getLocation().equals(SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH.getLocation());
                }
            }
        }
        return true;
    }
}
