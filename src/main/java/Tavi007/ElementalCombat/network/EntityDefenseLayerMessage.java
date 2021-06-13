package Tavi007.ElementalCombat.network;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.defense.DefenseLayer;
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
			retval.defenseLayerToSend.addElement(readMap(buf));
			retval.defenseLayerToSend.addStyle(readMap(buf));
			
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
		writeMap(buf, defenseLayerToSend.getElementFactor());
		writeMap(buf, defenseLayerToSend.getStyleFactor());
	}

	private void writeMap(PacketBuffer buf, HashMap<String,Integer> map) {
		buf.writeInt(map.size());
		map.forEach((key, value) -> {
			buf.writeInt(value);
			buf.writeString(key);
		});
	
	}
	
	private static HashMap<String, Integer> readMap(PacketBuffer buf) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int size = buf.readInt();
		for (int i=0; i<size; i++) {
			int value = buf.readInt();
			String key = buf.readString();
			map.put(key, value);
		}
		return map;
	}
}
