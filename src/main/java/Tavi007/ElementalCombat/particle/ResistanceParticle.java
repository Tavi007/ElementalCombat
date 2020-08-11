package Tavi007.ElementalCombat.particle;

import java.awt.Color;

import javax.annotation.Nullable;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;

public class ResistanceParticle extends SpriteTexturedParticle{

	private final IAnimatedSprite sprites;  

	public ResistanceParticle(ClientWorld world, double x, double y, double z,
			double velocityX, double velocityY, double velocityZ,
			Color tint, double diameter,
			IAnimatedSprite sprites)
	{
		super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.sprites = sprites;

		setColor(tint.getRed()/255.0F, tint.getGreen()/255.0F, tint.getBlue()/255.0F);
		setSize((float)diameter, (float)diameter);    // the size (width, height) of the collision box.

		final float PARTICLE_SCALE_FOR_ONE_METRE = 0.5F; //  if the particleScale is 0.5, the texture will be rendered as 1 metre high
		particleScale = PARTICLE_SCALE_FOR_ONE_METRE * (float)diameter; // sets the rendering size of the particle for a TexturedParticle.

		maxAge = 10;  // lifetime in ticks: 100 ticks = 5 seconds

		final float ALPHA_VALUE = 1.0F;
		this.particleAlpha = ALPHA_VALUE;

		//the vanilla Particle constructor added random variation to our starting velocity.  Undo it!
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;

		this.canCollide = false;  // the move() method will check for collisions with scenery
	}

	// Choose the appropriate render type for your particles:
	// There are several useful predefined types:
	// PARTICLE_SHEET_TRANSLUCENT semi-transparent (translucent) particles
	// PARTICLE_SHEET_OPAQUE    opaque particles
	// TERRAIN_SHEET            particles drawn from block or item textures
	// PARTICLE_SHEET_LIT       appears to be the same as OPAQUE.  Not sure of the difference.  In previous versions of minecraft,
	//                          "lit" particles changed brightness depending on world lighting i.e. block light + sky light
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	/**
	 * call once per tick to update the Particle position, calculate collisions, remove when max lifetime is reached, etc
	 */
	@Override
	public void tick()
	{
		// if you want to change the texture as the particle gets older, you can use
		// selectSpriteWithAge(sprites);

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		move(motionX, motionY, motionZ);  // simple linear motion.  You can change speed by changing motionX, motionY,
		// motionZ every tick.  For example - you can make the particle accelerate downwards due to gravity by
		// final double GRAVITY_ACCELERATION_PER_TICK = -0.02;
		// motionY += GRAVITY_ACCELERATION_PER_TICK;
		// calling move() also calculates collisions with other objects

		// collision with a block makes the ball disappear.  But does not collide with entities
		if (onGround) {  // onGround is only true if the particle collides while it is moving downwards...
			this.setExpired();
		}

		if (prevPosY == posY && motionY > 0) {  // detect a collision while moving upwards (can't move up at all)
			this.setExpired();
		}

		if (this.age++ >= this.maxAge) {
			this.setExpired();
		}
	}
	
	
	public static class Factory implements IParticleFactory<ResistanceParticleData> {  //IParticleFactory

		private final IAnimatedSprite sprites;

		public Factory(IAnimatedSprite sprite) {
			this.sprites = sprite;
		}
		
		private Factory() {
			throw new UnsupportedOperationException("Use the CombatParticleFactory(IAnimatedSprite sprite) constructor");
		}

		@Nullable
		//@Override
		public Particle makeParticle(ResistanceParticleData combatParticleData, ClientWorld world, double xPos, double yPos, double zPos, double xVelocity, double yVelocity, double zVelocity) {
			ResistanceParticle newParticle = new ResistanceParticle(world, xPos, yPos, zPos, xVelocity, yVelocity, zVelocity,
					combatParticleData.getTint(), combatParticleData.getDiameter(),
					sprites);
			newParticle.selectSpriteRandomly(sprites);  // choose a random sprite from the available list (in this case there is only one)
			return newParticle;
		}

	}

}