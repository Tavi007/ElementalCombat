package Tavi007.ElementalCombat.init;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.client.CombatDataHudOverlay;
import Tavi007.ElementalCombat.client.CombatDataLayerClientComponent;
import Tavi007.ElementalCombat.client.CombatDataLayerComponent;
import Tavi007.ElementalCombat.client.CombatParticle;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StartupClientOnly {

    public static final KeyMapping TOGGLE_HUD = new KeyMapping("Toggle HUD",
        KeyConflictContext.UNIVERSAL,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_J,
        "Elemental Combat");

    @SubscribeEvent
    public static void onRegisterKeyMappingsEvent(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_HUD);
        ElementalCombat.LOGGER.info("ElementalCombat key mappings registered.");
    }

    @SubscribeEvent
    public static void onRegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(CombatDataLayerComponent.class, CombatDataLayerClientComponent::new);
        ElementalCombat.LOGGER.info("ElementalCombat tooltip component factory registered.");
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlayst(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("player_data", new CombatDataHudOverlay());
        ElementalCombat.LOGGER.info("ElementalCombat hud overlay registered.");
    }

    @SubscribeEvent
    public static void onRegisterParticleProvidersEvent(RegisterParticleProvidersEvent event) {
        event.register(ParticleList.CRIT_ELEMENT.get(), CombatParticle.Factory::new);
        event.register(ParticleList.CRIT_STYLE.get(), CombatParticle.Factory::new);
        event.register(ParticleList.RESIST_ELEMENT.get(), CombatParticle.Factory::new);
        event.register(ParticleList.RESIST_STYLE.get(), CombatParticle.Factory::new);
        event.register(ParticleList.ABSORB.get(), CombatParticle.Factory::new);

        ElementalCombat.LOGGER.info("ElementalCombat particles factory registered.");
    }
}
