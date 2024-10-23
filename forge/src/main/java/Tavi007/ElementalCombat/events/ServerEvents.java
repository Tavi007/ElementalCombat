package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.ElementalCombat;
import Tavi007.ElementalCombat.server.events.CombatEvents;
import Tavi007.ElementalCombat.server.events.GameEvents;
import Tavi007.ElementalCombat.server.events.PotionEvents;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(ElementalCombat.COMBAT_PROPERTIES_MANGER);
        Constants.LOG.info("ReloadListener for combat data registered.");
    }

    @SubscribeEvent
    public static void entityJoinWorld(EntityJoinLevelEvent event) {
        GameEvents.onEntityJoinWorld(event.getEntity());
    }

    @SubscribeEvent
    public static void onCheckPotionEvent(MobEffectEvent.Applicable event) {
        PotionEvents.adjustPotionEffect(event.getEntity(), event.getEffectInstance());
    }

    @SubscribeEvent
    public static void onAddPotionEvent(MobEffectEvent.Added event) {
        PotionEvents.onAddPotionEvent(event.getEntity(), event.getEffectInstance());
    }

    @SubscribeEvent
    public static void onRemovePotionEvent(MobEffectEvent.Remove event) {
        PotionEvents.onRemovePotionEvent(event.getEntity(), event.getEffectInstance());
    }

    @SubscribeEvent
    public static void onExpirePotionEvent(MobEffectEvent.Expired event) {
        PotionEvents.onRemovePotionEvent(event.getEntity(), event.getEffectInstance());
    }

//    @SubscribeEvent(priority = EventPriority.HIGHEST)
//    public static void onElementifyDamageSource(ElementifyDamageSourceEvent event) {
//        DamageSource damageSource = event.getDamageSource();
//        event.addLayer(new ResourceLocation("base"), DatapackDataAccessor.getDamageTypeDefaultLayer(damageSource.type()));
//    }

    @SubscribeEvent
    public static void elementifyLivingHurtEvent(LivingHurtEvent event) {
        float damageAmount = CombatEvents.elementifyDamageCalculation(event.getEntity(), event.getSource(), event.getAmount());
        event.setAmount(damageAmount);
    }

    @SubscribeEvent
    public static void onLivingDropsEvent(LivingDropsEvent event) {
        event.getDrops().add(CombatEvents.getEssenceItemDrops(event.getEntity(), event.getLootingLevel()));
    }

}
