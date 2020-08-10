package Tavi007.ElementalCombat.particle;

import com.mojang.serialization.Codec;

import net.minecraft.particles.ParticleType;

public class AbsorbParticleType extends ParticleType<AbsorbParticleData> {

	private static boolean ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER = false;

	public AbsorbParticleType() {
		super(ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER, AbsorbParticleData.DESERIALIZER);
	}

	@Override
	public Codec<AbsorbParticleData> func_230522_e_() {
		// TODO Auto-generated method stub
		return null;
	}

}