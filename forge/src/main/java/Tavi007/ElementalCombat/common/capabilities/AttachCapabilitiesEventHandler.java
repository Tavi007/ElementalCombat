package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class AttachCapabilitiesEventHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {
        Object object = event.getObject();
        if (object instanceof LivingEntity) {
            if (!event.getCapabilities().containsKey(Constants.ATTACK_DATA_CAPABILITY_MOB)) {
                final AttackConfig attackData = new AttackConfig();
                event.addCapability(Constants.ATTACK_DATA_CAPABILITY_MOB,
                        AttackConfigCapability.createProvider(attackData));
            }
            if (!event.getCapabilities().containsKey(Constants.DEFENSE_DATA_CAPABILITY_MOB)) {
                final DefenseConfig defenseData = new DefenseConfig();
                event.addCapability(Constants.DEFENSE_DATA_CAPABILITY_MOB,
                        DefenseConfigCapability.createProvider(defenseData));
            }

            if (!event.getCapabilities().containsKey(Constants.IMMERSION_DATA_CAPABILITY)) {
                final ImmersionData immersionData = new ImmersionData();
                event.addCapability(Constants.IMMERSION_DATA_CAPABILITY,
                        ImmersionDataCapability.createProvider(immersionData));
            }
        }
        if (object instanceof Projectile) {
            if (!event.getCapabilities().containsKey(Constants.ATTACK_DATA_CAPABILITY_MOB)) {
                final AttackConfig attackData = new AttackConfig();
                event.addCapability(Constants.ATTACK_DATA_CAPABILITY_MOB,
                        AttackConfigCapability.createProvider(attackData));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
        if (!event.getCapabilities().containsKey(Constants.ATTACK_DATA_CAPABILITY_ITEM)) {
            final AttackConfig attackData = new AttackConfig();
            event.addCapability(Constants.ATTACK_DATA_CAPABILITY_ITEM,
                    AttackConfigCapability.createProvider(attackData));
        }
        if (!event.getCapabilities().containsKey(Constants.DEFENSE_DATA_CAPABILITY_MOB)) {
            final DefenseConfig defenseData = new DefenseConfig();
            event.addCapability(Constants.DEFENSE_DATA_CAPABILITY_MOB,
                    DefenseConfigCapability.createProvider(defenseData));
        }
    }
}
