package Tavi007.ElementalCombat.loading;

import com.google.gson.annotations.SerializedName;

import Tavi007.ElementalCombat.config.ServerConfig;
import net.minecraft.network.PacketBuffer;

public class AttackOnlyCombatProperties {

    @SerializedName("attack_style")
    private String attackStyle;
    @SerializedName("attack_element")
    private String attackElement;

    public AttackOnlyCombatProperties() {
        this.attackStyle = ServerConfig.getDefaultStyle();
        this.attackElement = ServerConfig.getDefaultElement();
    }

    public AttackOnlyCombatProperties(String attackStyle, String attackElement) {
        if (attackStyle.isEmpty()) {
            this.attackStyle = ServerConfig.getDefaultStyle();
        } else {
            this.attackStyle = attackStyle;
        }
        if (attackElement.isEmpty()) {
            this.attackElement = ServerConfig.getDefaultElement();
        } else {
            this.attackElement = attackElement;
        }
    }

    public AttackOnlyCombatProperties(AttackOnlyCombatProperties properties) {
        this.attackElement = properties.getAttackElement();
        this.attackStyle = properties.getAttackStyle();
    }

    public String getAttackStyle() {
        return this.attackStyle;
    }

    public String getAttackElement() {
        return this.attackElement;
    }

    public boolean isEmpty() {
        return attackStyle.equals(ServerConfig.getDefaultStyle()) && attackElement.equals(ServerConfig.getDefaultElement());
    }

    public void writeToBuffer(PacketBuffer buf) {
        buf.writeString(attackStyle);
        buf.writeString(attackElement);
    }

    public void readFromBuffer(PacketBuffer buf) {
        attackStyle = buf.readString();
        attackElement = buf.readString();
    }

    @Override
    public String toString() {
        return "\nAttackStyle=" + attackStyle.toString()
            + "\nAttackElement=" + attackElement.toString();
    }

}
