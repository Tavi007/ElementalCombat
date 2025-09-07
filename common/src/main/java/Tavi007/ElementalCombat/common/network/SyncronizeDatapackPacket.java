package Tavi007.ElementalCombat.common.network;

import Tavi007.ElementalCombat.common.api.data.*;
import Tavi007.ElementalCombat.common.data.DatapackDataAccessor;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Optional;

public class SyncronizeDatapackPacket extends AbstractPacket {

    private final AttackLayer defaultAttackLayer;
    private final Map<ResourceLocation, ElementalCombatMobData> defaultMobLayers;
    private final Map<ResourceLocation, ElementalCombatLayer> defaultItemLayers;
    private final Map<ResourceLocation, DefenseLayer> defaultBiomeLayers;
    private final Map<ResourceLocation, AttackLayer> defaultProjectileLayers;
    private final Map<ResourceLocation, AttackLayer> defaultDamageTypeLayers;

    public SyncronizeDatapackPacket(AttackLayer defaultAttackLayer,
                                    Map<ResourceLocation, ElementalCombatMobData> defaultMobLayers,
                                    Map<ResourceLocation, ElementalCombatLayer> defaultItemLayers,
                                    Map<ResourceLocation, DefenseLayer> defaultBiomeLayers,
                                    Map<ResourceLocation, AttackLayer> defaultProjectileLayers,
                                    Map<ResourceLocation, AttackLayer> defaultDamageTypeLayers) {
        this.defaultAttackLayer = defaultAttackLayer;
        this.defaultMobLayers = defaultMobLayers;
        this.defaultItemLayers = defaultItemLayers;
        this.defaultBiomeLayers = defaultBiomeLayers;
        this.defaultProjectileLayers = defaultProjectileLayers;
        this.defaultDamageTypeLayers = defaultDamageTypeLayers;
    }

    public SyncronizeDatapackPacket(FriendlyByteBuf buf) {
        this.defaultAttackLayer = readBaseAttack(buf);
        this.defaultMobLayers = readMob(buf);
        this.defaultItemLayers = readItem(buf);
        this.defaultBiomeLayers = readBiome(buf);
        this.defaultProjectileLayers = readAttackOnly(buf, defaultAttackLayer);
        this.defaultDamageTypeLayers = readAttackOnly(buf, defaultAttackLayer);
    }

    private static AttackLayer readBaseAttack(FriendlyByteBuf buf) {
        AttackLayer value = new AttackLayer("hit", "normal", Condition.BASE);
        value.readFromBuffer(buf);
        return value;
    }

    private static Map<ResourceLocation, ElementalCombatMobData> readMob(FriendlyByteBuf buf) {
        Builder<ResourceLocation, ElementalCombatMobData> builder = ImmutableMap.builder();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = new ResourceLocation(buf.readUtf());
            ElementalCombatMobData value = new ElementalCombatMobData();
            value.readFromBuffer(buf);
            builder.put(key, value);
        }
        return builder.build();
    }

    private static Map<ResourceLocation, ElementalCombatLayer> readItem(FriendlyByteBuf buf) {
        Builder<ResourceLocation, ElementalCombatLayer> builder = ImmutableMap.builder();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = new ResourceLocation(buf.readUtf());
            ElementalCombatLayer value = new ElementalCombatLayer();
            value.readFromBuffer(buf);
            builder.put(key, value);
        }
        return builder.build();
    }

    private static Map<ResourceLocation, DefenseLayer> readBiome(FriendlyByteBuf buf) {
        Builder<ResourceLocation, DefenseLayer> builder = ImmutableMap.builder();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = new ResourceLocation(buf.readUtf());
            DefenseLayer value = new DefenseLayer();
            value.readFromBuffer(buf);
            builder.put(key, value);
        }
        return builder.build();
    }

    private static Map<ResourceLocation, AttackLayer> readAttackOnly(FriendlyByteBuf buf, AttackLayer base) {
        Builder<ResourceLocation, AttackLayer> builder = ImmutableMap.builder();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = new ResourceLocation(buf.readUtf());
            AttackLayer value = new AttackLayer(base.getStyle(), base.getElement(), Condition.BASE);
            value.readFromBuffer(buf);
            builder.put(key, value);
        }
        return builder.build();
    }

    public void encode(FriendlyByteBuf buf) {
        writeBaseAttack(buf);
        writeMob(buf);
        writeItem(buf);
        writeBiome(buf);
        writeProjectile(buf);
        writeDamageType(buf);
    }

    private void writeBaseAttack(FriendlyByteBuf buf) {
        defaultAttackLayer.writeToBuffer(buf);
    }

    private void writeMob(FriendlyByteBuf buf) {
        buf.writeInt(defaultMobLayers.size());
        defaultMobLayers.forEach((key, value) -> {
            buf.writeUtf(key.toString());
            value.writeToBuffer(buf);
        });
    }

    private void writeItem(FriendlyByteBuf buf) {
        buf.writeInt(defaultItemLayers.size());
        defaultItemLayers.forEach((key, value) -> {
            buf.writeUtf(key.toString());
            value.writeToBuffer(buf);
        });
    }

    private void writeBiome(FriendlyByteBuf buf) {
        buf.writeInt(defaultBiomeLayers.size());
        defaultBiomeLayers.forEach((key, value) -> {
            buf.writeUtf(key.toString());
            value.writeToBuffer(buf);
        });
    }

    private void writeProjectile(FriendlyByteBuf buf) {
        buf.writeInt(defaultProjectileLayers.size());
        defaultProjectileLayers.forEach((key, value) -> {
            buf.writeUtf(key.toString());
            value.writeToBuffer(buf);
        });
    }

    private void writeDamageType(FriendlyByteBuf buf) {
        buf.writeInt(defaultDamageTypeLayers.size());
        defaultDamageTypeLayers.forEach((key, value) -> {
            buf.writeUtf(key.toString());
            value.writeToBuffer(buf);
        });
    }

    @Override
    public void processPacket(Optional<Level> level) {
        DatapackDataAccessor.resetDatapackData(defaultAttackLayer,
                defaultMobLayers,
                defaultItemLayers,
                defaultBiomeLayers,
                defaultProjectileLayers,
                defaultDamageTypeLayers);
    }

    @Override
    public boolean isValid() {
        return defaultAttackLayer != null && defaultMobLayers != null && defaultItemLayers != null && defaultBiomeLayers != null && defaultProjectileLayers != null && defaultDamageTypeLayers != null;
    }
}
