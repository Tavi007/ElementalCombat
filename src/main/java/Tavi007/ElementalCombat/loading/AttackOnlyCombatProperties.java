package Tavi007.ElementalCombat.loading;

import com.google.gson.annotations.SerializedName;

import Tavi007.ElementalCombat.config.ServerConfig;
import net.minecraft.network.FriendlyByteBuf;

public class AttackOnlyCombatProperties {

    @SerializedName("attack_style")
    private String attackStyle;
    @SerializedName("attack_element")
    private String attackElement;

    public AttackOnlyCombatProperties() {
        attackStyle = ServerConfig.getDefaultStyle();
        attackElement = ServerConfig.getDefaultElement();
    }

    public AttackOnlyCombatProperties(String attackStyle, String attackElement) {
        if (attackStyle == null || attackStyle.isEmpty()) {
            this.attackStyle = ServerConfig.getDefaultStyle();
        } else {
            this.attackStyle = attackStyle;
        }
        if (attackElement == null || attackElement.isEmpty()) {
            this.attackElement = ServerConfig.getDefaultElement();
        } else {
            this.attackElement = attackElement;
        }
    }

    public String getAttackStyleCopy() {
        return new String(attackStyle);
    }

    public String getAttackElementCopy() {
        return new String(attackElement);
    }

    public boolean isEmpty() {
        return attackStyle.equals(ServerConfig.getDefaultStyle()) && attackElement.equals(ServerConfig.getDefaultElement());
    }

    public void writeToBuffer(FriendlyByteBuf buf) {
        buf.writeUtf(attackStyle);
        buf.writeUtf(attackElement);
    }

    public void readFromBuffer(FriendlyByteBuf buf) {
        attackStyle = buf.readUtf();
        attackElement = buf.readUtf();
    }

    @Override
    public String toString() {
        return "\nAttackStyle=" + attackStyle.toString()
            + "\nAttackElement=" + attackElement.toString();
    }

}
