package Tavi007.ElementalCombat.network;

import java.util.HashMap;
import java.util.UUID;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraft.network.PacketBuffer;

public class DefenseDataMessageToClient {

	private boolean messageIsValid;
	private DefenseData dataToSend;
	private UUID uuid;

	public DefenseDataMessageToClient(DefenseData dataToSend, UUID uuid){
		this.dataToSend = dataToSend;
		this.messageIsValid = true;
		this.uuid = uuid;
	}

	// for use by the message handler only.
	public DefenseDataMessageToClient(){
		dataToSend = new DefenseData();
		messageIsValid = false;
		uuid = new UUID(0,0);
	}

	public DefenseData getDefenseData() {
		return dataToSend;
	}

	public boolean isMessageValid() {
		return messageIsValid;
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public void setUUID(long most, long least) {
		this.uuid = new UUID(most, least);
	}
	
	public static DefenseDataMessageToClient decode(PacketBuffer buf)
	{
		DefenseDataMessageToClient retval = new DefenseDataMessageToClient();
		try {
			
			//define uuid through 2 longs.
			retval.setUUID(buf.readLong(), buf.readLong());
			retval.getDefenseData().setElementFactor(readMap(buf));
			retval.getDefenseData().setStyleFactor(readMap(buf));
			
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			ElementalCombat.LOGGER.warn("Exception while reading DefenseDataMessageToClient: " + e);
			return retval;
		}
		retval.messageIsValid = true;
		return retval;
	}

	public void encode(PacketBuffer buf)
	{
		if (!messageIsValid) return;
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
		writeMap(buf, dataToSend.getElementFactor());
		writeMap(buf, dataToSend.getStyleFactor());
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
	
	@Override
	public String toString()
	{
		return "DefenseDataMessageToClient[DefenseData.elementFactor=" + dataToSend.getElementFactor().toString() + "; " + "DefenseData.styleFactor=" + dataToSend.getStyleFactor().toString() + "]";
	}
}
