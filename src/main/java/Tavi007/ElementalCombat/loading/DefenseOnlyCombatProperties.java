package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

import Tavi007.ElementalCombat.util.MapHelper;
import Tavi007.ElementalCombat.util.PacketBufferHelper;
import net.minecraft.network.FriendlyByteBuf;

public class DefenseOnlyCombatProperties {

    @SerializedName("defense_element")
    private HashMap<String, Integer> defenseElement;
    @SerializedName("defense_style")
    private HashMap<String, Integer> defenseStyle;

    public DefenseOnlyCombatProperties() {
        defenseElement = new HashMap<String, Integer>();
        defenseStyle = new HashMap<String, Integer>();
    }

    public DefenseOnlyCombatProperties(HashMap<String, Integer> defenseElement, HashMap<String, Integer> defenseStyle) {
        this.defenseElement = defenseElement;
        this.defenseStyle = defenseStyle;
    }

    public HashMap<String, Integer> getDefenseElementCopy() {
        return MapHelper.getDeepcopy(defenseElement);
    }

    public HashMap<String, Integer> getDefenseStyleCopy() {
        return MapHelper.getDeepcopy(defenseStyle);
    }

    public void writeToBuffer(FriendlyByteBuf buf) {
        PacketBufferHelper.writeHashMap(buf, defenseElement);
        PacketBufferHelper.writeHashMap(buf, defenseStyle);
    }

    public void readFromBuffer(FriendlyByteBuf buf) {
        defenseElement = PacketBufferHelper.readHashMap(buf);
        defenseStyle = PacketBufferHelper.readHashMap(buf);
    }

    @Override
    public String toString() {
        return "\nDefElement=" + defenseElement.toString() + "\nDefStyle=" + defenseStyle.toString();
    }

}
