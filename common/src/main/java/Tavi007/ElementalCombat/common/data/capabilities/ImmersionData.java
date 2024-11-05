package Tavi007.ElementalCombat.common.data.capabilities;

import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;

public class ImmersionData {

    public boolean disableFlag; // used to disable sound an rendering
    private int hurtTime; // used to temporarily save the hurt time of an entity

    public ImmersionData() {
        hurtTime = 0;
        disableFlag = false;
    }

    public ImmersionData(int hurtTime, boolean disableFlag) {
        this.hurtTime = hurtTime;
        this.disableFlag = disableFlag;
    }

    public int getHurtTime() {
        return hurtTime;
    }

    public void setHurtTime(int hurtTime) {
        if (hurtTime < 0) {
            this.hurtTime = 0;
        } else {
            this.hurtTime = hurtTime;
        }
    }

    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("hurt_time", IntTag.valueOf(getHurtTime()));
        nbt.put("disable_flag", ByteTag.valueOf(disableFlag));
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        IntTag timeNBT = (IntTag) nbt.get("hurt_time");
        ByteTag redNBT = (ByteTag) nbt.get("disable_flag");

        setHurtTime(timeNBT.getAsInt());
        disableFlag = redNBT.equals(ByteTag.ONE);
    }
}
