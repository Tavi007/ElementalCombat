package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalData;
import Tavi007.ElementalCombat.ElementalDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

public class ElementalDataLoadEvent extends Event
{
    private final ResourceLocation name;
    private ElementalData elementalData;
    private ElementalDataManager elementalDataManager;

    public ElementalDataLoadEvent(ResourceLocation name, ElementalData elementalData, ElementalDataManager elementalDataManager)
    {
        this.name = name;
        this.elementalData = elementalData;
        this.elementalDataManager = elementalDataManager;
    }

    public ResourceLocation getName()
    {
        return this.name;
    }

    public ElementalData getElementalData()
    {
        return this.elementalData;
    }

    public ElementalDataManager getElementalDataManager()
    {
        return this.elementalDataManager;
    }

    public void setElementalData(ElementalData elementalData)
    {
        this.elementalData = elementalData;
    }
}