package Tavi007.ElementalCombat.network;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraft.network.PacketBuffer;

public class EntityMessage extends CombatDataMessage {

	private int id;
	
	public EntityMessage(AttackData atckToSend, DefenseData defToSend, boolean isAdd, int id) {
		super(atckToSend, defToSend, isAdd);
		this.id = id;
	}

	// for use by the message handler only.
	public EntityMessage(){
		super();
		this.id = 0;
	}

	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public static EntityMessage decode(PacketBuffer buf)
	{
		EntityMessage retval = new EntityMessage();
		try {
			retval.setId(buf.readInt());
			
			//rest of the combat properties
			CombatDataMessage combatMessage = readCombatDataFromPacket(buf);
			retval.getDefenseData().setElementFactor(combatMessage.getDefenseData().getElementFactor());
			retval.getDefenseData().setStyleFactor(combatMessage.getDefenseData().getStyleFactor());
			retval.setIsAdd(combatMessage.isAdd());
			
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
		writeCombatDataToPacket(buf);
	}
}
