package Tavi007.ElementalCombat.network;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.attack.AttackData;
import net.minecraft.network.PacketBuffer;

public class EntityAttackDataMessage extends MessageToClient {

	private int id;
	private AttackData attackToSend;
	
	public EntityAttackDataMessage(AttackData attackToSend, int id) {
		this.messageIsValid = true;
		this.attackToSend = attackToSend;
		this.id = id;
	}

	// for use by the message handler only.
	public EntityAttackDataMessage(){
		super();
		id = 0;
		attackToSend = new AttackData();
	}
	
	public AttackData getAttackData() {
		return attackToSend;
	}

	public int getId() {
		return id;
	}
	
	public static EntityAttackDataMessage decode(PacketBuffer buf)
	{
		EntityAttackDataMessage retval = new EntityAttackDataMessage();
		try {
			retval.id =  buf.readInt();
			
			//rest of the combat properties
			retval.attackToSend.setElement(buf.readString());
			retval.attackToSend.setStyle(buf.readString());
			
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
		//get entity through id
		buf.writeInt(id);
		//write rest of the combat properties
		buf.writeString(attackToSend.getElement());
		buf.writeString(attackToSend.getStyle());
	}
}
