package Tavi007.ElementalCombat.common.api.data;

import com.google.gson.annotations.SerializedName;
import net.minecraft.network.FriendlyByteBuf;

public class ElementalCombatMobData extends ElementalCombatLayer {

    @SerializedName("biome_dependency")
    private boolean biomeDependency;

    public ElementalCombatMobData(AttackLayer attack, DefenseLayer defense, boolean biomeDependency) {
        super(attack, defense);
        this.biomeDependency = biomeDependency;
    }

    public ElementalCombatMobData() {
        super();
        this.biomeDependency = false;
    }

    public boolean getBiomeDependency() {
        return this.biomeDependency;
    }

    @Override
    public void writeToBuffer(FriendlyByteBuf buf) {
        super.writeToBuffer(buf);
        buf.writeBoolean(biomeDependency);
    }

    @Override
    public void readFromBuffer(FriendlyByteBuf buf) {
        super.readFromBuffer(buf);
        biomeDependency = buf.readBoolean();
    }

    @Override
    public String toString() {
        return super.toString() + "\nBiomeDepend=" + biomeDependency;
    }

}
