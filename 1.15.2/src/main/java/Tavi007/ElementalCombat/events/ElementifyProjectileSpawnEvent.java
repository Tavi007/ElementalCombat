package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.ElementalAttack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class ElementifyProjectileSpawnEvent {

	@SubscribeEvent
	public static void elementifyProjectileSpawnEvent(EntityJoinWorldEvent event)
	{
		if(!event.getWorld().isRemote()){ // only server side should check
			Entity entity = event.getEntity();
			//check if entity is a new spawning projectile.
			if(entity instanceof ProjectileItemEntity){
				if(entity.ticksExisted == 0){
					System.out.println("Newly spawned projectile found");
					
					ProjectileItemEntity projectile = (ProjectileItemEntity) entity;
					Entity  source = projectile.getThrower();
					if(source != null && source instanceof LivingEntity){
						LivingEntity sourceEntity = (LivingEntity) source;
						
						System.out.println("Shooter: " +sourceEntity.getDisplayName().getString());
						
						// If sourceEntity holds an item, use item data.
						// If not, use data from sourceEntity as default.
						ElementalAttack sourceData;
						if(sourceEntity.hasItemInSlot(EquipmentSlotType.MAINHAND)){
							ItemStack item = sourceEntity.getActiveItemStack();
							sourceData = ElementalCombatAPI.getElementalAttackData(item);
						}
						else{
							sourceData = ElementalCombatAPI.getElementalAttackData(sourceEntity);
						}
						
						//copy elemental attack capability
						ElementalAttack projectileData = ElementalCombatAPI.getElementalAttackData(projectile);
						projectileData.setAttackMap(sourceData.getAttackMap());
					}
				}
			}
		}
	}
}
