package Tavi007.ElementalCombat.client.data;

import Tavi007.ElementalCombat.common.api.data.Condition;
import Tavi007.ElementalCombat.common.new_data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.new_data.capabilities.CombatDataTree;
import Tavi007.ElementalCombat.common.new_data.capabilities.DefenseData;
import net.minecraft.util.Tuple;

import java.util.HashMap;
import java.util.Map;

public class ClientDataHandler {

    public static TooltipData getTooltipData(CombatDataTree<AttackData> attackTree, CombatDataTree<DefenseData> defenseTree) {
        return new TooltipData(getTooltipAttackData(attackTree), getTooltipDefenseData(defenseTree));
    }

    private static TooltipAttackData getTooltipAttackData(CombatDataTree<AttackData> attackTree) {
        // TODO: get correct values
        Map<Condition, Tuple<String, String>> conditionals = new HashMap<>();
        conditionals.put(new Condition("test", "testId", null), new Tuple<>("hit", "normal"));
        return new TooltipAttackData("hit", "normal", conditionals);
    }

    private static TooltipDefenseData getTooltipDefenseData(CombatDataTree<DefenseData> defenseTree) {
        // TODO: get correct values
        Map<String, Integer> baseStyle = Map.of("slash", 10, "bash", -10);
        Map<String, Integer> baseElement = Map.of("fire", 100, "ice", 105);
        Map<Condition, Tuple<Map<String, Integer>, Map<String, Integer>>> conditionals = new HashMap<>();
        conditionals.put(new Condition("test", "testId", null), new Tuple<>(baseStyle, baseElement));
        return new TooltipDefenseData(baseStyle, baseElement, conditionals);
    }

    public static HudData getHudData(CombatDataTree<AttackData> attackTree, CombatDataTree<DefenseData> defenseTree) {
        Tuple<String, String> attackData = getHudAttackData(attackTree);
        Tuple<Map<String, Integer>, Map<String, Integer>> defenseData = getHudDefenseData(defenseTree);
        return new HudData(attackData.getA(), attackData.getB(), defenseData.getA(), defenseData.getB());
    }

    public static Tuple<String, String> getHudAttackData(CombatDataTree<AttackData> attackTree) {
        // TODO: get correct values
        return new Tuple<>("hit", "normal");
    }

    public static Tuple<Map<String, Integer>, Map<String, Integer>> getHudDefenseData(CombatDataTree<DefenseData> defenseTree) {
        // TODO: get correct values
        Map<String, Integer> defenseStyle = Map.of("slash", 10, "bash", -10, "fire", 100, "ice", 105);
        Map<String, Integer> defenseElement = Map.of("fire", 100, "ice", 105);
        return new Tuple<>(defenseStyle, defenseElement);
    }
}
