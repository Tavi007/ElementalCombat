package Tavi007.ElementalCombat.network;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class EntityAttackLayerMessage extends MessageToClient {

    private int id;
    private String location;
    private AttackLayer attackLayer;

    public EntityAttackLayerMessage(AttackLayer attackLayer, ResourceLocation location, int id) {
        this.messageIsValid = true;
        this.attackLayer = attackLayer;
        this.location = location.toString();
        this.id = id;
    }

    // for use by the message handler only.
    public EntityAttackLayerMessage() {
        super();
        id = 0;
        location = "";
        attackLayer = new AttackLayer();
    }

    public AttackLayer getAttackLayer() {
        return attackLayer;
    }

    public String getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }

    public static EntityAttackLayerMessage decode(FriendlyByteBuf buf) {
        EntityAttackLayerMessage retval = new EntityAttackLayerMessage();
        try {
            retval.id = buf.readInt();
            retval.location = buf.readUtf();

            // rest of the combat properties
            retval.attackLayer.setElement(buf.readUtf());
            retval.attackLayer.setStyle(buf.readUtf());

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
        // get entity through id
        buf.writeInt(id);
        buf.writeUtf(location);
        // write rest of the combat properties
        buf.writeUtf(attackLayer.getElement());
        buf.writeUtf(attackLayer.getStyle());
    }
}
