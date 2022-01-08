package Tavi007.ElementalCombat.network;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.network.PacketBuffer;

public class CreateEmitterMessage extends MessageToClient {

    private int entityId;
    private String particleName;
    private int amount;

    private CreateEmitterMessage() {
        super();
        entityId = 0;
        particleName = "";
        amount = 1;
    };

    public CreateEmitterMessage(int entityId, String particleName, int amount) {
        this.entityId = entityId;
        this.particleName = particleName;
        this.amount = amount;
        messageIsValid = true;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int id) {
        entityId = id;
    }

    public String getParticleName() {
        return particleName;
    }

    public void setParticleName(String name) {
        particleName = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static CreateEmitterMessage decode(PacketBuffer buf) {
        CreateEmitterMessage ret = new CreateEmitterMessage();
        try {
            ret.setEntityId(buf.readInt());
            ret.setParticleName(buf.readString());
            ret.setAmount(buf.readInt());

        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            ElementalCombat.LOGGER.warn("Exception while reading CreateEmitterMessage: " + e);
            return ret;
        }
        ret.messageIsValid = true;
        return ret;
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(entityId);
        buf.writeString(particleName);
        buf.writeInt(amount);
    }

}
