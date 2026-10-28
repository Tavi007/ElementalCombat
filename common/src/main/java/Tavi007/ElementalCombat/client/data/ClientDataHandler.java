package Tavi007.ElementalCombat.client.data;

import Tavi007.ElementalCombat.common.data.capabilities.Condition;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import net.minecraft.util.Tuple;

import java.util.HashMap;
import java.util.Map;

public class ClientDataHandler {

    public static TooltipData getTooltipData(AttackConfig attack, DefenseConfig defense) {
        return new TooltipData(getTooltipAttackData(attack), getTooltipDefenseData(defense));
    }

    private static TooltipAttackData getTooltipAttackData(AttackConfig attack) {
        // TODO: get correct values
        Map<Condition, Tuple<String, String>> conditionals = new HashMap<>();
        String descriptionId = "description.elemental_combat.condition.test_id";
        conditionals.put(new Condition("test", null), new Tuple<>("hit", "normal"));
        return new TooltipAttackData("hit", "normal", conditionals);
    }

    private static TooltipDefenseData getTooltipDefenseData(DefenseConfig defense) {
        // TODO: get correct values
        Map<String, Integer> baseStyle = Map.of("slash", 10, "bash", -10);
        Map<String, Integer> baseElement = Map.of("fire", 100, "ice", 105);
        String descriptionId = "description.elemental_combat.condition.test_id";
        Map<Condition, Tuple<Map<String, Integer>, Map<String, Integer>>> conditionals = new HashMap<>();
        conditionals.put(new Condition("test", null), new Tuple<>(baseStyle, baseElement));
        return new TooltipDefenseData(baseStyle, baseElement, conditionals);
    }

    public static HudData getHudData(AttackConfig attack, DefenseConfig defense) {
        Tuple<String, String> attackData = getHudAttackData(attack);
        Tuple<Map<String, Integer>, Map<String, Integer>> defenseData = getHudDefenseData(defense);
        return new HudData(attackData.getA(), attackData.getB(), defenseData.getA(), defenseData.getB());
    }

    public static Tuple<String, String> getHudAttackData(AttackConfig attack) {
        // TODO: get correct values
        return new Tuple<>("hit", "normal");
    }

    public static Tuple<Map<String, Integer>, Map<String, Integer>> getHudDefenseData(DefenseConfig defense) {
        // TODO: get correct values
        Map<String, Integer> defenseStyle = Map.of("slash", 10, "bash", -10, "fire", 100, "ice", 105);
        Map<String, Integer> defenseElement = Map.of("fire", 100, "ice", 105);
        return new Tuple<>(defenseStyle, defenseElement);
    }
}
