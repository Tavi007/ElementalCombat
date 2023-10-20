package Tavi007.ElementalCombat.events;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.init.StartupClientOnly;
import Tavi007.ElementalCombat.util.AttackDataHelper;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import Tavi007.ElementalCombat.util.NetworkHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ShieldItem;
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
            if (entity.getOffhandItem().getItem() instanceof ShieldItem) {
                defenseLayer.addLayer(DefenseDataHelper.get(entity.getOffhandItem()).toLayer());
            }
            if (entity.getMainHandItem().getItem() instanceof ShieldItem) {
                defenseLayer.addLayer(DefenseDataHelper.get(entity.getOffhandItem()).toLayer());
            }
            if (!defenseLayer.isEmpty()) {
                DefenseDataAPI.putLayer(entity, defenseLayer, new ResourceLocation("hands"));
            }
            AttackDataHelper.updateItemLayer(entity);
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
}
