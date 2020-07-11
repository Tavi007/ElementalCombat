package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.CapabilityProviderEntityAndItem;
import Tavi007.ElementalCombat.capabilities.CapabilityProviderProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AttachCapabilityEvent 
{
	private static final ResourceLocation ENTITY = new ResourceLocation(ElementalCombat.MOD_ID, "entity");
	private static final ResourceLocation PROJECTILE = new ResourceLocation(ElementalCombat.MOD_ID, "projectile");
	private static final ResourceLocation ITEM = new ResourceLocation(ElementalCombat.MOD_ID, "item");
	
	@SubscribeEvent
    public static void onAttachEntity(AttachCapabilitiesEvent<Entity> e)
    {
        if(e.getObject().getEntity() instanceof LivingEntity) e.addCapability(ENTITY, new CapabilityProviderEntityAndItem());
        if(e.getObject().getEntity() instanceof ProjectileItemEntity) e.addCapability(PROJECTILE, new CapabilityProviderProjectile());  
        
    }
	
	@SubscribeEvent
	public static void onAttachItemStack(AttachCapabilitiesEvent<ItemStack> i)
	{
		i.addCapability(ITEM, new CapabilityProviderEntityAndItem());
	}
}
