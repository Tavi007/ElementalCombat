package Tavi007.ElementalCombat.events;

import java.util.List;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.IElementalAttackData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
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
			if(entity instanceof ProjectileEntity){
				if(entity.ticksExisted == 0){
					System.out.println("Newly spawned projectile found");
					
					ProjectileEntity projectile = (ProjectileEntity) entity;
					
					//find livingEntity source. (aka the nearest LivingEntity, that isn't the spawning projectileEntity? What about dispensers?)
					Vector3d pos = projectile.getPositionVec();
					AxisAlignedBB boundingBox = new AxisAlignedBB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z); 
					List<Entity> nearbyEntities = event.getWorld().getEntitiesWithinAABBExcludingEntity(projectile, boundingBox);
					System.out.println("Entity in List: "+ nearbyEntities);
					
					//check if only one entity was found. This should be the sourceEntity
					//if List is empty, then source is a TileEntity (i.e Dispenser). 
					if(nearbyEntities.size() == 1)
					{
						LivingEntity sourceEntity = (LivingEntity) nearbyEntities.get(0);
						
						// If sourceEntity holds an item, use item data.
						// If not, use data from sourceEntity as default.
						IElementalAttackData sourceData;
						if(sourceEntity.hasItemInSlot(EquipmentSlotType.MAINHAND)){
							ItemStack item = sourceEntity.getActiveItemStack();
							sourceData = ElementalCombatAPI.getElementalAttackData(item);
						}
						else{
							sourceData = ElementalCombatAPI.getElementalAttackData(sourceEntity);
						}
						
						//copy elemental attack capability
						IElementalAttackData projectileData = ElementalCombatAPI.getElementalAttackData(projectile);
						projectileData.setAttackMap(sourceData.getAttackMap());
					}
				}
			}
		}
	}
}
