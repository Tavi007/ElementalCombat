package Tavi007.ElementalCombat.common.network;

import Tavi007.ElementalCombat.client.registry.ModParticles;
import Tavi007.ElementalCombat.common.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class CreateEmitterPacket extends AbstractPacket {

    private final Integer entityId;
    private final String particleName;
    private final Integer amount;

    public CreateEmitterPacket(Integer entityId, String particleName, Integer amount) {
        this.entityId = entityId;
        this.particleName = particleName;
        this.amount = amount;
    }

    public CreateEmitterPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.particleName = buf.readUtf();
        this.amount = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeUtf(particleName);
        buf.writeInt(amount);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public String getParticleName() {
        return this.particleName;
    }

    public int getAmount() {
        return this.amount;
    }

    public boolean isValid() {
        return entityId != null && particleName != null && amount != null;
    }

//    @Override
//    public void handle(Context context) {
//        LogicalSide sideReceived = context.getDirection().getReceptionSide();
//        context.enqueueWork(() -> {
//            if (!isValid()) {
//                return;
//            }
//
//            Optional<Level> level = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
//            if (!level.isPresent()) {
//                ElementalCombat.LOGGER.warn(" context could not provide a ClientLevel.");
//                return;
//            }
//            processMessage(level.get());
//            context.setPacketHandled(true);
//        });
//    }


    public void processPacket(Level level) {
        if (!isValid()) {
            Constants.LOG.warn("Invalid CreateEmitterPacket encountered. Skip emitting.");
            return;
        }
        if (amount == 0) {
            return;
        }

        ParticleEngine engine = Minecraft.getInstance().particleEngine;

        Entity entity = level.getEntity(entityId);
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
