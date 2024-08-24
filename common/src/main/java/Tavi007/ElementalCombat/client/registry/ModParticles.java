package Tavi007.ElementalCombat.client.registry;

import Tavi007.ElementalCombat.common.Constants;
import net.minecraft.core.particles.ParticleType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ModParticles {

    private static final Map<String, ParticleType<?>> registry = new HashMap<>();
    public static final CombatParticleType CRIT_ELEMENT = register(Constants.CRIT_ELEMENT, new CombatParticleType(false));
    public static final CombatParticleType CRIT_STYLE = register(Constants.CRIT_STYLE, new CombatParticleType(false));
    public static final CombatParticleType RESIST_ELEMENT = register(Constants.RESIST_ELEMENT, new CombatParticleType(false));
    public static final CombatParticleType RESIST_STYLE = register(Constants.RESIST_STYLE, new CombatParticleType(false));
    public static final CombatParticleType ABSORB = register(Constants.ABSORB, new CombatParticleType(false));

    private static CombatParticleType register(String name, ParticleType<?> particles) {
        registry.put(name, particles);
        return (CombatParticleType) particles;
    }

    public static void register(BiConsumer<String, ParticleType<?>> registerConsumer) {
        registry.forEach((name, particles) -> registerConsumer.accept(name, particles));
    }
}
