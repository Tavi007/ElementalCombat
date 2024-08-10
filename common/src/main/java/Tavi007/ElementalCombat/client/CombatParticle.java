package Tavi007.ElementalCombat.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class CombatParticle extends TextureSheetParticle {

    private CombatParticle(ClientLevel world, double x, double y, double z, double xd, double yd, double zd) {
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
        return this.quadSize * Mth.clamp(((float) this.age + scaleFactor) / (float) this.lifetime * 32.0F, 0.0F, 1.0F);
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

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed,
                                       double zSpeed) {
            CombatParticle particle = new CombatParticle(worldIn, x, y, z, xSpeed, ySpeed + 1.0D, zSpeed);
            particle.setLifetime(15);
            particle.setSpriteFromAge(this.spriteSet);
            return particle;
        }
    }
}
