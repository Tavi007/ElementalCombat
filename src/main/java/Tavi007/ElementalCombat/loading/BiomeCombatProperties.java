package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

import Tavi007.ElementalCombat.util.PacketBufferHelper;
import net.minecraft.network.PacketBuffer;

public class BiomeCombatProperties {

    @SerializedName("defense_element")
    private HashMap<String, Integer> defenseElement;

    public BiomeCombatProperties() {
        this.defenseElement = new HashMap<String, Integer>();
    }

    public BiomeCombatProperties(HashMap<String, Integer> defenseElement) {
        this.defenseElement = defenseElement;
    }

    public BiomeCombatProperties(BiomeCombatProperties biomeData) {
        this.defenseElement = biomeData.getDefenseElement();
    }

    public HashMap<String, Integer> getDefenseElement() {
        return this.defenseElement;
    }

    public void writeToBuffer(PacketBuffer buf) {
        PacketBufferHelper.writeHashMap(buf, defenseElement);
    }

    public void readFromBuffer(PacketBuffer buf) {
        defenseElement = PacketBufferHelper.readHashMap(buf);
    }

    @Override
    public String toString() {
        return "\nDefElement=" + defenseElement.toString();
    }

}
