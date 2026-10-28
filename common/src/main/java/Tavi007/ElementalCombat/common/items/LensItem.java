package Tavi007.ElementalCombat.common.items;

import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackConfig;
import Tavi007.ElementalCombat.common.data.capabilities.defense.DefenseConfig;
import com.google.gson.Gson;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LensItem extends Item {

    private static final Gson GSON = new Gson();

    public LensItem(Properties builderIn) {
        super(builderIn);
    }

    public static void analyze(Player player, Entity entity) {
        if (entity instanceof LivingEntity target) {
            AttackConfig attack = CapabilitiesAccessors.getAttackConfig(target);
            String attackMessage = GSON.toJson(attack);
            player.sendSystemMessage(Component.literal(attackMessage));

            DefenseConfig defense = CapabilitiesAccessors.getDefenseConfig(target);
            String defenseMessage = GSON.toJson(attack);
            player.sendSystemMessage(Component.literal(defenseMessage));
        } else if (entity instanceof Projectile target) {
            AttackConfig attack = CapabilitiesAccessors.getAttackConfig(target);
            String attackMessage = GSON.toJson(attack);
            player.sendSystemMessage(Component.literal(attackMessage));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal(ChatFormatting.GRAY
                + "Dev Tool. Click on an mob to see its properties. "
                + "Take damage while holding the lens to see the attack properties of the damage source." + ChatFormatting.RESET));
    }

}
