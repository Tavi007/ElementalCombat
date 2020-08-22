package Tavi007.ElementalCombat.particle;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleList {
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ElementalCombat.MOD_ID);
	
	public static final RegistryObject<BasicParticleType> ELEMENT_WEAKNESS = PARTICLES.register("element_weakness", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> ELEMENT_RESISTANCE = PARTICLES.register("element_resistance", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> ELEMENT_IMMUNITY = PARTICLES.register("element_immunity", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> ELEMENT_ABSORPTION = PARTICLES.register("element_absorption", () -> new BasicParticleType(true));

	public static final RegistryObject<BasicParticleType> STYLE_WEAKNESS = PARTICLES.register("style_weakness", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> STYLE_RESISTANCE = PARTICLES.register("style_resistance", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> STYLE_IMMUNITY = PARTICLES.register("style_immunity", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> STYLE_ABSORPTION = PARTICLES.register("style_absorption", () -> new BasicParticleType(true));
}
