package Tavi007.ElementalCombat.particle;

import net.minecraft.particles.ParticleType;

public class ResistanceParticleType extends ParticleType<ResistanceParticleData> {

	private static boolean ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER = false;

	public ResistanceParticleType() {
		super(ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER, ResistanceParticleData.DESERIALIZER);
	}

}