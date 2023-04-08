package Tavi007.ElementalCombat.api;

import javax.annotation.Nonnull;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * {@link ElementifyDamageSourceEvent} is fired while the
 * {@link net.minecraftforge.event.entity.living.LivingEvent.LivingHurtEvent} is running to compute the attack data for the damage calculation.
 * Use this event to add your own {@link AttackLayer} to the stack.
 * <br>
 * {@link #damageSource} contains the {@link DamageSource} of the hurt event.
 * <br>
 * <br>
 * This event is not {@link Cancelable}. <br>
 * <br>
 * This event does not have a result. {@link HasResult} <br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
public class ElementifyDamageSourceEvent extends Event {

    private AttackData attackData;
    private DamageSource damageSource;

    public ElementifyDamageSourceEvent(@Nonnull DamageSource damageSource, AttackData attackData) {
        this.damageSource = damageSource;
        this.attackData = attackData;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }

    public void addLayer(ResourceLocation resourceLocation, AttackLayer layer) {
        attackData.putLayer(resourceLocation, layer);
    }
}
