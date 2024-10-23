package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.client.registry.ModParticles;
import Tavi007.ElementalCombat.common.Constants;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiConsumer;

public class ParticleList {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Constants.MOD_ID);

    static {
        BiConsumer<String, ParticleType<?>> registerConsumer = (name, particle) -> {
            PARTICLES.register(name, () -> particle);
        };
        ModParticles.register(registerConsumer);
    }
}
