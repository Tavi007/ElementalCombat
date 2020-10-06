package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.network.DefenseDataMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class ElementifySpawnEvent {

	@SubscribeEvent
	public static void entityJoinWorld(EntityJoinWorldEvent event)
	{
		if(!event.getWorld().isRemote()){ // only server side should check
			Entity entity = event.getEntity();

			// for syncronising after switching dimensions
			if (entity instanceof ServerPlayerEntity) {
				ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) entity;
				DefenseData defData = ElementalCombatAPI.getDefenseData(serverPlayerEntity);

				DefenseDataMessage messageToClient = new DefenseDataMessage(defData, serverPlayerEntity.getUniqueID());
				ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);
			}
			
			// for newly spawned projectiles.
			else if(entity instanceof ProjectileEntity){
				if(entity.ticksExisted == 0){
					ProjectileEntity projectile = (ProjectileEntity) entity;
					Entity  source = projectile.func_234616_v_();
					if(source != null && source instanceof LivingEntity){
						LivingEntity sourceEntity = (LivingEntity) source;
						// If sourceEntity holds an item, use item data.
						// If not, use data from sourceEntity as default.
						AttackData sourceData;
						if(sourceEntity.hasItemInSlot(EquipmentSlotType.MAINHAND)){
							ItemStack item = sourceEntity.getActiveItemStack();
							sourceData = ElementalCombatAPI.getAttackData(item);
						}
						else{
							sourceData = ElementalCombatAPI.getAttackData(sourceEntity);
						}

						//copy elemental attack capability
						AttackData projectileData = ElementalCombatAPI.getAttackData(projectile);
						projectileData.setElement(sourceData.getElement());
						projectileData.setStyle("projectile");
					}
				}
			}
		}
	}
}