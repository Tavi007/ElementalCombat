package Tavi007.ElementalCombat.server.network;

import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesHelper;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import Tavi007.ElementalCombat.common.network.UpdateEntityAttackLayerPacket;
import Tavi007.ElementalCombat.common.network.UpdateEntityDefenseLayerPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class NetworkHelper {

    /**
     * Sends a message to all clients and syncronize the DefenseData {@link DefenseData} and AttackData {@link AttackData} of the {@link LivingEntity}.
     *
     * @param livingEntity A LivingEntity.
     */
    public static void syncMessageForClients(LivingEntity livingEntity) {
        if (!livingEntity.level().isClientSide) {
            CapabilitiesHelper.updateMainHandItemLayer(livingEntity);
            AttackData attackData = CapabilitiesAccessors.getAttackData(livingEntity);
            attackData.getLayers().forEach((rl, layer) -> {
                UpdateEntityAttackLayerPacket attackMessageToClient = new UpdateEntityAttackLayerPacket(layer, rl, livingEntity.getId());
                ServerPacketSender.sendPacket(attackMessageToClient);
            });
            DefenseData defenseData = CapabilitiesAccessors.getDefenseData(livingEntity);
            defenseData.getLayers().forEach((rl, layer) -> {
                UpdateEntityDefenseLayerPacket defenseMessageToClient = new UpdateEntityDefenseLayerPacket(layer, rl, livingEntity.getId());
                ServerPacketSender.sendPacket(defenseMessageToClient);
            });
        }
    }

    /**
     * Sends a message to all clients. The provided DefenseLayer {@link DefenseLayer} will be put to the entity on the client.
     * In addition the message syncs the AttackData {@link AttackData} of the {@link LivingEntity}.
     *
     * @param livingEntity A LivingEntity.
     * @param layer        The DefenseLayer to be added to the defense values of the LivingEntity (on the client).
     */
    public static void syncDefenseLayerMessageForClients(LivingEntity livingEntity, DefenseLayer layer, ResourceLocation location) {
        if (!livingEntity.level().isClientSide) {
            CapabilitiesAccessors.getDefenseData(livingEntity).putLayer(location, layer);
            UpdateEntityDefenseLayerPacket defenseMessageToClient = new UpdateEntityDefenseLayerPacket(layer, location, livingEntity.getId());
            ServerPacketSender.sendPacket(defenseMessageToClient);
        }
    }

    public static void syncAttackLayerMessageForClients(LivingEntity livingEntity, AttackLayer layer, ResourceLocation location) {
        if (!livingEntity.level().isClientSide) {
            CapabilitiesAccessors.getAttackData(livingEntity).putLayer(location, layer);
            UpdateEntityAttackLayerPacket attackMessageToClient = new UpdateEntityAttackLayerPacket(layer, location, livingEntity.getId());
            ServerPacketSender.sendPacket(attackMessageToClient);
        }
    }

    public static void syncJsonMessageForClients(Player player) {
//        if (!player.level().isClientSide && player instanceof ServerPlayer) {
//            SyncronizeDatapackPacket messageToClient = ElementalCombat.COMBAT_PROPERTIES_MANGER.createSyncMessage();
//            ServerPacketSender.sendPacket(messageToClient, player);
//        }
    }
}
