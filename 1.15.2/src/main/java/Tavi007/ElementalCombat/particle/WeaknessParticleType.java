package Tavi007.ElementalCombat.particle;

import net.minecraft.particles.ParticleType;

public class WeaknessParticleType extends ParticleType<WeaknessParticleData> {

	private static boolean ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER = false;

	public WeaknessParticleType() {
		super(ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER, WeaknessParticleData.DESERIALIZER);
	}
}