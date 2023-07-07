package Tavi007.ElementalCombat.util;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.network.PacketManager;
import Tavi007.ElementalCombat.network.clientbound.BasePropertiesPacket;
import Tavi007.ElementalCombat.network.clientbound.EntityAttackLayerPacket;
import Tavi007.ElementalCombat.network.clientbound.EntityDefenseLayerPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class NetworkHelper {

    /**
     * Sends a message to all clients and syncronize the DefenseData {@link DefenseData} and AttackData {@link AttackData} of the {@link LivingEntity}.
     * 
     * @param livingEntity
     *            A LivingEntity.
     */
    public static void syncMessageForClients(LivingEntity livingEntity) {
        if (!livingEntity.level.isClientSide) {
            AttackDataHelper.updateItemLayer(livingEntity);
            AttackData attackData = AttackDataHelper.get(livingEntity);
            attackData.getLayers().forEach((rl, layer) -> {
                EntityAttackLayerPacket attackMessageToClient = new EntityAttackLayerPacket(layer, rl, livingEntity.getId());
                PacketManager.sendToAllClients(attackMessageToClient);
            });
            DefenseData defenseData = DefenseDataHelper.get(livingEntity);
            defenseData.getLayers().forEach((rl, layer) -> {
                EntityDefenseLayerPacket defenseMessageToClient = new EntityDefenseLayerPacket(layer, rl, livingEntity.getId());
                PacketManager.sendToAllClients(defenseMessageToClient);
            });
        }
    }

    /**
     * Sends a message to all clients. The provided DefenseLayer {@link DefenseData} will be put to the entity on the client.
     * In addition the message syncs the AttackData {@link AttackData} of the {@link LivingEntity}.
     * 
     * @param livingEntity
     *            A LivingEntity.
     * @param defenseDataToAdd
     *            The DefenseData to be added to the defense values of the LivingEntity (on the client).
     */
    public static void syncDefenseLayerMessageForClients(LivingEntity livingEntity, DefenseLayer layer, ResourceLocation location) {
        if (!livingEntity.level.isClientSide) {
            DefenseDataHelper.get(livingEntity).putLayer(location, layer);
            EntityDefenseLayerPacket defenseMessageToClient = new EntityDefenseLayerPacket(layer, location, livingEntity.getId());
            PacketManager.sendToAllClients(defenseMessageToClient);
        }
    }

    public static void syncAttackLayerMessageForClients(LivingEntity livingEntity, AttackLayer layer, ResourceLocation location) {
        if (!livingEntity.level.isClientSide) {
            AttackDataHelper.get(livingEntity).putLayer(location, layer);
            ;
            EntityAttackLayerPacket attackMessageToClient = new EntityAttackLayerPacket(layer, location, livingEntity.getId());
            PacketManager.sendToAllClients(attackMessageToClient);
        }
    }

    public static void syncJsonMessageForClients(Player player) {
        if (!player.level.isClientSide && player instanceof ServerPlayer) {
            BasePropertiesPacket messageToClient = ElementalCombat.COMBAT_PROPERTIES_MANGER.createSyncMessage();
            PacketManager.sendToClient(messageToClient, player);
        }
    }
}
