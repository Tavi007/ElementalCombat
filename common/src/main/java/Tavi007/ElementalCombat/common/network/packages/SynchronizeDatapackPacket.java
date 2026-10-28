package Tavi007.ElementalCombat.common.network.packages;

import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import Tavi007.ElementalCombat.common.data.datapack.CombatConfig;
import Tavi007.ElementalCombat.common.data.datapack.CombatPropertiesManager;
import Tavi007.ElementalCombat.common.data.datapack.MobCombatConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Optional;

public record SynchronizeDatapackPacket(
        AttackProperty defaultAttackProperty,
        Map<ResourceLocation, MobCombatConfig> defaultMobConfigs,
        Map<ResourceLocation, CombatConfig> defaultItemConfigs,
        Map<ResourceLocation, DefenseConfig> defaultBiomeConfigs,
        Map<ResourceLocation, AttackConfig> defaultProjectileConfigs,
        Map<ResourceLocation, AttackProperty> defaultDamageTypeProperties
) implements IPacket {

    public static SynchronizeDatapackPacket read(FriendlyByteBuf buf) {
        return new SynchronizeDatapackPacket(
                AttackProperty.read(buf),
                buf.readMap(FriendlyByteBuf::readResourceLocation, MobCombatConfig::read),
                buf.readMap(FriendlyByteBuf::readResourceLocation, CombatConfig::read),
                buf.readMap(FriendlyByteBuf::readResourceLocation, DefenseConfig::read),
                buf.readMap(FriendlyByteBuf::readResourceLocation, AttackConfig::read),
                buf.readMap(FriendlyByteBuf::readResourceLocation, AttackProperty::read));
    }

    public static void write(FriendlyByteBuf buf, SynchronizeDatapackPacket packet) {
        AttackProperty.write(buf, packet.defaultAttackProperty);
        buf.writeMap(packet.defaultMobConfigs, FriendlyByteBuf::writeResourceLocation, MobCombatConfig::write);
        buf.writeMap(packet.defaultItemConfigs, FriendlyByteBuf::writeResourceLocation, CombatConfig::write);
        buf.writeMap(packet.defaultBiomeConfigs, FriendlyByteBuf::writeResourceLocation, DefenseConfig::write);
        buf.writeMap(packet.defaultProjectileConfigs, FriendlyByteBuf::writeResourceLocation, AttackConfig::write);
        buf.writeMap(packet.defaultDamageTypeProperties, FriendlyByteBuf::writeResourceLocation, AttackProperty::write);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        write(buf, this);
    }


    @Override
    public void processPacket(Optional<Level> level) {
        CombatPropertiesManager.replace(
                defaultAttackProperty,
                defaultMobConfigs,
                defaultItemConfigs,
                defaultBiomeConfigs,
                defaultProjectileConfigs,
                defaultDamageTypeProperties
        );
    }

    @Override
    public boolean isValid() {
        return defaultAttackProperty != null
                && defaultAttackProperty.isValid()
                && defaultMobConfigs != null
                && defaultMobConfigs.values().stream().allMatch(obj -> obj != null && obj.isValid())
                && defaultItemConfigs != null
                && defaultItemConfigs.values().stream().allMatch(obj -> obj != null && obj.isValid())
                && defaultBiomeConfigs != null
                && defaultBiomeConfigs.values().stream().allMatch(obj -> obj != null && obj.isValid())
                && defaultProjectileConfigs != null
                && defaultProjectileConfigs.values().stream().allMatch(obj -> obj != null && obj.isValid())
                && defaultDamageTypeProperties != null
                && defaultDamageTypeProperties.values().stream().allMatch(obj -> obj != null && obj.isValid());
    }
}
