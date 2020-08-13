package Tavi007.ElementalCombat.particle;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleList {
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ElementalCombat.MOD_ID);
	
	public static final RegistryObject<BasicParticleType> WEAKNESS_PARTICLE = PARTICLES.register("weakness", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> RESISTANCE_PARTICLE = PARTICLES.register("resistance", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> IMMUNITY_PARTICLE = PARTICLES.register("immunity", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> ABSORPTION_PARTICLE = PARTICLES.register("absorption", () -> new BasicParticleType(true));
	
}
