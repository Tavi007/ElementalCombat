package Tavi007.ElementalCombat.common.network;

import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatLayer;
import Tavi007.ElementalCombat.common.api.data.ElementalCombatMobData;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Map;

public class SyncronizeDatapackPacket extends AbstractPacket {

    private final AttackLayer baseAttackProperties;
    private final Map<ResourceLocation, ElementalCombatMobData> mobData;
    private final Map<ResourceLocation, ElementalCombatLayer> itemData;
    private final Map<ResourceLocation, DefenseLayer> biomeData;
    private final Map<ResourceLocation, AttackLayer> damageTypeData;
    private final Map<ResourceLocation, AttackLayer> projectileData;

    public SyncronizeDatapackPacket(AttackLayer baseAttackProperties,
                                    Map<ResourceLocation, ElementalCombatMobData> mobData,
                                    Map<ResourceLocation, ElementalCombatLayer> itemData,
                                    Map<ResourceLocation, DefenseLayer> biomeData,
                                    Map<ResourceLocation, AttackLayer> damageTypeData,
                                    Map<ResourceLocation, AttackLayer> projectileData) {
        this.baseAttackProperties = baseAttackProperties;
        this.mobData = mobData;
        this.itemData = itemData;
        this.biomeData = biomeData;
        this.damageTypeData = damageTypeData;
        this.projectileData = projectileData;
    }

    public SyncronizeDatapackPacket(FriendlyByteBuf buf) {
        this.baseAttackProperties = readBaseAttack(buf);
        this.mobData = readMob(buf);
        this.itemData = readItem(buf);
        this.biomeData = readBiome(buf);
        this.projectileData = readAttackOnly(buf, baseAttackProperties);
        this.damageTypeData = readAttackOnly(buf, baseAttackProperties);
    }

    private static AttackLayer readBaseAttack(FriendlyByteBuf buf) {
        AttackLayer value = new AttackLayer("hit", "normal");
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
            AttackLayer value = new AttackLayer(base.getStyle(), base.getElement());
            value.readFromBuffer(buf);
            builder.put(key, value);
        }
        return builder.build();
    }

    public AttackLayer getBaseAttack() {
        return baseAttackProperties;
    }

    public Map<ResourceLocation, ElementalCombatMobData> getMobData() {
        return mobData;
    }

    public Map<ResourceLocation, ElementalCombatLayer> getItemData() {
        return itemData;
    }

    public Map<ResourceLocation, DefenseLayer> getBiomeData() {
        return biomeData;
    }

    public Map<ResourceLocation, AttackLayer> getProjectileData() {
        return projectileData;
    }

    public Map<ResourceLocation, AttackLayer> getDamageTypeData() {
        return damageTypeData;
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
        baseAttackProperties.writeToBuffer(buf);
    }

    private void writeMob(FriendlyByteBuf buf) {
        buf.writeInt(mobData.size());
        mobData.forEach((key, value) -> {
            buf.writeUtf(key.toString());
            value.writeToBuffer(buf);
        });
    }

    private void writeItem(FriendlyByteBuf buf) {
        buf.writeInt(itemData.size());
        itemData.forEach((key, value) -> {
            buf.writeUtf(key.toString());
            value.writeToBuffer(buf);
        });
    }

    private void writeBiome(FriendlyByteBuf buf) {
        buf.writeInt(biomeData.size());
        biomeData.forEach((key, value) -> {
            buf.writeUtf(key.toString());
            value.writeToBuffer(buf);
        });
    }

    private void writeProjectile(FriendlyByteBuf buf) {
        buf.writeInt(projectileData.size());
        projectileData.forEach((key, value) -> {
            buf.writeUtf(key.toString());
            value.writeToBuffer(buf);
        });
    }

    private void writeDamageType(FriendlyByteBuf buf) {
        buf.writeInt(damageTypeData.size());
        damageTypeData.forEach((key, value) -> {
            buf.writeUtf(key.toString());
            value.writeToBuffer(buf);
        });
    }

    @Override
    public void processPacket(Level level) {
        //DatapackDataAccessor.;
    }

    @Override
    public boolean isValid() {
        return baseAttackProperties != null && mobData != null && itemData != null && biomeData != null && projectileData != null && damageTypeData != null;
    }
}
