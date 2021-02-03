package Tavi007.ElementalCombat.capabilities.immersion;

public class ImmersionData {
	public boolean disableFlag; //used to disable sound an rendering
	private int hurtTime; //used to temporarily save the hurt time of an entity
	
	public ImmersionData() {
		hurtTime = 0;
		disableFlag = false;
	}
	public ImmersionData(int hurtTime, boolean disableFlag) {
		this.hurtTime = hurtTime;
		this.disableFlag = disableFlag;
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
