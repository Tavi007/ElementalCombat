package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.loading.DataManager;
import Tavi007.ElementalCombat.loading.EntityData;
import Tavi007.ElementalCombat.loading.GeneralData;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

public class DataLoadEvent extends Event
{
    private final ResourceLocation name;
    private GeneralData data;
    private DataManager dataManager;

    public DataLoadEvent(ResourceLocation name, GeneralData data,DataManager dataManager)
    {
        this.name = name;
        this.data = data;
        this.dataManager = dataManager;
    }

    public ResourceLocation getName()
    {
        return this.name;
    }

    public GeneralData getEntityData()
    {
        return this.data;
    }

    public DataManager getDataManager()
    {
        return this.dataManager;
    }

    public void setElementalEntityData(GeneralData data)
    {
        this.data = data;
    }
}