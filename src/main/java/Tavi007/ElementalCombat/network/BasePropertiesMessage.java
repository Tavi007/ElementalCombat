package Tavi007.ElementalCombat.network;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.loading.AttackOnlyCombatProperties;
import Tavi007.ElementalCombat.loading.BiomeCombatProperties;
import Tavi007.ElementalCombat.loading.ItemCombatProperties;
import Tavi007.ElementalCombat.loading.MobCombatProperties;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class BasePropertiesMessage extends MessageToClient {

	private Map<ResourceLocation, MobCombatProperties> mobData;
	private Map<ResourceLocation, ItemCombatProperties> itemData;
	private Map<ResourceLocation, BiomeCombatProperties> biomeData;
	private Map<ResourceLocation, AttackOnlyCombatProperties> damageSourceData;
	private Map<ResourceLocation, AttackOnlyCombatProperties> projectileData;

	private BasePropertiesMessage() {
	}
	
	public BasePropertiesMessage(Map<ResourceLocation, MobCombatProperties> mobData,
			Map<ResourceLocation, ItemCombatProperties> itemData,
			Map<ResourceLocation, BiomeCombatProperties> biomeData,
			Map<ResourceLocation, AttackOnlyCombatProperties> damageSourceData,
			Map<ResourceLocation, AttackOnlyCombatProperties> projectileData) {
		this.mobData = mobData;
		this.itemData = itemData;
		this.biomeData = biomeData;
		this.damageSourceData = damageSourceData;
		this.projectileData = projectileData;
	}

	public Map<ResourceLocation, MobCombatProperties> getMobData() {
		return mobData;
	}

	public Map<ResourceLocation, ItemCombatProperties> getItemData() {
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

	public static BasePropertiesMessage decode(PacketBuffer buf) {
		BasePropertiesMessage ret = new BasePropertiesMessage();
		try {
			ret.mobData = readMob(buf);
			ret.itemData = readItem(buf);
			ret.biomeData = readBiome(buf);
			ret.projectileData = readAttackOnly(buf);
			ret.damageSourceData = readAttackOnly(buf);

		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			ElementalCombat.LOGGER.warn("Exception while reading BasePropertiesMessage: " + e);
			return ret;
		}
		ret.messageIsValid = true;
		return ret;
	}

	public void encode(PacketBuffer buf) {
		writeMob(buf);
		writeItem(buf);
		writeBiome(buf);
		writeProjectile(buf);
		writeDamageSource(buf);
	}

	private void writeMob(PacketBuffer buf) {
		buf.writeInt(mobData.size());
		mobData.forEach((key, value) -> {
			buf.writeString(key.toString());
			value.writeToBuffer(buf);
		});
	}

	private void writeItem(PacketBuffer buf) {
		buf.writeInt(itemData.size());
		itemData.forEach((key, value) -> {
			buf.writeString(key.toString());
			value.writeToBuffer(buf);
		});
	}

	private void writeBiome(PacketBuffer buf) {
		buf.writeInt(biomeData.size());
		biomeData.forEach((key, value) -> {
			buf.writeString(key.toString());
			value.writeToBuffer(buf);
		});
	}

	private void writeProjectile(PacketBuffer buf) {
		buf.writeInt(projectileData.size());
		projectileData.forEach((key, value) -> {
			buf.writeString(key.toString());
			value.writeToBuffer(buf);
		});
	}

	private void writeDamageSource(PacketBuffer buf) {
		buf.writeInt(damageSourceData.size());
		damageSourceData.forEach((key, value) -> {
			buf.writeString(key.toString());
			value.writeToBuffer(buf);
		});
	}

	private static Map<ResourceLocation, MobCombatProperties> readMob(PacketBuffer buf) {
		Builder<ResourceLocation, MobCombatProperties> builder = ImmutableMap.builder();
		int size = buf.readInt();
		for (int i=0; i<size; i++) {
			ResourceLocation key = new ResourceLocation(buf.readString());
			MobCombatProperties value = new MobCombatProperties();
			value.readFromBuffer(buf);
			builder.put(key, value);
		}
		return builder.build();
	}

	private static Map<ResourceLocation, ItemCombatProperties> readItem(PacketBuffer buf) {
		Builder<ResourceLocation, ItemCombatProperties> builder = ImmutableMap.builder();
		int size = buf.readInt();
		for (int i=0; i<size; i++) {
			ResourceLocation key = new ResourceLocation(buf.readString());
			ItemCombatProperties value = new ItemCombatProperties();
			value.readFromBuffer(buf);
			builder.put(key, value);
		}
		return builder.build();
	}

	private static Map<ResourceLocation, BiomeCombatProperties> readBiome(PacketBuffer buf) {
		Builder<ResourceLocation, BiomeCombatProperties> builder = ImmutableMap.builder();
		int size = buf.readInt();
		for (int i=0; i<size; i++) {
			ResourceLocation key = new ResourceLocation(buf.readString());
			BiomeCombatProperties value = new BiomeCombatProperties();
			value.readFromBuffer(buf);
			builder.put(key, value);
		}
		return builder.build();
	}

	private static Map<ResourceLocation, AttackOnlyCombatProperties> readAttackOnly(PacketBuffer buf) {
		Builder<ResourceLocation, AttackOnlyCombatProperties> builder = ImmutableMap.builder();
		int size = buf.readInt();
		for (int i=0; i<size; i++) {
			ResourceLocation key = new ResourceLocation(buf.readString());
			AttackOnlyCombatProperties value = new AttackOnlyCombatProperties();
			value.readFromBuffer(buf);
			builder.put(key, value);
		}
		return builder.build();
	}
}
