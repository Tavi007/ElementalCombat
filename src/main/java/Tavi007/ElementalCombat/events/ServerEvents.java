package Tavi007.ElementalCombat.events;

import java.util.function.Supplier;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.network.CreateEmitterMessage;
import Tavi007.ElementalCombat.network.DisableDamageRenderMessage;
import Tavi007.ElementalCombat.network.ServerPlayerSupplier;
import Tavi007.ElementalCombat.util.AttackDataHelper;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import Tavi007.ElementalCombat.util.NetworkHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(ElementalCombat.COMBAT_PROPERTIES_MANGER);
        ElementalCombat.LOGGER.info("ReloadListener for combat data registered.");
    }

    @SubscribeEvent
    public static void entityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide()) { // only server side should check
            Entity entity = event.getEntity();

            // for synchronization after switching dimensions
            if (entity instanceof LivingEntity) {
                NetworkHelper.syncMessageForClients((LivingEntity) entity);
            } else if (entity instanceof Projectile && entity.tickCount == 0) {
                // fill with default values in here.
                Projectile projectile = (Projectile) entity;
                AttackData projectileData = AttackDataHelper.get(projectile);
                projectileData.putLayer(new ResourceLocation("base"), BasePropertiesAPI.getAttackData(projectile));
                addLayerFromSource(projectileData, projectile.getOwner());
                addLayerFromPotion(projectileData, projectile);
            }
        }
    }

    private static void addLayerFromSource(AttackData projectileData, Entity source) {
        if (source != null && source instanceof LivingEntity) {
            AttackData sourceData = AttackDataHelper.get((LivingEntity) source);
            projectileData.putLayer(new ResourceLocation("mob"), sourceData.toLayer());
        }
    }

    private static void addLayerFromPotion(AttackData projectileData, Projectile projectile) {
        if (projectile instanceof ArrowEntity) {
            CompoundNBT compound = new CompoundNBT();
            ((ArrowEntity) projectile).addAdditionalSaveData(compound);
            if (compound.contains("Potion", 8)) {
                AttackData potionLayer = new AttackData();
                PotionUtils.getAllEffects(compound).forEach(effect -> {
                    potionLayer.putLayer(new ResourceLocation(effect.getDescriptionId()), BasePropertiesAPI.getAttackLayer(effect));
                });
                projectileData.putLayer(new ResourceLocation("potion"), potionLayer.toLayer());
            }
        }
    }

    @SubscribeEvent
    public static void elementifyLivingHurtEvent(LivingHurtEvent event) {
        DamageSource damageSource = event.getSource();

        // no modification. Entity should take normal damage and die eventually.
        if (damageSource == DamageSource.OUT_OF_WORLD) {
            return;
        }

        // compute new Damage value
        AttackData sourceData = AttackDataHelper.get(damageSource);
        LivingEntity target = event.getEntityLiving();
        float damageAmount = event.getAmount();
        // Get the protection data from target
        DefenseData defCap = DefenseDataHelper.get(target);
        float defenseStyleScaling = DefenseDataHelper.getScaling(defCap.getStyleFactor(), sourceData.getStyle(), true);
        float defenseElementScaling = DefenseDataHelper.getScaling(defCap.getElementFactor(), sourceData.getElement(), false);
        damageAmount = (float) (damageAmount * defenseStyleScaling * defenseElementScaling);

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
        if (damageAmount <= 0) {
            target.heal(-damageAmount);
            event.setCanceled(true);
            damageAmount = 0;

            // send message to disable the hurt animation and sound.
            DisableDamageRenderMessage messageToClient = new DisableDamageRenderMessage(target.getId());
            ElementalCombat.simpleChannel.send(PacketDistributor.ALL.noArg(), messageToClient);

            // play a healing sound
            SoundEvent sound = SoundEvents.PLAYER_LEVELUP; // need better sound
            target.getCommandSenderWorld().playSound(null, target.blockPosition(), sound, SoundCategory.MASTER, 1.0f, 2.0f);
        }

        event.setAmount(damageAmount);
    }

    private static void sendParticleMessage(LivingEntity entity, String name, int amount) {
        // define message
        CreateEmitterMessage messageToClient = new CreateEmitterMessage(entity.getId(), name, amount);

        // send message to nearby players
        ServerWorld world = (ServerWorld) entity.level;
        for (int j = 0; j < world.players().size(); ++j) {
            ServerPlayer serverplayerentity = world.players().get(j);
            BlockPos blockpos = serverplayerentity.blockPosition();
            if (blockpos.closerThan(entity.position(), 32.0D)) {
                Supplier<ServerPlayerEntity> supplier = new ServerPlayerSupplier(serverplayerentity);
                ElementalCombat.simpleChannel.send(PacketDistributor.PLAYER.with(supplier), messageToClient);
            }
        }
    }

}
