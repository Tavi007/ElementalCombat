package Tavi007.ElementalCombat.api;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.network.EntityCombatDataMessage;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.network.PacketDistributor;

public class NetworkAPI {

	public static void syncMessageForClients(LivingEntity livingEntity) {
		if(livingEntity.isServerWorld()) {
			AttackData attackData = AttackDataAPI.get(livingEntity);
			DefenseData defenseData = DefenseDataAPI.get(livingEntity);
			EntityCombatDataMessage messageToClient = new EntityCombatDataMessage(attackData, defenseData, false, livingEntity.getEntityId());
			ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);
		}
	}

	public static void addDefenseMessageForClients(LivingEntity livingEntity, DefenseData defenseDataToAdd) {
		if(livingEntity.isServerWorld()) {
			AttackData attackData = AttackDataAPI.get(livingEntity);
			EntityCombatDataMessage messageToClient = new EntityCombatDataMessage(attackData, defenseDataToAdd, true, livingEntity.getEntityId());
			ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);
		}
	}

	public static void setAttackMessageForClients(LivingEntity livingEntity, AttackData AttackDataToSet) {
		AttackDataAPI.get(livingEntity).set(AttackDataToSet);
		addDefenseMessageForClients(livingEntity, new DefenseData());
	}
}
