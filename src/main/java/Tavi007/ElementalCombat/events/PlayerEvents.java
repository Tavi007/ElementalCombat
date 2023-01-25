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
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {

    @SubscribeEvent
    public static void livingEquipmentChange(LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntity();
        // change defense properties
        switch (event.getSlot().getType()) {
        case ARMOR:
            DefenseLayer defenseLayer = new DefenseLayer();
            entity.getArmorSlots().forEach(stack -> {
                DefenseData data = DefenseDataHelper.get(stack);
                defenseLayer.addLayer(data.toLayer());
            });
            DefenseDataAPI.putLayer(entity, defenseLayer, new ResourceLocation("armor"));
        case HAND:
            AttackDataHelper.updateItemLayer(entity);
        }
    }

    // every armor piece (curios and vanilla) will be re-applied on log in
    // same goes for any auras (WIP)
    // this is why, all this stuff need to be removed on log out.
    @SubscribeEvent
    public static void playerLoggedOut(PlayerLoggedOutEvent event) {
        Player entity = event.getEntity();
        if (entity != null) {
            DefenseData defCapEntity = DefenseDataHelper.get(entity);
            defCapEntity.clear();
        }
    }

    @SubscribeEvent
    public static void playerLoggedIn(PlayerLoggedInEvent event) {
        NetworkHelper.syncJsonMessageForClients(event.getEntity());
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (StartupClientOnly.TOGGLE_HUD.isDown()) {
            ClientConfig.toogleHUD();
        }
    }
}
