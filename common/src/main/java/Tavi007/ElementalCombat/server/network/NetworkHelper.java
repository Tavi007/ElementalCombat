package Tavi007.ElementalCombat.server.network;

import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackBranch;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.common.data.datapack.CombatPropertiesManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class NetworkHelper {

    /**
     * Sends a message to all clients and syncronize the DefenseConfig {@link DefenseConfig} and AttackConfig {@link AttackConfig} of the {@link LivingEntity}.
     *
     * @param livingEntity A LivingEntity.
     */
    public static void syncMessageForClients(LivingEntity livingEntity) {
//        if (!livingEntity.level().isClientSide) {
//            CapabilitiesHelper.updateMainHandItemLayer(livingEntity);
//            AttackConfig attackData = CapabilitiesAccessors.getAttackConfig(livingEntity);
//            attackData.getLayers().forEach((rl, layer) -> {
//                UpdateEntityAttackLayerPacket attackMessageToClient = new UpdateEntityAttackLayerPacket(layer, rl, livingEntity.getId());
//                ServerPacketSender.sendPacket(attackMessageToClient);
//            });
//            DefenseConfig defenseData = CapabilitiesAccessors.getDefenseConfig(livingEntity);
//            defenseData.getLayers().forEach((rl, layer) -> {
//                UpdateEntityDefenseLayerPacket defenseMessageToClient = new UpdateEntityDefenseLayerPacket(layer, rl, livingEntity.getId());
//                ServerPacketSender.sendPacket(defenseMessageToClient);
//            });
//        }
    }

    public static void syncDefenseLayerMessageForClients(Entity livingEntity, DefenseLayer layer, ResourceLocation location) {
//        if (!livingEntity.level().isClientSide) {
//            CapabilitiesAccessors.getDefenseConfig(livingEntity).putLayer(location, layer);
//            UpdateEntityDefenseLayerPacket defenseMessageToClient = new UpdateEntityDefenseLayerPacket(layer, location, livingEntity.getId());
//            ServerPacketSender.sendPacket(defenseMessageToClient);
//        }
    }

    /**
     * Sends a message to all clients. The provided AttackBranch {@link AttackBranch} will be put to the entity on the client.
     * In addition the message syncs the AttackConfig {@link AttackConfig} of the {@link LivingEntity}.
     *
     * @param livingEntity A LivingEntity.
     * @param branch        The AttackBranch to be added to the LivingEntity (on the client).
     */
    public static void syncAttackLayerMessageForClients(Entity livingEntity, AttackBranch branch, ResourceLocation location) {
//        if (!livingEntity.level().isClientSide) {
//            CapabilitiesAccessors.getAttackData(livingEntity).putLayer(location, layer);
//            UpdateEntityAttackLayerPacket attackMessageToClient = new UpdateEntityAttackLayerPacket(layer, location, livingEntity.getId());
//            ServerPacketSender.sendPacket(attackMessageToClient);
//        }
    }

    public static void syncJsonMessageForClients(Player player) {
        if (!player.level().isClientSide && player instanceof ServerPlayer) {
            CombatPropertiesManager.sendSyncMessage((ServerPlayer) player);
        }
    }
}
