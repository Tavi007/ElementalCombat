package Tavi007.ElementalCombat.particle;

import net.minecraft.particles.ParticleType;

public class ImmunityParticleType extends ParticleType<ImmunityParticleData> {

	private static boolean ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER = false;

	public ImmunityParticleType() {
		super(ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER, ImmunityParticleData.DESERIALIZER);
	}

}