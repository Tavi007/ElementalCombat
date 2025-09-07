package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.client.ForgeCombatDataHudOverlay;
import Tavi007.ElementalCombat.client.gui.CombatDataComponent;
import Tavi007.ElementalCombat.client.gui.CombatDataTooltipComponent;
import Tavi007.ElementalCombat.client.particles.CombatParticle;
import Tavi007.ElementalCombat.client.registry.ModParticles;
import Tavi007.ElementalCombat.common.Constants;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class StartupClientOnly {

    public static final KeyMapping TOGGLE_HUD = new KeyMapping("Toggle HUD",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_J,
            "Elemental Combat");

    @SubscribeEvent
    public static void onRegisterKeyMappingsEvent(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_HUD);
        Constants.LOG.info("ElementalCombat key mappings registered.");
    }

    @SubscribeEvent
    public static void onRegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(CombatDataComponent.class, CombatDataTooltipComponent::new);
        Constants.LOG.info("ElementalCombat tooltip component factory registered.");
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("elemental_combat_player_data", new ForgeCombatDataHudOverlay());
        Constants.LOG.info("ElementalCombat hud overlay registered.");
    }

    @SubscribeEvent
    public static void onRegisterParticleProvidersEvent(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.CRIT_ELEMENT, CombatParticle.Factory::new);
        event.registerSpriteSet(ModParticles.CRIT_STYLE, CombatParticle.Factory::new);
        event.registerSpriteSet(ModParticles.RESIST_ELEMENT, CombatParticle.Factory::new);
        event.registerSpriteSet(ModParticles.RESIST_STYLE, CombatParticle.Factory::new);
        event.registerSpriteSet(ModParticles.ABSORB, CombatParticle.Factory::new);

        Constants.LOG.info("ElementalCombat particles factory registered.");
    }
}
