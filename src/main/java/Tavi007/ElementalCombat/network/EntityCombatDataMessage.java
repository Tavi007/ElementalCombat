package Tavi007.ElementalCombat.network;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import net.minecraft.network.PacketBuffer;

public class EntityCombatDataMessage extends MessageToClient {

	private int id;
	private AttackData attackToSend;
	private DefenseData defenseToSend;
	private boolean isAdd;
	
	public EntityCombatDataMessage(AttackData attackToSend, DefenseData defenseToSend, boolean isAdd, int id) {
		this.messageIsValid = true;
		this.attackToSend = attackToSend;
		this.defenseToSend = defenseToSend;
		this.isAdd = isAdd;
		this.id = id;
	}

	// for use by the message handler only.
	public EntityCombatDataMessage(){
		super();
		this.id = 0;
		this.attackToSend = new AttackData();
		this.defenseToSend = new DefenseData();
		this.isAdd = false;
	}

	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public DefenseData getDefenseData() {
		return defenseToSend;
	}
	
	public AttackData getAttackData() {
		return attackToSend;
	}
	
	public boolean isAdd() {
		return this.isAdd;
	}
	
	public void setIsAdd(boolean isSet) {
		this.isAdd = isSet;
	}
	
	public void setValid(boolean messageIsValid) {
		this.messageIsValid = messageIsValid;
	}
	
	public static EntityCombatDataMessage decode(PacketBuffer buf)
	{
		EntityCombatDataMessage retval = new EntityCombatDataMessage();
		try {
			retval.setId(buf.readInt());
			
			//rest of the combat properties
			retval.getDefenseData().setElementFactor(readMap(buf));
			retval.getDefenseData().setStyleFactor(readMap(buf));
			retval.setIsAdd(buf.readBoolean());
			retval.getAttackData().setElement(buf.readString());
			retval.getAttackData().setStyle(buf.readString());
			
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			ElementalCombat.LOGGER.warn("Exception while reading EntityMessage: " + e);
			return retval;
		}
		retval.setValid(true);
		return retval;
	}

	public void encode(PacketBuffer buf)
	{
		if (!isMessageValid()) return;
		//get entity through id
		buf.writeInt(this.id);
		
		//write rest of the combat properties
		writeMap(buf, this.defenseToSend.getElementFactor());
		writeMap(buf, this.defenseToSend.getStyleFactor());
		buf.writeBoolean(this.isAdd);
		buf.writeString(this.attackToSend.getElement());
		buf.writeString(this.attackToSend.getStyle());
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
