package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.GainDefenseFromEquipmentEvent;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.init.StartupClientOnly;
import Tavi007.ElementalCombat.items.LensItem;
import Tavi007.ElementalCombat.util.AttackDataHelper;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import Tavi007.ElementalCombat.util.NetworkHelper;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {

    @SubscribeEvent
    public static void livingEquipmentChange(LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity == null) {
            return;
        }

        if (EquipmentSlot.MAINHAND.equals(event.getSlot())) {
            AttackDataHelper.updateItemLayer(entity);
        }

        GainDefenseFromEquipmentEvent gainDefenseEvent = new GainDefenseFromEquipmentEvent(
            event.getTo(),
            event.getSlot());
        MinecraftForge.EVENT_BUS.post(gainDefenseEvent);
        if (!gainDefenseEvent.isCanceled()) {
            DefenseDataAPI.putLayer(
                entity,
                DefenseDataHelper.get(event.getTo()).toLayer(),
                new ResourceLocation(event.getSlot().name().toLowerCase()));
        }
    }

    @SubscribeEvent
    public static void gainDefenseFromEquipmentEvent(GainDefenseFromEquipmentEvent event) {
        ItemStack stack = event.getItemStack();
        if (!Potions.EMPTY.equals(PotionUtils.getPotion(stack))) {
            event.setCanceled(true);
            return;
        }
        if (EquipmentSlot.Type.HAND.equals(event.getEquipmentSlot().getType())) {
            Item item = stack.getItem();
            event.setCanceled(item instanceof ArmorItem ||
                item instanceof EnchantedBookItem);
        }
    }

    @SubscribeEvent
    public static void playerLoggedIn(PlayerLoggedInEvent event) {
        NetworkHelper.syncJsonMessageForClients(event.getPlayer());
    }

    @SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {
        if (StartupClientOnly.TOGGLE_HUD.isDown()) {
            ClientConfig.toogleHUD();
        }
    }

    @SubscribeEvent
    public static void onInteractEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();
        if (event.getItemStack().getItem() instanceof LensItem) {
            if (event.getTarget() instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) event.getTarget();
                player.sendMessage(new TextComponent("Elemental Combat properties of mob " + target.getType().getRegistryName()), player.getUUID());

                AttackData attackData = AttackDataHelper.get(target);
                if (attackData.getLayers().isEmpty()) {
                    player.sendMessage(new TextComponent("Has no attack layers"), player.getUUID());
                } else {
                    player.sendMessage(new TextComponent("The attack layers: "), player.getUUID());
                    attackData.getLayers().forEach((rl, layer) -> {
                        player.sendMessage(new TextComponent(" - " + rl + ": " + layer), player.getUUID());
                    });
                }
                player.sendMessage(new TextComponent("Resulting attack values: "), player.getUUID());
                player.sendMessage(new TextComponent(attackData.toLayer().toString()), player.getUUID());

                DefenseData defenseData = DefenseDataHelper.get(target);
                if (defenseData.getLayers().isEmpty()) {
                    player.sendMessage(new TextComponent("Has no defense layers"), player.getUUID());
                } else {
                    player.sendMessage(new TextComponent("The defense layers: "), player.getUUID());
                    defenseData.getLayers().forEach((rl, layer) -> {
                        player.sendMessage(new TextComponent(" - " + rl + ": " + layer), player.getUUID());
                    });
                }
                player.sendMessage(new TextComponent("Resulting defense values: "), player.getUUID());
                player.sendMessage(new TextComponent(defenseData.toLayer().toString()), player.getUUID());
            } else if (event.getTarget() instanceof Projectile) {
                Projectile target = (Projectile) event.getTarget();
                player.sendMessage(new TextComponent("Elemental Combat properties of projectile " + target.getType().getRegistryName()),
                    player.getUUID());

                AttackData attackData = AttackDataHelper.get(target);
                if (attackData.getLayers().isEmpty()) {
                    player.sendMessage(new TextComponent("Has no attack layers"), player.getUUID());
                } else {
                    player.sendMessage(new TextComponent("The attack data: "), player.getUUID());
                    attackData.getLayers().forEach((rl, layer) -> {
                        player.sendMessage(new TextComponent(" - " + rl + ": " + layer), player.getUUID());
                    });
                }
                player.sendMessage(new TextComponent("Resulting attack values: "), player.getUUID());
                player.sendMessage(new TextComponent(attackData.toLayer().toString()), player.getUUID());
            }
        }
    }
}
