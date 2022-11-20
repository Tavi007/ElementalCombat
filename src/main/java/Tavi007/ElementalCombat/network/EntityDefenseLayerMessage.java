package Tavi007.ElementalCombat.network;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.util.PacketBufferHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class EntityDefenseLayerMessage extends MessageToClient {

    private int id;
    private String location;
    private DefenseLayer defenseLayerToSend;

    public EntityDefenseLayerMessage(DefenseLayer defenseLayerToSend, ResourceLocation location, int id) {
        this.messageIsValid = true;
        this.defenseLayerToSend = defenseLayerToSend;
        this.location = location.toString();
        this.id = id;
    }

    // for use by the message handler only.
    public EntityDefenseLayerMessage() {
        super();
        id = 0;
        location = "";
        defenseLayerToSend = new DefenseLayer();
    }

    public DefenseLayer getDefenseLayer() {
        return defenseLayerToSend;
    }

    public int getId() {
        return id;
    }

    public ResourceLocation getLocation() {
        return new ResourceLocation(location);
    }

    public static EntityDefenseLayerMessage decode(FriendlyByteBuf buf) {
        EntityDefenseLayerMessage retval = new EntityDefenseLayerMessage();
        try {
            retval.id = buf.readInt();
            retval.location = buf.readUtf();
            retval.defenseLayerToSend.addElement(PacketBufferHelper.readHashMap(buf));
            retval.defenseLayerToSend.addStyle(PacketBufferHelper.readHashMap(buf));

        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            ElementalCombat.LOGGER.warn("Exception while reading EntityMessage: " + e);
            return retval;
        }
        retval.verify();
        return retval;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!isMessageValid())
            return;
        buf.writeInt(id);
        buf.writeUtf(location);
        PacketBufferHelper.writeHashMap(buf, defenseLayerToSend.getElementFactor());
        PacketBufferHelper.writeHashMap(buf, defenseLayerToSend.getStyleFactor());
    }
}
