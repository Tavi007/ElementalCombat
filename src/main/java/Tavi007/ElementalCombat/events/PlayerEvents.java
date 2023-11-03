package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.init.StartupClientOnly;
import Tavi007.ElementalCombat.interaction.HandleCuriosInventory;
import Tavi007.ElementalCombat.util.AttackDataHelper;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import Tavi007.ElementalCombat.util.NetworkHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
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
        // change defense properties
        DefenseLayer defenseLayer = new DefenseLayer();
        switch (event.getSlot().getType()) {
        case ARMOR:
            entity.getArmorSlots().forEach(stack -> {
                DefenseData data = DefenseDataHelper.get(stack);
                defenseLayer.addLayer(data.toLayer());
            });
            DefenseDataAPI.putLayer(entity, defenseLayer, new ResourceLocation("armor"));
        case HAND:
            if (canGiveDefenseFromHolding(entity.getOffhandItem())) {
                defenseLayer.addLayer(DefenseDataHelper.get(entity.getOffhandItem()).toLayer());
            }
            if (canGiveDefenseFromHolding(entity.getMainHandItem())) {
                defenseLayer.addLayer(DefenseDataHelper.get(entity.getMainHandItem()).toLayer());
            }
            DefenseDataAPI.putLayer(entity, defenseLayer, new ResourceLocation("hands"));
            AttackDataHelper.updateItemLayer(entity);
        }
    }

    private static boolean canGiveDefenseFromHolding(ItemStack stack) {
        Item item = stack.getItem();
        return !(item instanceof ArmorItem ||
            item instanceof PotionItem ||
            item instanceof EnchantedBookItem ||
            (ElementalCombat.isCuriosLoaded() && HandleCuriosInventory.isCurioItem(stack)));
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
}
