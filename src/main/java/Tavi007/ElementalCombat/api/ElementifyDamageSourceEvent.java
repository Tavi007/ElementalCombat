package Tavi007.ElementalCombat.api;

import javax.annotation.Nonnull;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.eventbus.api.Event;

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
