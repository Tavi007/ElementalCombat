package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

import net.minecraft.network.FriendlyByteBuf;

public class MobCombatProperties extends ItemCombatProperties {

    @SerializedName("biome_dependency")
    private boolean biomeDependency;

    public MobCombatProperties(HashMap<String, Integer> defenseStyle, HashMap<String, Integer> defenseElement, String attackStyle, String attackElement,
            boolean biomeDependency) {
        super(defenseStyle, defenseElement, attackStyle, attackElement);
        this.biomeDependency = biomeDependency;
    }

    public MobCombatProperties() {
        super();
        this.biomeDependency = false;
    }

    public MobCombatProperties(MobCombatProperties entityData) {
        super(entityData.getDefenseStyle(), entityData.getDefenseElement(), entityData.getAttackStyle(), entityData.getAttackElement());
        this.biomeDependency = entityData.biomeDependency;
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
