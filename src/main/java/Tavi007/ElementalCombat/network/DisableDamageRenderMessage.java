package Tavi007.ElementalCombat.network;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.network.PacketBuffer;

public class DisableDamageRenderMessage extends MessageToClient {

	private int id;
	
	private DisableDamageRenderMessage(){
		super();
		this.id = 0;
	}
	
	public DisableDamageRenderMessage(int id) {
		this.messageIsValid = true;
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	public static DisableDamageRenderMessage decode(PacketBuffer buf) {
		DisableDamageRenderMessage retval = new DisableDamageRenderMessage();
		try {
			retval.setId(buf.readInt());
			
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			ElementalCombat.LOGGER.warn("Exception while reading DisableDamageRenderMessage: " + e);
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
