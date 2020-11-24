package Tavi007.ElementalCombat.network;

import java.util.UUID;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraft.network.PacketBuffer;

public class EntityMessage extends CombatDataMessage {

	private UUID uuid;
	
	public EntityMessage(DefenseData defToSend, AttackData atckToSend, boolean isAdd, UUID uuid) {
		super(defToSend, atckToSend, isAdd);
		this.uuid = uuid;
	}

	// for use by the message handler only.
	public EntityMessage(){
		super();
		this.uuid = new UUID(0,0);
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public void setUUID(long most, long least) {
		this.uuid = new UUID(most, least);
	}
	
	public static EntityMessage decode(PacketBuffer buf)
	{
		EntityMessage retval = new EntityMessage();
		try {
			
			//define uuid through 2 longs.
			retval.setUUID(buf.readLong(), buf.readLong());
			CombatDataMessage combatMessage = read(buf);
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
		buf.writeLong(this.uuid.getMostSignificantBits());
		buf.writeLong(this.uuid.getLeastSignificantBits());
		write(buf);
	}
}
