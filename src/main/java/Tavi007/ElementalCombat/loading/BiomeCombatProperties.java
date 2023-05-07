package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import com.google.common.base.Optional;
import com.google.gson.annotations.SerializedName;

import Tavi007.ElementalCombat.util.PacketBufferHelper;
import net.minecraft.network.FriendlyByteBuf;

public class BiomeCombatProperties {

    @SerializedName("defense_element")
    private HashMap<String, Integer> defenseElement;

    public BiomeCombatProperties() {
        defenseElement = new HashMap<String, Integer>();
    }

    public BiomeCombatProperties(BiomeCombatProperties biomeData) {
        defenseElement = biomeData.getDefenseElement();
    }

    public HashMap<String, Integer> getDefenseElement() {
        return Optional.fromNullable(defenseElement).or(new HashMap<>());
    }

    public void writeToBuffer(FriendlyByteBuf buf) {
        PacketBufferHelper.writeHashMap(buf, getDefenseElement());
    }

    public void readFromBuffer(FriendlyByteBuf buf) {
        defenseElement = PacketBufferHelper.readHashMap(buf);
    }

    @Override
    public String toString() {
        return "\nDefElement=" + getDefenseElement().toString();
    }

}
