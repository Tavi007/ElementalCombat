package Tavi007.ElementalCombat.particle;

import javax.annotation.Nullable;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;

public class ImmunityParticleFactory implements IParticleFactory<ImmunityParticleData> {  //IParticleFactory

	private final IAnimatedSprite sprites;  // contains a list of textures; choose one using either
	// newParticle.selectSpriteRandomly(sprites); or newParticle.selectSpriteWithAge(sprites);

	// this method is needed for proper registration of your Factory:
	// The ParticleManager.register method creates a Sprite and passes it to your factory for subsequent use when rendering, then
	//   populates it with the textures from your textures/particle/xxx.json

	public ImmunityParticleFactory(IAnimatedSprite sprite) {
		this.sprites = sprite;
	}

	// This is private to prevent you accidentally registering the Factory using the default constructor.
	// ParticleManager has two register methods, and if you use the wrong one the game will enter an infinite loop
	private ImmunityParticleFactory() {
		throw new UnsupportedOperationException("Use the CombatParticleFactory(IAnimatedSprite sprite) constructor");
	}

	@Nullable
	//@Override
	public Particle makeParticle(ImmunityParticleData combatParticleData, ClientWorld world, double xPos, double yPos, double zPos, double xVelocity, double yVelocity, double zVelocity) {
		ImmunityParticle newParticle = new ImmunityParticle(world, xPos, yPos, zPos, xVelocity, yVelocity, zVelocity,
				combatParticleData.getTint(), combatParticleData.getDiameter(),
				sprites);
		newParticle.selectSpriteRandomly(sprites);  // choose a random sprite from the available list (in this case there is only one)
		return newParticle;
	}

}
