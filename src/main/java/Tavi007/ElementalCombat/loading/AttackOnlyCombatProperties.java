package Tavi007.ElementalCombat.loading;

import com.google.common.base.Optional;
import com.google.gson.annotations.SerializedName;

import net.minecraft.network.FriendlyByteBuf;

public class AttackOnlyCombatProperties {

    @SerializedName("attack_style")
    private String attackStyle;
    @SerializedName("attack_element")
    private String attackElement;

    public AttackOnlyCombatProperties() {
        this.attackStyle = "";
        this.attackElement = "";
    }

    public AttackOnlyCombatProperties(AttackOnlyCombatProperties properties) {
        this.attackStyle = properties.getAttackStyle();
        this.attackElement = properties.getAttackElement();
    }

    public String getAttackStyle() {
        return Optional.fromNullable(this.attackStyle).or("");
    }

    public String getAttackElement() {
        return Optional.fromNullable(this.attackElement).or("");
    }

    public void writeToBuffer(FriendlyByteBuf buf) {
        buf.writeUtf(getAttackStyle());
        buf.writeUtf(getAttackElement());
    }

    public void readFromBuffer(FriendlyByteBuf buf) {
        attackStyle = buf.readUtf();
        attackElement = buf.readUtf();
    }

    @Override
    public String toString() {
        return "\nAttackStyle=" + getAttackStyle().toString()
            + "\nAttackElement=" + getAttackElement().toString();
    }

}
