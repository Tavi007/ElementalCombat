package Tavi007.ElementalCombat.network;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraft.network.PacketBuffer;

public class ItemMessage extends CombatDataMessage {
	private int windowId;
	private int slotNumber;
	
	public ItemMessage(AttackData atckToSend, DefenseData defToSend, int windowId, int slotNumber) {
		super(atckToSend, defToSend, false);
		this.windowId = windowId;
		this.slotNumber = slotNumber;
	}

	// for use by the message handler only.
	public ItemMessage() {
		super();
		this.windowId = 0;
		this.slotNumber = 0;
	}
	
	public static ItemMessage decode(PacketBuffer buf)
	{
		ItemMessage retval = new ItemMessage();
		try {
			//for the container slot
			retval.windowId = buf.readInt();
			retval.slotNumber = buf.readInt();
			
			//rest of the combat properties
			CombatDataMessage combatMessage = readCombatDataFromPacket(buf);
			retval.getDefenseData().setElementFactor(combatMessage.getDefenseData().getElementFactor());
			retval.getDefenseData().setStyleFactor(combatMessage.getDefenseData().getStyleFactor());
			retval.setIsAdd(combatMessage.isAdd());
			
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			ElementalCombat.LOGGER.warn("Exception while reading ItemMessage: " + e);
			return retval;
		}
		retval.setValid(true);
		return retval;
	}

	public void encode(PacketBuffer buf)
	{
		if (!isMessageValid()) return;
		//define container slot
		buf.writeInt(windowId);
		buf.writeInt(slotNumber);
		
		//write rest of the combat properties
		writeCombatDataToPacket(buf);
	}
	
	public int getWindowId() {return this.windowId;}
	public int getSlotNumber() {return this.slotNumber;}
}
