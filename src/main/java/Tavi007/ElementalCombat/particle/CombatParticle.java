package Tavi007.ElementalCombat.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CombatParticle extends SpriteTexturedParticle{

	protected CombatParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		
		float f = this.rand.nextFloat() * 1.0f;
		this.particleRed = f;
		this.particleGreen = f;
		this.particleBlue = f;
		
		this.setSize(0.6f, 0.6f);
		this.motionX = xSpeedIn;
		this.motionY = Math.abs(ySpeedIn);
		this.motionZ = zSpeedIn;
		this.maxAge = 20; //ticks?
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}
	
	@Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		if(this.maxAge-- <= 0) {
			this.setExpired();
		} else {
			this.move(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.6D;
			this.motionY *= 0.6D;
			this.motionZ *= 0.6D;
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;
		
		public Factory(IAnimatedSprite sprite) {
			this.spriteSet = sprite;
		}
		
		
		@Override
		public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			CombatParticle combatParticle = new CombatParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
			combatParticle.setColor(1.0f, 1.0f, 1.0f);
			combatParticle.selectSpriteRandomly(this.spriteSet);
			return combatParticle;
		}
		
	}

}
