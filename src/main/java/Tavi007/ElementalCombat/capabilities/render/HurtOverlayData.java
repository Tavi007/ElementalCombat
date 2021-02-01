package Tavi007.ElementalCombat.capabilities.render;

public class HurtOverlayData {
	private int hurtTime;
	public boolean disableRedOverlay;
	
	public HurtOverlayData() {
		hurtTime = 0;
		disableRedOverlay = false;
	}
	public HurtOverlayData(int hurtTime, boolean disableRedOverlay) {
		this.hurtTime = hurtTime;
		this.disableRedOverlay = disableRedOverlay;
	}
	
	public void setHurtTime(int hurtTime) {
		if(hurtTime<0) {
			this.hurtTime = 0;
		}
		else {
			this.hurtTime = hurtTime;
		}
	}
	
	public int getHurtTime() {
		return hurtTime;
	}
}
