package Tavi007.ElementalCombat.init;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.client.CombatParticle;
import Tavi007.ElementalCombat.client.ElementalCombatClientComponent;
import Tavi007.ElementalCombat.client.ElementalCombatComponent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

public class StartupClientOnly {

    public static final KeyMapping TOGGLE_HUD = new KeyMapping("Toggle HUD",
        KeyConflictContext.UNIVERSAL,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_J,
        "Elemental Combat");

    @SubscribeEvent
    public static void onClientSetupEvent(final FMLClientSetupEvent event) {
        // key bindings
        ClientRegistry.registerKeyBinding(TOGGLE_HUD);

        MinecraftForgeClient.registerTooltipComponentFactory(ElementalCombatComponent.class,
            component -> {
                return new ElementalCombatClientComponent(component);
            });

        ElementalCombat.LOGGER.info("ElementalCombat clientRegistries method registered.");
    }

    @SuppressWarnings("resource")
    @SubscribeEvent
    public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleList.CRIT_ELEMENT.get(), CombatParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleList.CRIT_STYLE.get(), CombatParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleList.RESIST_ELEMENT.get(), CombatParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleList.RESIST_STYLE.get(), CombatParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleList.ABSORB.get(), CombatParticle.Factory::new);

        ElementalCombat.LOGGER.info("ElementalCombat particles factory registered.");
    }
}
