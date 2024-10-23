package Tavi007.ElementalCombat.server.events;

import Tavi007.ElementalCombat.common.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.common.api.DatapackDataAPI;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.alchemy.PotionUtils;

public class GameEvents {

//  TODO: fix reload listener

//    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
//        event.addListener(ElementalCombat.COMBAT_PROPERTIES_MANGER);
//        Constants.LOG.info("ReloadListener for combat data registered.");
//    }


    public static void onEntityJoinWorld(Entity entity) {
        if (entity == null) {
            return;
        }

        // for synchronization after switching dimensions
        if (entity instanceof LivingEntity) {
            //NetworkHelper.syncMessageForClients((LivingEntity) entity);
        } else if (entity instanceof Projectile projectile && entity.tickCount == 0) {
            // fill with default values in here.
            AttackData projectileData = CapabilitiesAccessors.getAttackData(projectile);
            projectileData.putLayer(new ResourceLocation("base"), DatapackDataAPI.getAttackLayer(projectile));
            addLayerFromSource(projectileData, projectile.getOwner());
            addLayerFromPotion(projectileData, projectile);
        }
    }

    private static void addLayerFromSource(AttackData projectileData, Entity source) {
        if (source != null && source instanceof LivingEntity) {
            AttackData sourceData = CapabilitiesAccessors.getAttackData((LivingEntity) source);
            projectileData.putLayer(new ResourceLocation("mob"), sourceData.toLayer());
        }
    }

    private static void addLayerFromPotion(AttackData projectileData, Projectile projectile) {
        if (projectile != null && projectile instanceof Arrow) {
            CompoundTag compound = new CompoundTag();
            ((Arrow) projectile).addAdditionalSaveData(compound);
            if (compound.contains("Potion", 8)) {
                AttackData potionLayer = new AttackData();
                PotionUtils.getAllEffects(compound).forEach(effect -> {
                    potionLayer.putLayer(new ResourceLocation(effect.getDescriptionId()), BasePropertiesAPI.getAttackLayer(effect));
                });
                projectileData.putLayer(new ResourceLocation("potion"), potionLayer.toLayer());
            }
        }
    }
}
