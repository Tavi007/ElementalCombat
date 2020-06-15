package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.capabilities.ElementalAttackDataCapability;
import Tavi007.ElementalCombat.capabilities.ElementalDefenseDataCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ElementalCombatForgeEventBusSub 
{
	private static final ResourceLocation DEFENSE = new ResourceLocation(ElementalCombat.MOD_ID, "elemental_combat_defense");
	private static final ResourceLocation ATTACK = new ResourceLocation(ElementalCombat.MOD_ID, "elemental_combat_attack");
	
	@SubscribeEvent
    public static void onAttachEntity(AttachCapabilitiesEvent<Entity> e)
    {
        if(e.getObject().getEntity() instanceof LivingEntity) e.addCapability(DEFENSE, new ElementalDefenseDataCapability());
        if(e.getObject().getEntity() instanceof MonsterEntity) e.addCapability(ATTACK, new ElementalAttackDataCapability());
        if(e.getObject().getEntity() instanceof PlayerEntity) e.addCapability(ATTACK, new ElementalAttackDataCapability());
        if(e.getObject().getEntity() instanceof ProjectileItemEntity) e.addCapability(ATTACK, new ElementalAttackDataCapability());  
    }
}
