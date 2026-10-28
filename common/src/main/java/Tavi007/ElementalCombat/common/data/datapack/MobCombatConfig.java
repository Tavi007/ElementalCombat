package Tavi007.ElementalCombat.common.data.datapack;

import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import com.google.gson.annotations.SerializedName;
import net.minecraft.network.FriendlyByteBuf;

public record MobCombatConfig(AttackConfig attack,
                              DefenseConfig defense,
                              @SerializedName("biome_dependent") boolean biomeDependent) {

    public MobCombatConfig() {
        this(new AttackConfig(), new DefenseConfig(), false);
    }

    public static MobCombatConfig read(FriendlyByteBuf buf) {
        AttackConfig attack = AttackConfig.read(buf);
        DefenseConfig defense = DefenseConfig.read(buf);
        return new MobCombatConfig(attack, defense, buf.readBoolean());
    }

    public static void write(FriendlyByteBuf buf, MobCombatConfig config) {
        AttackConfig.write(buf, config.attack);
        DefenseConfig.write(buf, config.defense);
        buf.writeBoolean(config.biomeDependent);
    }

    public boolean isValid() {
        return attack != null
                && attack.isValid()
                && defense != null
                && defense.isValid();
    }
}
