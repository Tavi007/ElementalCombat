package Tavi007.ElementalCombat.network;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraft.network.PacketBuffer;

public class CombatDataMessage {

	private boolean messageIsValid;
	private DefenseData defToSend;
	private AttackData atckToSend;
	private boolean isAdd;
	
	public CombatDataMessage(DefenseData defToSend, AttackData atckToSend, boolean isAdd) {
		this.defToSend = defToSend;
		this.atckToSend = atckToSend;
		this.isAdd = isAdd;
		this.messageIsValid = true;
	}
	
	public CombatDataMessage(CombatDataMessage message) {
		this.defToSend = message.getDefenseData();
		this.atckToSend = message.getAttackData();
		this.isAdd = message.isAdd();
		this.messageIsValid = message.isMessageValid();
	}
	
	// for use by the message handler only.
	public CombatDataMessage(){
		this.defToSend = new DefenseData();
		this.atckToSend = new AttackData();
		this.isAdd = true;
		this.messageIsValid = false;
	}

	public static CombatDataMessage read(PacketBuffer buf)
	{
		CombatDataMessage retval = new CombatDataMessage();
		try {
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

	public void write(PacketBuffer buf)
	{
		if (!this.messageIsValid) return;
		writeMap(buf, this.defToSend.getElementFactor());
		writeMap(buf, this.defToSend.getStyleFactor());
		buf.writeBoolean(this.isAdd);
	}

	public DefenseData getDefenseData() {
		return defToSend;
	}
	
	public AttackData getAttackData() {
		return atckToSend;
	}

	public boolean isMessageValid() {
		return messageIsValid;
	}

	public void setValid(boolean messageIsValid) {
		this.messageIsValid = messageIsValid;
	}
	
	public boolean isAdd() {
		return this.isAdd;
	}
	
	public void setIsAdd(boolean isSet) {
		this.isAdd = isSet;
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
		return "Defense: Element=" + this.defToSend.getElementFactor().toString() + "; " + "Style=" + this.defToSend.getStyleFactor().toString() + "\n" + 
			   "Attack: Element=" + this.atckToSend.getElement() + "; " + "Style=" + this.atckToSend.getStyle();
	}
}
