package Tavi007.ElementalCombat.client;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class ElementalCombatComponent implements TooltipComponent {

    private AttackData attackData = new AttackData();
    private DefenseData defenseData = new DefenseData();

    private boolean alwaysShowAttack = true;
    private boolean alwaysShowDefense = true;

    public ElementalCombatComponent(AttackData attackData, DefenseData defenseData) {
        this.attackData = attackData;
        this.defenseData = defenseData;
    }

    public int getNumberOfLines() {
        boolean showAttack = alwaysShowAttack
            || !attackData.isDefault();
        boolean showDefenseElement = alwaysShowDefense
            || !defenseData.getElementFactor().isEmpty();
        boolean showDefenseStyle = alwaysShowDefense
            || !defenseData.getStyleFactor().isEmpty();

        int numberOfLines = showAttack ? 1 : 0;
        numberOfLines += showDefenseElement ? 1 : 0;
        numberOfLines += showDefenseStyle ? 1 : 0;
        return numberOfLines;
    }

    public AttackData getAttackData() {
        return attackData;
    }

    public boolean hasAttackData() {
        return attackData != null;
    }

    public DefenseData getDefenseData() {
        return defenseData;
    }

    public boolean hasDefenseData() {
        return defenseData != null && !defenseData.isEmpty();
    }
}
