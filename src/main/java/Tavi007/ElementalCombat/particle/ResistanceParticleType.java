package Tavi007.ElementalCombat.particle;

import com.mojang.serialization.Codec;

import net.minecraft.particles.ParticleType;

public class ResistanceParticleType extends ParticleType<ResistanceParticleData> {

	private static boolean ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER = false;

	public ResistanceParticleType() {
		super(ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER, ResistanceParticleData.DESERIALIZER);
	}

	// was missing from the example? maybe a 1.16 difference?
	@Override
	public Codec<ResistanceParticleData> func_230522_e_() {
		// TODO Auto-generated method stub
		return null;
	}

}