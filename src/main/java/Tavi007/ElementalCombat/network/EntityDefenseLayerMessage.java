package Tavi007.ElementalCombat.network;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.util.PacketBufferHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

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
	public EntityDefenseLayerMessage(){
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
	
	public static EntityDefenseLayerMessage decode(PacketBuffer buf)
	{
		EntityDefenseLayerMessage retval = new EntityDefenseLayerMessage();
		try {
			retval.id =  buf.readInt();
			retval.location = buf.readString();
			retval.defenseLayerToSend.addElement(PacketBufferHelper.readStringToInt(buf));
			retval.defenseLayerToSend.addStyle(PacketBufferHelper.readStringToInt(buf));
			
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			ElementalCombat.LOGGER.warn("Exception while reading EntityMessage: " + e);
			return retval;
		}
		retval.verify();
		return retval;
	}

	public void encode(PacketBuffer buf)
	{
		if (!isMessageValid()) return;
		buf.writeInt(id);
		buf.writeString(location);
		PacketBufferHelper.writeStringToInt(buf, defenseLayerToSend.getElementFactor());
		PacketBufferHelper.writeStringToInt(buf, defenseLayerToSend.getStyleFactor());
	}
}
