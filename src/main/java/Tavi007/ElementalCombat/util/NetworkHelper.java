package Tavi007.ElementalCombat.util;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.network.BasePropertiesMessage;
import Tavi007.ElementalCombat.network.EntityAttackLayerMessage;
import Tavi007.ElementalCombat.network.EntityDefenseLayerMessage;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.PacketDistributor;

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
                EntityAttackLayerMessage attackMessageToClient = new EntityAttackLayerMessage(layer, rl, livingEntity.getId());
                ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), attackMessageToClient);
            });
            DefenseData defenseData = DefenseDataHelper.get(livingEntity);
            defenseData.getLayers().forEach((rl, layer) -> {
                EntityDefenseLayerMessage defenseMessageToClient = new EntityDefenseLayerMessage(layer, rl, livingEntity.getId());
                ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), defenseMessageToClient);
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
        if (livingEntity != null && !livingEntity.level.isClientSide) {
            DefenseDataHelper.get(livingEntity).putLayer(location, layer);
            EntityDefenseLayerMessage defenseMessageToClient = new EntityDefenseLayerMessage(layer, location, livingEntity.getId());
            ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), defenseMessageToClient);
        }
    }

    public static void syncAttackLayerMessageForClients(LivingEntity livingEntity, AttackLayer layer, ResourceLocation location) {
        if (livingEntity != null && !livingEntity.level.isClientSide) {
            AttackDataHelper.get(livingEntity).putLayer(location, layer);
            ;
            EntityAttackLayerMessage attackMessageToClient = new EntityAttackLayerMessage(layer, location, livingEntity.getId());
            ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), attackMessageToClient);
        }
    }

    public static void syncJsonMessageForClients(PlayerEntity player) {
        if (player != null && !player.level.isClientSide && player instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            BasePropertiesMessage messageToClient = ElementalCombat.COMBAT_PROPERTIES_MANGER.createSyncMessage();
            ElementalCombat.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), messageToClient);
        }
    }
}
