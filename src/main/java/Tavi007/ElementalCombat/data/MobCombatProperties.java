package Tavi007.ElementalCombat.data;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

import net.minecraft.network.PacketBuffer;

public class MobCombatProperties extends ElementalCombatProperties {

    @SerializedName("biome_dependency")
    private boolean biomeDependency;

    public MobCombatProperties(HashMap<String, Integer> defenseStyle, HashMap<String, Integer> defenseElement, String attackStyle, String attackElement,
            boolean biomeDependency) {
        super(defenseStyle, defenseElement, attackStyle, attackElement);
        this.biomeDependency = biomeDependency;
    }

    public MobCombatProperties() {
        super();
        biomeDependency = false;
    }

    public boolean getBiomeDependency() {
        return biomeDependency;
    }

    @Override
    public void writeToBuffer(PacketBuffer buf) {
        super.writeToBuffer(buf);
        buf.writeBoolean(biomeDependency);
    }

    @Override
    public void readFromBuffer(PacketBuffer buf) {
        super.readFromBuffer(buf);
        biomeDependency = buf.readBoolean();
    }

    @Override
    public String toString() {
        return super.toString() + "\nBiomeDepend=" + biomeDependency;
    }

}
