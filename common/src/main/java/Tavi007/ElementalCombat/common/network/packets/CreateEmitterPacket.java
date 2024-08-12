package Tavi007.ElementalCombat.common.network.packets;

import net.minecraft.network.FriendlyByteBuf;

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


}
