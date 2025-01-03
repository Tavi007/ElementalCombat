package Tavi007.ElementalCombat.common.events;

import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import Tavi007.ElementalCombat.common.items.LensItem;
import Tavi007.ElementalCombat.common.util.ResourceLocationAccessor;
import Tavi007.ElementalCombat.server.network.NetworkHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;

public class PlayerInputEvents {


    public static void playerLoggedIn(Player player) {
        NetworkHelper.syncJsonMessageForClients(player);
    }

    public static void onInteractEntity(Player player, Entity targetEntity, ItemStack stack) {
        if (stack.getItem() instanceof LensItem) {
            ResourceLocation rlEntity = ResourceLocationAccessor.getResourceLocation(targetEntity);
            if (targetEntity instanceof LivingEntity target) {
                player.sendSystemMessage(Component.literal("Elemental Combat properties of mob " + rlEntity));

                AttackData attackData = CapabilitiesAccessors.getAttackData(target);
                if (attackData.getLayers().isEmpty()) {
                    player.sendSystemMessage(Component.literal("Has no attack layers"));
                } else {
                    player.sendSystemMessage(Component.literal("The attack layers: "));
                    attackData.getLayers().forEach((rl, layer) -> {
                        player.sendSystemMessage(Component.literal(" - " + rl + ": " + layer));
                    });
                }
                player.sendSystemMessage(Component.literal("Resulting attack values: "));
                player.sendSystemMessage(Component.literal(attackData.toLayer().toString()));

                DefenseData defenseData = CapabilitiesAccessors.getDefenseData(target);
                if (defenseData.getLayers().isEmpty()) {
                    player.sendSystemMessage(Component.literal("Has no defense layers"));
                } else {
                    player.sendSystemMessage(Component.literal("The defense layers: "));
                    defenseData.getLayers().forEach((rl, layer) -> {
                        player.sendSystemMessage(Component.literal(" - " + rl + ": " + layer));
                    });
                }
                player.sendSystemMessage(Component.literal("Resulting defense values: "));
                player.sendSystemMessage(Component.literal(defenseData.toLayer().toString()));
            } else if (targetEntity instanceof Projectile target) {
                player.sendSystemMessage(Component.literal("Elemental Combat properties of projectile " + rlEntity));

                AttackData attackData = CapabilitiesAccessors.getAttackData(target);
                if (attackData.getLayers().isEmpty()) {
                    player.sendSystemMessage(Component.literal("Has no attack layers"));
                } else {
                    player.sendSystemMessage(Component.literal("The attack data: "));
                    attackData.getLayers().forEach((rl, layer) -> {
                        player.sendSystemMessage(Component.literal(" - " + rl + ": " + layer));
                    });
                }
                player.sendSystemMessage(Component.literal("Resulting attack values: "));
                player.sendSystemMessage(Component.literal(attackData.toLayer().toString()));
            }
        }
    }
}
