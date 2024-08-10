package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.common.Constants;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleList {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Constants.MOD_ID);

    public static final RegistryObject<SimpleParticleType> CRIT_ELEMENT = PARTICLES.register(Constants.CRIT_ELEMENT, () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CRIT_STYLE = PARTICLES.register(Constants.CRIT_STYLE, () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> RESIST_ELEMENT = PARTICLES.register(Constants.RESIST_ELEMENT, () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> RESIST_STYLE = PARTICLES.register(Constants.RESIST_STYLE, () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ABSORB = PARTICLES.register(Constants.ABSORB, () -> new SimpleParticleType(false));
}
