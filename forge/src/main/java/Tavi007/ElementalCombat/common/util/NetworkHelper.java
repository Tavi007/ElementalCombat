package Tavi007.ElementalCombat.common.util;

import Tavi007.ElementalCombat.common.ElementalCombat;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.data.DatapackDataAccessor;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import Tavi007.ElementalCombat.common.network.PacketManager;
import Tavi007.ElementalCombat.common.network.packets.SyncronizeDatapackPacket;
import Tavi007.ElementalCombat.common.network.packets.UpdateEntityAttackLayerPacket;
import Tavi007.ElementalCombat.common.network.packets.UpdateEntityDefenseLayerPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
            DatapackDataAccessor.updateItemLayer(livingEntity);
            AttackData attackData = DatapackDataAccessor.get(livingEntity);
            attackData.getLayers().forEach((rl, layer) -> {
                UpdateEntityAttackLayerPacket attackMessageToClient = new UpdateEntityAttackLayerPacket(layer, rl, livingEntity.getId());
                PacketManager.sendToAllClients(attackMessageToClient);
            });
            DefenseData defenseData = DamageCalculationHelper.get(livingEntity);
            defenseData.getLayers().forEach((rl, layer) -> {
                UpdateEntityDefenseLayerPacket defenseMessageToClient = new UpdateEntityDefenseLayerPacket(layer, rl, livingEntity.getId());
                PacketManager.sendToAllClients(defenseMessageToClient);
            });
        }
    }

    /**
     * Sends a message to all clients. The provided DefenseLayer {@link DefenseData} will be put to the entity on the client.
     * In addition the message syncs the AttackData {@link AttackData} of the {@link LivingEntity}.
     *
     * @param livingEntity     A LivingEntity.
     * @param defenseDataToAdd The DefenseData to be added to the defense values of the LivingEntity (on the client).
     */
    public static void syncDefenseLayerMessageForClients(LivingEntity livingEntity, DefenseLayer layer, ResourceLocation location) {
        if (!livingEntity.level().isClientSide) {
            DamageCalculationHelper.get(livingEntity).putLayer(location, layer);
            UpdateEntityDefenseLayerPacket defenseMessageToClient = new UpdateEntityDefenseLayerPacket(layer, location, livingEntity.getId());
            PacketManager.sendToAllClients(defenseMessageToClient);
        }
    }

    public static void syncAttackLayerMessageForClients(LivingEntity livingEntity, AttackLayer layer, ResourceLocation location) {
        if (!livingEntity.level().isClientSide) {
            DatapackDataAccessor.get(livingEntity).putLayer(location, layer);
            UpdateEntityAttackLayerPacket attackMessageToClient = new UpdateEntityAttackLayerPacket(layer, location, livingEntity.getId());
            PacketManager.sendToAllClients(attackMessageToClient);
        }
    }

    public static void syncJsonMessageForClients(Player player) {
        if (!player.level().isClientSide && player instanceof ServerPlayer) {
            SyncronizeDatapackPacket messageToClient = ElementalCombat.COMBAT_PROPERTIES_MANGER.createSyncMessage();
            PacketManager.sendToClient(messageToClient, player);
        }
    }
}
