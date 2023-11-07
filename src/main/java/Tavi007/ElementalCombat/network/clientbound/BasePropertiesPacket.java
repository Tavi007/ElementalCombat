package Tavi007.ElementalCombat.network.clientbound;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.loading.AttackOnlyCombatProperties;
import Tavi007.ElementalCombat.loading.DefenseOnlyCombatProperties;
import Tavi007.ElementalCombat.loading.ElementalCombatProperties;
import Tavi007.ElementalCombat.loading.MobCombatProperties;
import Tavi007.ElementalCombat.network.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent.Context;

public class BasePropertiesPacket extends Packet {

    private AttackOnlyCombatProperties baseAttackProperties;
    private Map<ResourceLocation, MobCombatProperties> mobData;
    private Map<ResourceLocation, ElementalCombatProperties> itemData;
    private Map<ResourceLocation, DefenseOnlyCombatProperties> biomeData;
    private Map<ResourceLocation, AttackOnlyCombatProperties> damageTypeData;
    private Map<ResourceLocation, AttackOnlyCombatProperties> projectileData;

    public BasePropertiesPacket(AttackOnlyCombatProperties baseAttackProperties,
            Map<ResourceLocation, MobCombatProperties> mobData,
            Map<ResourceLocation, ElementalCombatProperties> itemData,
            Map<ResourceLocation, DefenseOnlyCombatProperties> biomeData,
            Map<ResourceLocation, AttackOnlyCombatProperties> damageTypeData,
            Map<ResourceLocation, AttackOnlyCombatProperties> projectileData) {
        this.baseAttackProperties = baseAttackProperties;
        this.mobData = mobData;
        this.itemData = itemData;
        this.biomeData = biomeData;
        this.damageTypeData = damageTypeData;
        this.projectileData = projectileData;
    }

    public AttackOnlyCombatProperties getBaseAttack() {
        return baseAttackProperties;
    }

    public Map<ResourceLocation, MobCombatProperties> getMobData() {
        return mobData;
    }

    public Map<ResourceLocation, ElementalCombatProperties> getItemData() {
        return itemData;
    }

    public Map<ResourceLocation, DefenseOnlyCombatProperties> getBiomeData() {
        return biomeData;
    }

    public Map<ResourceLocation, AttackOnlyCombatProperties> getProjectileData() {
        return projectileData;
    }

    public Map<ResourceLocation, AttackOnlyCombatProperties> getDamageTypeData() {
        return damageTypeData;
    }

    public BasePropertiesPacket(FriendlyByteBuf buf) {
        this.baseAttackProperties = readBaseAttack(buf);
        this.mobData = readMob(buf);
        this.itemData = readItem(buf);
        this.biomeData = readBiome(buf);
        this.projectileData = readAttackOnly(buf, baseAttackProperties);
        this.damageTypeData = readAttackOnly(buf, baseAttackProperties);
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

    private static AttackOnlyCombatProperties readBaseAttack(FriendlyByteBuf buf) {
        AttackOnlyCombatProperties value = new AttackOnlyCombatProperties("hit", "normal");
        value.readFromBuffer(buf);
        return value;
    }

    private static Map<ResourceLocation, MobCombatProperties> readMob(FriendlyByteBuf buf) {
        Builder<ResourceLocation, MobCombatProperties> builder = ImmutableMap.builder();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = new ResourceLocation(buf.readUtf());
            MobCombatProperties value = new MobCombatProperties();
            value.readFromBuffer(buf);
            builder.put(key, value);
        }
        return builder.build();
    }

    private static Map<ResourceLocation, ElementalCombatProperties> readItem(FriendlyByteBuf buf) {
        Builder<ResourceLocation, ElementalCombatProperties> builder = ImmutableMap.builder();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = new ResourceLocation(buf.readUtf());
            ElementalCombatProperties value = new ElementalCombatProperties();
            value.readFromBuffer(buf);
            builder.put(key, value);
        }
        return builder.build();
    }

    private static Map<ResourceLocation, DefenseOnlyCombatProperties> readBiome(FriendlyByteBuf buf) {
        Builder<ResourceLocation, DefenseOnlyCombatProperties> builder = ImmutableMap.builder();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = new ResourceLocation(buf.readUtf());
            DefenseOnlyCombatProperties value = new DefenseOnlyCombatProperties();
            value.readFromBuffer(buf);
            builder.put(key, value);
        }
        return builder.build();
    }

    private static Map<ResourceLocation, AttackOnlyCombatProperties> readAttackOnly(FriendlyByteBuf buf, AttackOnlyCombatProperties base) {
        Builder<ResourceLocation, AttackOnlyCombatProperties> builder = ImmutableMap.builder();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = new ResourceLocation(buf.readUtf());
            AttackOnlyCombatProperties value = new AttackOnlyCombatProperties(base.getAttackStyleCopy(), base.getAttackElementCopy());
            value.readFromBuffer(buf);
            builder.put(key, value);
        }
        return builder.build();
    }

    @Override
    public void handle(Context context) {
        context.enqueueWork(() -> {
            if (!isValid()) {
                return;
            }
            ElementalCombat.COMBAT_PROPERTIES_MANGER.set(this);
            context.setPacketHandled(true);
        });
    }

    private boolean isValid() {
        return baseAttackProperties != null && mobData != null && itemData != null && biomeData != null && projectileData != null && damageTypeData != null;
    }
}
