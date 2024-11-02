package Tavi007.ElementalCombat.server.events;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.AttackDataAPI;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import Tavi007.ElementalCombat.common.items.LensItem;
import Tavi007.ElementalCombat.common.network.CreateEmitterPacket;
import Tavi007.ElementalCombat.common.network.DisableDamageRenderPacket;
import Tavi007.ElementalCombat.common.registry.ModItems;
import Tavi007.ElementalCombat.common.util.DamageCalculationHelper;
import Tavi007.ElementalCombat.server.ServerConfig;
import Tavi007.ElementalCombat.server.network.ServerPacketSender;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CombatEvents {

//    public static void onElementifyDamageSource(ElementifyDamageSourceEvent event) {
//        DamageSource damageSource = event.getDamageSource();
//        event.addLayer(new ResourceLocation("base"), DatapackDataAccessor.getDamageTypeDefaultLayer(damageSource.type()));
//    }

    public static float elementifyDamageCalculation(LivingEntity target, DamageSource damageSource, float damageAmount) {

        // no modification. Entity should take normal damage and die eventually.
        if (damageSource == null || damageSource.is(DamageTypes.GENERIC)) {
            return damageAmount;
        }

        // Get the attack data from the damage source
        AttackData sourceData = new AttackData();
        //MinecraftForge.EVENT_BUS.post(new ElementifyDamageSourceEvent(damageSource, sourceData));

        // Get the protection data from target
        DefenseData defCap = CapabilitiesAccessors.getDefenseData(target);

        // compute new Damage value
        float defenseStyleScaling = DamageCalculationHelper.getScaling(defCap.getStyles(), sourceData.getStyle(), true);
        float defenseElementScaling = DamageCalculationHelper.getScaling(defCap.getElements(), sourceData.getElement(), false);
        float newDamageAmount = damageAmount * defenseStyleScaling * defenseElementScaling;

        // display particles
        int maxParticle = 12;
        int amountScale = 16;
        if (defenseStyleScaling < 1) {
            sendParticleMessage(target, "resistent_style", Math.min(maxParticle, 1 + Math.round((1 - defenseStyleScaling) * amountScale)));
        } else if (defenseStyleScaling > 1) {
            sendParticleMessage(target, "critical_style", Math.min(maxParticle, 1 + Math.round((defenseStyleScaling - 1) * amountScale)));
        }

        if (defenseElementScaling < 0) {
            sendParticleMessage(target, "absorb", Math.min(maxParticle, 1 + Math.round(-defenseElementScaling * amountScale)));
        } else if (defenseElementScaling >= 0 && defenseElementScaling < 1) {
            sendParticleMessage(target, "resistent_element", Math.min(maxParticle, 1 + Math.round((1 - defenseElementScaling) * amountScale)));
        } else if (defenseElementScaling > 1) {
            sendParticleMessage(target, "critical_element", Math.min(maxParticle, 1 + Math.round((defenseElementScaling - 1) * amountScale)));
        }

        // heal the target, if damage is lower than 0
        if (newDamageAmount <= 0) {
            target.heal(-newDamageAmount);
            newDamageAmount = 0;

            // send message to disable the hurt animation and sound.
            ServerPacketSender.sendPacket(new DisableDamageRenderPacket(target.getId()));

            if (newDamageAmount < 0) {
                // play a healing sound
                SoundEvent sound = SoundEvents.PLAYER_LEVELUP; // need better sound
                target.getCommandSenderWorld().playSound(null, target.blockPosition(), sound, SoundSource.MASTER, 1.0f, 2.0f);
            }
        }

        // handle lens items
        if (target.getOffhandItem().getItem() instanceof LensItem ||
                target.getMainHandItem().getItem() instanceof LensItem) {
            target.sendSystemMessage(Component.literal("Attack properties of damage source " + damageSource.getMsgId() + ":"));
            sourceData.getLayers().forEach((rl, layer) -> {
                target.sendSystemMessage(Component.literal(" - " + rl + ": " + layer));
            });
        }

        return newDamageAmount;
    }

    private static void sendParticleMessage(LivingEntity entity, String name, int amount) {
        // define message
        CreateEmitterPacket packet = new CreateEmitterPacket(entity.getId(), name, amount);

        // send message to nearby players
        ServerLevel world = (ServerLevel) entity.level();
        for (int j = 0; j < world.players().size(); ++j) {
            ServerPlayer serverPlayer = world.players().get(j);
            BlockPos blockpos = serverPlayer.blockPosition();
            if (blockpos.closerToCenterThan(entity.position(), 32.0D)) {
                ServerPacketSender.sendPacket(packet, serverPlayer);
            }
        }
    }

    public static ItemEntity getEssenceItemDrops(LivingEntity entity, int lootingLevel) {
        if (entity instanceof Player) {
            return null;
        }

        int numberOfDrops = (int) Math.round(Math.random() * ServerConfig.getEssenceSpawnWeight() * (1 + lootingLevel));
        if (numberOfDrops < 1) {
            return null;
        }

        AttackLayer layer = AttackDataAPI.getFullDataAsLayer(entity);
        Item item = getEssenceItem(layer.getElement());
        if (item == null) {
            return null;
        }

        return new ItemEntity(entity.getCommandSenderWorld(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(item, numberOfDrops));
    }

    private static Item getEssenceItem(String element) {
        switch (element) {
            case Constants.FIRE:
                return ModItems.ESSENCE_FIRE.get();
            case Constants.ICE:
                return ModItems.ESSENCE_ICE.get();
            case Constants.WATER:
                return ModItems.ESSENCE_WATER.get();
            case Constants.THUNDER:
                return ModItems.ESSENCE_THUNDER.get();
            case Constants.DARKNESS:
                return ModItems.ESSENCE_DARKNESS.get();
            case Constants.LIGHT:
                return ModItems.ESSENCE_LIGHT.get();
            case Constants.EARTH:
                return ModItems.ESSENCE_EARTH.get();
            case Constants.WIND:
                return ModItems.ESSENCE_WIND.get();
            case Constants.FLORA:
                return ModItems.ESSENCE_FLORA.get();
            default:
                return null;
        }
    }
}
