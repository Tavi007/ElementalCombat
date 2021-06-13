package Tavi007.ElementalCombat.network;

public class MessageToClient {

	protected boolean messageIsValid;
	
	public MessageToClient( ) {
		messageIsValid = false;
	}
	
	public boolean isMessageValid() {
		return messageIsValid;
	}
	
	public void verify() {
		messageIsValid = true;
	}
}
