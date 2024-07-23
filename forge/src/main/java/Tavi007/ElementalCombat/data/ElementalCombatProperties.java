package Tavi007.ElementalCombat.data;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.util.MapHelper;
import Tavi007.ElementalCombat.util.PacketBufferHelper;
import net.minecraft.network.FriendlyByteBuf;

public class ElementalCombatProperties {

    @SerializedName("defense_style")
    protected HashMap<String, Integer> defenseStyle;
    @SerializedName("defense_element")
    protected HashMap<String, Integer> defenseElement;

    @SerializedName("attack_style")
    protected String attackStyle;
    @SerializedName("attack_element")
    protected String attackElement;

    public ElementalCombatProperties(HashMap<String, Integer> defenseStyle, HashMap<String, Integer> defenseElement, String attackStyle, String attackElement) {
        this.defenseStyle = defenseStyle;
        this.defenseElement = defenseElement;
        if (attackStyle == null || attackStyle.isEmpty()) {
            this.attackStyle = BasePropertiesAPI.getDefaultAttackStyle();
        } else {
            this.attackStyle = attackStyle;
        }
        if (attackElement == null || attackElement.isEmpty()) {
            this.attackElement = BasePropertiesAPI.getDefaultAttackElement();
        } else {
            this.attackElement = attackElement;
        }
    }

    public ElementalCombatProperties() {
        defenseStyle = new HashMap<String, Integer>();
        defenseElement = new HashMap<String, Integer>();
        attackStyle = BasePropertiesAPI.getDefaultAttackStyle();
        attackElement = BasePropertiesAPI.getDefaultAttackElement();
    }

    public HashMap<String, Integer> getDefenseStyleCopy() {
        return MapHelper.getDeepcopy(defenseStyle);
    }

    public HashMap<String, Integer> getDefenseElementCopy() {
        return MapHelper.getDeepcopy(defenseElement);
    }

    public String getAttackStyleCopy() {
        return new String(attackStyle);
    }

    public String getAttackElementCopy() {
        return new String(attackElement);
    }

    public void writeToBuffer(FriendlyByteBuf buf) {
        PacketBufferHelper.writeHashMap(buf, defenseStyle);
        PacketBufferHelper.writeHashMap(buf, defenseElement);
        buf.writeUtf(attackStyle);
        buf.writeUtf(attackElement);
    }

    public void readFromBuffer(FriendlyByteBuf buf) {
        defenseStyle = PacketBufferHelper.readHashMap(buf);
        defenseElement = PacketBufferHelper.readHashMap(buf);
        attackStyle = buf.readUtf();
        attackElement = buf.readUtf();
    }

    @Override
    public String toString() {
        return "\nDefStyle=" + defenseStyle.toString()
            + "\nDefElement=" + defenseElement.toString()
            + "\nAtckStyle=" + attackStyle.toString()
            + "\nAtckElement=" + attackElement.toString();
    }

}
