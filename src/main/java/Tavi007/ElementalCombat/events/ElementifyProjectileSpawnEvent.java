package Tavi007.ElementalCombat.events;

import java.util.List;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.IElementalAttackData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
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
		Entity entity = event.getEntity();
		//check if entity is a new spawning projectile (only server side)
		if(entity instanceof ProjectileEntity)
		{
			if(entity.ticksExisted == 0) //need checking
			{
				System.out.println("New Projectile");
				/*
				ProjectileEntity projectile = (ProjectileEntity) entity;
				
				//find source. (aka the held item by the nearest entity, that isn't the spawning entity?)
				Vector3d pos = projectile.getPositionVec();
				//List<Entity> entities = event.getWorld().getClosestEntity(e, predicate, null, pos.x, pos.y, pos.z);
				//List<Entity> entities = event.getWorld().getEntitiesInAABBexcluding
				ItemStack source = new ItemStack();
				
				//copy elemental attack capability
				IElementalAttackData sourceData = ElementalCombatAPI.getElementalAttackData(source);
				IElementalAttackData projectileData = ElementalCombatAPI.getElementalAttackData(projectile);
				projectileData.setAttackMap(sourceData.getAttackMap());
				*/
			}
		}
	}
}
