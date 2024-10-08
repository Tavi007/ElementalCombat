package Tavi007.ElementalCombat.client.network;

import Tavi007.ElementalCombat.client.registry.ModParticles;
import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.network.CreateEmitterPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ClientPacketHandler {


    private void handlePacket(CreateEmitterPacket packet, Level level) {
        if (!packet.isValid()) {
            Constants.LOG.warn("Invalid " + packet.getClass().getName() + " encountered. Skip emitting.");
            return;
        }
        if (packet.getAmount() == 0) {
            return;
        }

        ParticleEngine engine = Minecraft.getInstance().particleEngine;

        Entity entity = level.getEntity(packet.getEntityId());
        SimpleParticleType particle = ParticleTypes.CRIT;
        switch (packet.getParticleName()) {
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
                Constants.LOG.warn("Unknown particle Name " + packet.getParticleName() + " encountered. Skip emitting.");
                return;
        }

        for (int i = 0; i < packet.getAmount(); i++) {
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
