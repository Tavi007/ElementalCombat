package Tavi007.ElementalCombat.common.data.datapack;

import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import net.minecraft.network.FriendlyByteBuf;

public record CombatConfig(AttackConfig attack, DefenseConfig defense) {

    public CombatConfig() {
        this(new AttackConfig(), new DefenseConfig());
    }

    public static CombatConfig read(FriendlyByteBuf buf) {
        AttackConfig attack = AttackConfig.read(buf);
        DefenseConfig defense = DefenseConfig.read(buf);
        return new CombatConfig(attack, defense);
    }

    public static void write(FriendlyByteBuf buf, CombatConfig config) {
        AttackConfig.write(buf, config.attack);
        DefenseConfig.write(buf, config.defense);
    }

    public boolean isValid() {
        return attack != null
                && attack.isValid()
                && defense != null
                && defense.isValid();
    }
}
