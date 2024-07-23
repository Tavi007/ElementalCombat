package Tavi007.ElementalCombat.init;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleList {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ElementalCombat.MOD_ID);

    public static final RegistryObject<SimpleParticleType> CRIT_ELEMENT = PARTICLES.register("crit_element", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CRIT_STYLE = PARTICLES.register("crit_style", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> RESIST_ELEMENT = PARTICLES.register("resist_element", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> RESIST_STYLE = PARTICLES.register("resist_style", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ABSORB = PARTICLES.register("absorb", () -> new SimpleParticleType(false));
}
