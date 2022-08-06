package Tavi007.ElementalCombat.init;

import org.lwjgl.glfw.GLFW;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.client.CombatParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class StartupClientOnly {

    public static final KeyBinding TOGGLE_HUD = new KeyBinding("Toggle HUD",
        KeyConflictContext.UNIVERSAL,
        InputMappings.Type.KEYSYM,
        GLFW.GLFW_KEY_J,
        "Elemental Combat");

    @SubscribeEvent
    public static void onClientSetupEvent(final FMLClientSetupEvent event) {
        // key bindings
        ClientRegistry.registerKeyBinding(TOGGLE_HUD);

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
