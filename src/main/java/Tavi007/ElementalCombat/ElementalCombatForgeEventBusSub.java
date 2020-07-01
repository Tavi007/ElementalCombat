package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.capabilities.ElementalAttackDataCapability;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseDataCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ElementalCombatForgeEventBusSub 
{
	private static final ResourceLocation DEFENSE = new ResourceLocation(ElementalCombat.MOD_ID, "elemental_combat/defense");
	private static final ResourceLocation ATTACK = new ResourceLocation(ElementalCombat.MOD_ID, "elemental_combat/attack");
	
	@SubscribeEvent
    public static void onAttachEntity(AttachCapabilitiesEvent<Entity> e)
    {
        if(e.getObject().getEntity() instanceof LivingEntity) e.addCapability(DEFENSE, new ElementalDefenseDataCapability());
        if(e.getObject().getEntity() instanceof LivingEntity) e.addCapability(ATTACK, new ElementalAttackDataCapability());
        if(e.getObject().getEntity() instanceof ProjectileEntity) e.addCapability(ATTACK, new ElementalAttackDataCapability());  
        
    }
	
	@SubscribeEvent
	public static void onAttachItemStack(AttachCapabilitiesEvent<ItemStack> i)
	{
        i.addCapability(ATTACK, new ElementalAttackDataCapability());
        i.addCapability(DEFENSE, new ElementalDefenseDataCapability());
	}
}
