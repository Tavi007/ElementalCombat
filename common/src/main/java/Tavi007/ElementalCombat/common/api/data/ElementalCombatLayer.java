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
    }

    public void readFromBuffer(FriendlyByteBuf buf) {
    }

    @Override
    public String toString() {
        return "attack=" + attack.toString() + "\n"
                + "defense=" + defense.toString();
    }

}
