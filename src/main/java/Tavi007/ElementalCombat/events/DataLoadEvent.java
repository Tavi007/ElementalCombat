package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.EntityData;
import Tavi007.ElementalCombat.DataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

public class DataLoadEvent extends Event
{
    private final ResourceLocation name;
    private EntityData entityData;
    private DataManager dataManager;

    public DataLoadEvent(ResourceLocation name, EntityData entityData,DataManager dataManager)
    {
        this.name = name;
        this.entityData = entityData;
        this.dataManager = dataManager;
    }

    public ResourceLocation getName()
    {
        return this.name;
    }

    public EntityData getEntityData()
    {
        return this.entityData;
    }

    public DataManager getDataManager()
    {
        return this.dataManager;
    }

    public void setElementalEntityData(EntityData entityData)
    {
        this.entityData = entityData;
    }
}