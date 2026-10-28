package Tavi007.ElementalCombat.common.network.packages;

import Tavi007.ElementalCombat.client.registry.ModParticles;
import Tavi007.ElementalCombat.common.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record CreateEmitterPacket(int entityId, String particleName, int amount) implements IPacket {

    public static CreateEmitterPacket read(FriendlyByteBuf buf) {
        return new CreateEmitterPacket(
                buf.readInt(),
                buf.readUtf(),
                buf.readInt()
        );
    }

    public static void write(FriendlyByteBuf buf, CreateEmitterPacket packet) {
        buf.writeInt(packet.entityId);
        buf.writeUtf(packet.particleName);
        buf.writeInt(packet.amount);
    }

    public void encode(FriendlyByteBuf buf) {
        write(buf, this);
    }

    public boolean isValid() {
        return particleName != null;
    }

    @Override
    public void processPacket(Optional<Level> level) {
        if (level.isEmpty()) {
            Constants.LOG.warn("Sender without level encountered. Skip CreateEmitterPacket.");
            return;
        }

        if (amount == 0) {
            return; // Nothing to do
        }
        
        Entity entity = level.get().getEntity(entityId);
        if (entity == null) {
            return;
        }

        SimpleParticleType particle = ParticleTypes.CRIT;
        switch (particleName) {
            case Constants.CRIT_ELEMENT:
                particle = ModParticles.CRIT_ELEMENT;
                break;
            case Constants.RESIST_ELEMENT:
                particle = ModParticles.RESIST_ELEMENT;
                break;
            case Constants.ABSORB:
                particle = ModParticles.ABSORB;
                break;
            case Constants.CRIT_STYLE:
                particle = ModParticles.CRIT_STYLE;
                break;
            case Constants.RESIST_STYLE:
                particle = ModParticles.RESIST_STYLE;
                break;
            default:
                Constants.LOG.warn("Unknown particle Name " + particleName + " encountered. Skip emitting.");
                return;
        }

        ParticleEngine engine = Minecraft.getInstance().particleEngine;
        for (int i = 0; i < amount; i++) {
            double vy = Math.random() - 0.75;
            double vx = Math.sin(Math.random() * 2 * Math.PI) * 0.5;
            double vz = Math.cos(Math.random() * 2 * Math.PI) * 0.5;

            engine.createParticle(particle,
                    entity.getX(),
                    entity.getY() + entity.getEyeHeight(),
                    entity.getZ(),
                    vx,
                    vy,
                    vz);
        }
    }

}
