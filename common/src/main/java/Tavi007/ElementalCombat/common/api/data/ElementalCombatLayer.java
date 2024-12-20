package Tavi007.ElementalCombat.common.api.data;

import net.minecraft.network.FriendlyByteBuf;

public class ElementalCombatLayer {

    protected AttackLayer attack;
    protected DefenseLayer defense;

    public ElementalCombatLayer(AttackLayer attack, DefenseLayer defense) {
        this.attack = attack;
        this.defense = defense;
    }

    public ElementalCombatLayer() {
        this.attack = new AttackLayer();
        this.defense = new DefenseLayer();
    }

    public AttackLayer getAttack() {
        return attack;
    }

    public DefenseLayer getDefense() {
        return defense;
    }

    public void writeToBuffer(FriendlyByteBuf buf) {
        boolean hasAttack = attack != null;
        buf.writeBoolean(hasAttack);
        if (hasAttack) {
            attack.writeToBuffer(buf);
        }
        boolean hasDefense = defense != null;
        buf.writeBoolean(hasAttack);
        if (hasDefense) {
            defense.writeToBuffer(buf);
        }
    }

    public void readFromBuffer(FriendlyByteBuf buf) {
        boolean hasAttack = buf.readBoolean();
        if (hasAttack) {
            attack = new AttackLayer();
            attack.readFromBuffer(buf);
        }
        boolean hasDefense = buf.readBoolean();
        if (hasDefense) {
            defense = new DefenseLayer();
            defense.readFromBuffer(buf);
        }
    }

    @Override
    public String toString() {
        return "attack=" + attack.toString() + "\n"
                + "defense=" + defense.toString();
    }

}
