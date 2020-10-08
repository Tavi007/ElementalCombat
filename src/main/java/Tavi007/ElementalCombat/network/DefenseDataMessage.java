package Tavi007.ElementalCombat.network;

import java.util.HashMap;
import java.util.UUID;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraft.network.PacketBuffer;

public class DefenseDataMessage {

	private boolean messageIsValid;
	private DefenseData dataToSend;
	private UUID uuid;
	private boolean isAdd;

	public DefenseDataMessage(DefenseData dataToSend, UUID uuid, boolean isAdd){
		this.dataToSend = dataToSend;
		this.messageIsValid = true;
		this.uuid = uuid;
		this.isAdd = isAdd;
	}

	// for use by the message handler only.
	public DefenseDataMessage(){
		this.dataToSend = new DefenseData();
		this.messageIsValid = false;
		this.uuid = new UUID(0,0);
		this.isAdd = true;
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
	
	public boolean isAdd() {
		return this.isAdd;
	}
	
	public void setIsAdd(boolean isSet) {
		this.isAdd = isSet;
	}
	
	public void setUUID(long most, long least) {
		this.uuid = new UUID(most, least);
	}
	
	public static DefenseDataMessage decode(PacketBuffer buf)
	{
		DefenseDataMessage retval = new DefenseDataMessage();
		try {
			
			//define uuid through 2 longs.
			retval.setUUID(buf.readLong(), buf.readLong());
			retval.getDefenseData().setElementFactor(readMap(buf));
			retval.getDefenseData().setStyleFactor(readMap(buf));
			retval.setIsAdd(buf.readBoolean());
			
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			ElementalCombat.LOGGER.warn("Exception while reading DefenseDataMessageToClient: " + e);
			return retval;
		}
		retval.messageIsValid = true;
		return retval;
	}

	public void encode(PacketBuffer buf)
	{
		if (!this.messageIsValid) return;
		buf.writeLong(this.uuid.getMostSignificantBits());
		buf.writeLong(this.uuid.getLeastSignificantBits());
		writeMap(buf, this.dataToSend.getElementFactor());
		writeMap(buf, this.dataToSend.getStyleFactor());
		buf.writeBoolean(this.isAdd);
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
		return "DefenseDataMessage[DefenseData.elementFactor=" + this.dataToSend.getElementFactor().toString() + "; " + "DefenseData.styleFactor=" + this.dataToSend.getStyleFactor().toString() + "]";
	}
}
