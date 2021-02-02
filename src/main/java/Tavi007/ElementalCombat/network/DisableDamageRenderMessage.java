package Tavi007.ElementalCombat.network;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.network.PacketBuffer;

public class DisableDamageRenderMessage {

	private boolean messageIsValid;
	private int id;
	
	public DisableDamageRenderMessage(int id) {
		this.messageIsValid = true;
		this.id = id;
	}
	
	public DisableDamageRenderMessage(DisableDamageRenderMessage message) {
		this.messageIsValid = message.isMessageValid();
		this.id = message.getId();
	}


	// for use by the message handler only.
	public DisableDamageRenderMessage(){
		this.messageIsValid = false;
		this.id = 0;
	}
	
	public boolean isMessageValid() {return this.messageIsValid;}
	public int getId() {return this.id;}
	private void setId(int id) {this.id = id;}
	
	public static DisableDamageRenderMessage decode(PacketBuffer buf) {
		DisableDamageRenderMessage retval = new DisableDamageRenderMessage();
		try {
			retval.setId(buf.readInt());
			
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			ElementalCombat.LOGGER.warn("Exception while reading DefenseDataMessageToClient: " + e);
			return retval;
		}
		retval.messageIsValid = true;
		return retval;
	}

	public void encode(PacketBuffer buf) {
		if (!this.messageIsValid) return;
		buf.writeInt(this.id);
	}

	
}
