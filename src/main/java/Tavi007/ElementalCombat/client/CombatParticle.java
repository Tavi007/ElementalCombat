package Tavi007.ElementalCombat.client;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CombatParticle extends SpriteTexturedParticle {

    private CombatParticle(ClientWorld world, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.xd *= (double) 0.1F;
        this.yd *= (double) 0.1F;
        this.zd *= (double) 0.1F;
        this.xd += xd * 0.4D;
        this.yd += yd * 0.4D;
        this.zd += zd * 0.4D;
        this.quadSize *= 0.75F;
        this.lifetime = Math.max((int) (6.0D / (Math.random() * 0.8D + 0.6D)), 1);
        this.hasPhysics = false;
        this.tick();
    }

    public float getScale(float scaleFactor) {
        return this.quadSize * MathHelper.clamp(((float) this.age + scaleFactor) / (float) this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);
            this.xd *= (double) 0.7F;
            this.yd *= (double) 0.7F;
            this.zd *= (double) 0.7F;
            this.yd -= (double) 0.02F;
            if (this.onGround) {
                this.xd *= (double) 0.7F;
                this.zd *= (double) 0.7F;
            }
        }
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {

        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed,
                double zSpeed) {
            CombatParticle particle = new CombatParticle(worldIn, x, y, z, xSpeed, ySpeed + 1.0D, zSpeed);
            particle.setLifetime(15);
            particle.setSpriteFromAge(this.spriteSet);
            return particle;
        }
    }
}
