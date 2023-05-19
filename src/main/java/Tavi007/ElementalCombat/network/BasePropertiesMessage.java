package Tavi007.ElementalCombat.network;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.loading.AttackOnlyCombatProperties;
import Tavi007.ElementalCombat.loading.BiomeCombatProperties;
import Tavi007.ElementalCombat.loading.ElementalCombatProperties;
import Tavi007.ElementalCombat.loading.MobCombatProperties;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class BasePropertiesMessage extends MessageToClient {

    private AttackOnlyCombatProperties baseAttackProperties;
    private Map<ResourceLocation, MobCombatProperties> mobData;
    private Map<ResourceLocation, ElementalCombatProperties> itemData;
    private Map<ResourceLocation, BiomeCombatProperties> biomeData;
    private Map<ResourceLocation, AttackOnlyCombatProperties> damageSourceData;
    private Map<ResourceLocation, AttackOnlyCombatProperties> projectileData;

    private BasePropertiesMessage() {
    }

    public BasePropertiesMessage(AttackOnlyCombatProperties baseAttackProperties,
            Map<ResourceLocation, MobCombatProperties> mobData,
            Map<ResourceLocation, ElementalCombatProperties> itemData,
            Map<ResourceLocation, BiomeCombatProperties> biomeData,
            Map<ResourceLocation, AttackOnlyCombatProperties> damageSourceData,
            Map<ResourceLocation, AttackOnlyCombatProperties> projectileData) {
        this.baseAttackProperties = baseAttackProperties;
        this.mobData = mobData;
        this.itemData = itemData;
        this.biomeData = biomeData;
        this.damageSourceData = damageSourceData;
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

    public Map<ResourceLocation, BiomeCombatProperties> getBiomeData() {
        return biomeData;
    }

    public Map<ResourceLocation, AttackOnlyCombatProperties> getProjectileData() {
        return projectileData;
    }

    public Map<ResourceLocation, AttackOnlyCombatProperties> getDamageSourceData() {
        return damageSourceData;
    }

    public static BasePropertiesMessage decode(FriendlyByteBuf buf) {
        BasePropertiesMessage ret = new BasePropertiesMessage();
        try {
            ret.baseAttackProperties = readBaseAttack(buf);
            ret.mobData = readMob(buf);
            ret.itemData = readItem(buf);
            ret.biomeData = readBiome(buf);
            ret.projectileData = readAttackOnly(buf, ret.baseAttackProperties);
            ret.damageSourceData = readAttackOnly(buf, ret.baseAttackProperties);

        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            ElementalCombat.LOGGER.warn("Exception while reading BasePropertiesMessage: " + e);
            return ret;
        }
        ret.messageIsValid = true;
        return ret;
    }

    public void encode(FriendlyByteBuf buf) {
        writeBaseAttack(buf);
        writeMob(buf);
        writeItem(buf);
        writeBiome(buf);
        writeProjectile(buf);
        writeDamageSource(buf);
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

    private void writeDamageSource(FriendlyByteBuf buf) {
        buf.writeInt(damageSourceData.size());
        damageSourceData.forEach((key, value) -> {
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

    private static Map<ResourceLocation, BiomeCombatProperties> readBiome(FriendlyByteBuf buf) {
        Builder<ResourceLocation, BiomeCombatProperties> builder = ImmutableMap.builder();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = new ResourceLocation(buf.readUtf());
            BiomeCombatProperties value = new BiomeCombatProperties();
            value.readFromBuffer(buf);
            builder.put(key, value);
        }
        return builder.build();
    }

    private static Map<ResourceLocation, AttackOnlyCombatProperties> readAttackOnly(FriendlyByteBuf buf, AttackOnlyCombatProperties defaulProperty) {
        Builder<ResourceLocation, AttackOnlyCombatProperties> builder = ImmutableMap.builder();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation key = new ResourceLocation(buf.readUtf());
            AttackOnlyCombatProperties value = defaulProperty;
            value.readFromBuffer(buf);
            builder.put(key, value);
        }
        return builder.build();
    }
}
