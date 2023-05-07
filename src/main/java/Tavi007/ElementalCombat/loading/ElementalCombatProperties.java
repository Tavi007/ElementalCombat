package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import com.google.common.base.Optional;
import com.google.gson.annotations.SerializedName;

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
        this.attackStyle = attackStyle;
        this.attackElement = attackElement;
    }

    public ElementalCombatProperties() {
        this.defenseStyle = new HashMap<String, Integer>();
        this.defenseElement = new HashMap<String, Integer>();
        this.attackStyle = "";
        this.attackElement = "";
    }

    public ElementalCombatProperties(ElementalCombatProperties properties) {
        this.defenseStyle = properties.getDefenseStyle();
        this.defenseElement = properties.getDefenseElement();
        this.attackStyle = properties.getAttackStyle();
        this.attackElement = properties.getAttackElement();
    }

    public HashMap<String, Integer> getDefenseStyle() {
        return Optional.fromNullable(defenseStyle).or(new HashMap<>());
    }

    public HashMap<String, Integer> getDefenseElement() {
        return Optional.fromNullable(defenseElement).or(new HashMap<>());
    }

    public String getAttackStyle() {
        return Optional.fromNullable(this.attackStyle).or("");
    }

    public String getAttackElement() {
        return Optional.fromNullable(this.attackElement).or("");
    }

    public void writeToBuffer(FriendlyByteBuf buf) {
        PacketBufferHelper.writeHashMap(buf, getDefenseStyle());
        PacketBufferHelper.writeHashMap(buf, getDefenseElement());
        buf.writeUtf(getAttackStyle());
        buf.writeUtf(getAttackElement());
    }

    public void readFromBuffer(FriendlyByteBuf buf) {
        defenseStyle = PacketBufferHelper.readHashMap(buf);
        defenseElement = PacketBufferHelper.readHashMap(buf);
        attackStyle = buf.readUtf();
        attackElement = buf.readUtf();
    }

    @Override
    public String toString() {
        return "\nDefStyle=" + getDefenseStyle().toString()
            + "\nDefElement=" + getDefenseElement().toString()
            + "\nAtckStyle=" + getAttackStyle().toString()
            + "\nAtckElement=" + getAttackElement().toString();
    }

}
