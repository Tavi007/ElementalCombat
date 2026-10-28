package Tavi007.ElementalCombat.server.events;

import Tavi007.ElementalCombat.common.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.common.api.DatapackDataAPI;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.List;

public class GameEvents {

    public static void onEntityJoinWorld(Entity entity) {
        if (entity == null) {
            return;
        }

        // for synchronization after switching dimensions
        if (entity instanceof LivingEntity) {
            //NetworkHelper.syncMessageForClients((LivingEntity) entity);
        } else if (entity instanceof Projectile projectile && entity.tickCount == 0) {
            // fill with default values in here.
            AttackConfig projectileData = CapabilitiesAccessors.getAttackConfig(projectile);
            projectileData.put(DatapackDataAPI.getAttackConfig(projectile));
            addLayerFromSource(projectileData, projectile.getOwner());
            addLayerFromPotion(projectileData, projectile);
        }
    }

    private static void addLayerFromSource(AttackConfig config, Entity source) {
        if (source instanceof LivingEntity) {
            AttackConfig sourceData = CapabilitiesAccessors.getAttackConfig((LivingEntity) source);
            AttackProperty sourceProperty = sourceData.getSummedProperty(source, null, source.level());
            AttackLayer layer = new AttackLayer(100, sourceProperty);
            config.put(new ResourceLocation("source"), layer);
        }
    }

    private static void addLayerFromPotion(AttackConfig config, Projectile projectile) {
        if (projectile instanceof Arrow arrow) {
            // since potion is private in Arrow, write to the CompoundTag and then read it from there.
            CompoundTag compound = new CompoundTag();
            arrow.addAdditionalSaveData(compound);
            List<MobEffectInstance> effects = PotionUtils.getAllEffects(compound);

            AttackConfig potionConfig = new AttackConfig();
            int priority = 10;
            for (MobEffectInstance effect : effects) {
                AttackProperty effectProperty = BasePropertiesAPI.getAttackProperty(effect);
                AttackLayer effectLayer = new AttackLayer(priority, effectProperty);
                potionConfig.put(new ResourceLocation(effect.getDescriptionId()), effectLayer);
                priority = priority + 1;
            }
            // can not access the ItemStack from the arrow. :/
            AttackProperty potionProperty = potionConfig.getSummedProperty(arrow, null, arrow.level());
            AttackLayer potionLayer = new AttackLayer(25, potionProperty);
            config.put(new ResourceLocation("potion"), potionLayer);

        }
    }
}
