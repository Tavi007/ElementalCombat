package Tavi007.ElementalCombat.api;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.api.defense.DefenseLayer;
import Tavi007.ElementalCombat.network.EntityCombatDataMessage;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.network.PacketDistributor;

public class NetworkAPI {

	/**
	 * Sends a message to all clients and syncronize the DefenseData {@link DefenseData} and AttackData {@link AttackData} of the {@link LivingEntity}.  
	 * @param livingEntity A LivingEntity.
	 */
	public static void syncMessageForClients(LivingEntity livingEntity) {
		if(livingEntity.isServerWorld()) {
			AttackData attackData = AttackDataAPI.get(livingEntity);
			DefenseData defenseData = DefenseDataAPI.get(livingEntity);
			EntityCombatDataMessage messageToClient = new EntityCombatDataMessage(attackData, defenseData, false, livingEntity.getEntityId());
			ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);
		}
	}


	/**
	 * Sends a message to all clients. The provided DefenseData {@link DefenseData} will be added to the defense of the entity on the client.
	 * In addition the message syncs the AttackData {@link AttackData} of the {@link LivingEntity}.  
	 * @param livingEntity A LivingEntity.
	 * @param defenseDataToAdd The DefenseData to be added to the defense values of the LivingEntity (on the client).
	 */
	public static void addDefenseLayerMessageForClients(LivingEntity livingEntity, DefenseLayer layerToAdd, String name) {
		if(livingEntity.isServerWorld()) {
			AttackData attackData = AttackDataAPI.get(livingEntity);
			EntityCombatDataMessage messageToClient = new EntityCombatDataMessage(attackData, defenseDataToAdd, true, livingEntity.getEntityId());
			ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);
		}
	}


	/**
	 * Set the attack-combat data {@link AttackData} of the {@link LivingEntity} and sends a message to all clients for syncronization.
	 * @param livingEntity A LivingEntity.
	 * @param attackDataToSet The AttackData to be set as the attack values of the LivingEntity.
	 */
	public static void syncAttackMessageForClients(LivingEntity livingEntity, AttackData AttackDataToSet) {
		AttackDataAPI.get(livingEntity).set(AttackDataToSet);
		addDefenseMessageForClients(livingEntity, new DefenseData());
	}
}
