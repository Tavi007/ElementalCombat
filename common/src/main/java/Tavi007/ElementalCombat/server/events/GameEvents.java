package Tavi007.ElementalCombat.server.events;

import Tavi007.ElementalCombat.common.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.common.api.DatapackDataAPI;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackBranch;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackTree;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameEvents {

    public static void onEntityJoinWorld(Entity entity) {
        if (entity == null) {
            return;
        }

        // for synchronization after switching dimensions
        if (entity instanceof LivingEntity) {
            //TODO:
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
            Queue<ResourceLocation> treePath = new LinkedList<>();
            treePath.add(new ResourceLocation("source"));
            AttackBranch branch = new AttackBranch(sourceData.tree(), 50);
            config.put(treePath, branch);
        }
    }

    private static void addLayerFromPotion(AttackConfig config, Projectile projectile) {
        if (projectile instanceof Arrow arrow) {
            // since potion is private in Arrow, write to the CompoundTag and then read it from there.
            CompoundTag compound = new CompoundTag();
            arrow.addAdditionalSaveData(compound);
            List<MobEffectInstance> effects = PotionUtils.getAllEffects(compound);

            // create Tree of the potion effects
            AttackTree potionTree = new AttackTree();
            int priority = 10;
            for (MobEffectInstance effect : effects) {
                ResourceLocation location = new ResourceLocation(effect.getDescriptionId());
                AttackProperty effectProperty = BasePropertiesAPI.getAttackProperty(effect);
                AttackBranch branch = new AttackBranch(effectProperty, priority);

                potionTree.put(location, branch);
                priority = priority + 1; // TODO: maybe rethink priority here. Does the order of adding enchantments matter?
            }

            // add this tree to entities AttackConfig
            Queue<ResourceLocation> treePath = new LinkedList<>();
            treePath.add(new ResourceLocation("potions"));
            AttackBranch branch = new AttackBranch(potionTree, 50);
            config.put(treePath, branch);

        }
    }
}
