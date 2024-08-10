package Tavi007.ElementalCombat.common.network.clientbound;

import Tavi007.ElementalCombat.common.ElementalCombat;
import Tavi007.ElementalCombat.common.init.ParticleList;
import Tavi007.ElementalCombat.common.network.Packet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.Optional;

public class CreateEmitterPacket extends Packet {

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

    @Override
    public void handle(Context context) {
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.enqueueWork(() -> {
            if (!isValid()) {
                return;
            }

            Optional<Level> level = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
            if (!level.isPresent()) {
                ElementalCombat.LOGGER.warn(" context could not provide a ClientLevel.");
                return;
            }
            processMessage(level.get());
            context.setPacketHandled(true);
        });
    }

    private boolean isValid() {
        return entityId != null && particleName != null && amount != null;
    }

    private void processMessage(Level level) {
        ParticleEngine engine = Minecraft.getInstance().particleEngine;

        Entity entity = level.getEntity(entityId);
        SimpleParticleType particle = ParticleTypes.CRIT;
        switch (particleName) {
            case "critical_element":
                particle = ParticleList.CRIT_ELEMENT.get();
                break;
            case "resistent_element":
                particle = ParticleList.RESIST_ELEMENT.get();
                break;
            case "absorb":
                particle = ParticleList.ABSORB.get();
                break;
            case "critical_style":
                particle = ParticleList.CRIT_STYLE.get();
                break;
            case "resistent_style":
                particle = ParticleList.RESIST_STYLE.get();
                break;
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
