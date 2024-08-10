package Tavi007.ElementalCombat.common.api;

import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;

public class ElementifyDamageSourceEvent extends Event {

    private final AttackData attackData;
    private final DamageSource damageSource;

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
