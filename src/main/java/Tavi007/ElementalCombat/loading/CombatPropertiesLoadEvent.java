package Tavi007.ElementalCombat.loading;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

public class CombatPropertiesLoadEvent extends Event
{
    private final ResourceLocation name;
    private GeneralCombatProperties data;
    private CombatPropertiesManager combatPropertiesManager;

    public CombatPropertiesLoadEvent(ResourceLocation name, GeneralCombatProperties data, CombatPropertiesManager combatPropertiesManager)
    {
        this.name = name;
        this.data = data;
        this.combatPropertiesManager = combatPropertiesManager;
    }

    public ResourceLocation getName()
    {
        return this.name;
    }

    public GeneralCombatProperties getEntityData()
    {
        return this.data;
    }

    public CombatPropertiesManager getCombatPropertiesManager()
    {
        return this.combatPropertiesManager;
    }

    public void setElementalEntityData(GeneralCombatProperties data)
    {
        this.data = data;
    }
}