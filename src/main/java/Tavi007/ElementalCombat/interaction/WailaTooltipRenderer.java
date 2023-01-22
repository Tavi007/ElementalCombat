package Tavi007.ElementalCombat.interaction;

import java.awt.Dimension;

import com.mojang.blaze3d.vertex.PoseStack;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.client.CombatDataLayerClientComponent;
import Tavi007.ElementalCombat.client.CombatDataLayerComponent;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.util.AttackDataHelper;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class WailaTooltipRenderer implements ITooltipRenderer {

    @Override
    public Dimension getSize(CompoundTag data, ICommonAccessor accessor) {
        if (ClientConfig.isHWYLAActive()) {
            AttackData attackData = null;
            DefenseData defenseData = null;

            if (accessor.getEntity() != null && accessor.getEntity() instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) accessor.getEntity();
                attackData = AttackDataHelper.get(living);
                defenseData = DefenseDataHelper.get(living);
            } else if (accessor.getStack() != null) {
                ItemStack stack = accessor.getStack();
                attackData = AttackDataHelper.get(stack);
                defenseData = DefenseDataHelper.get(stack);
            }

            CombatDataLayerClientComponent clientComponent = new CombatDataLayerClientComponent(
                new CombatDataLayerComponent(
                    attackData.toLayer(),
                    defenseData.toLayer(),
                    true,
                    true,
                    ClientConfig.isDoubleRowDefenseHWYLA()));

            return new Dimension(
                clientComponent.getWidth(Minecraft.getInstance().font),
                clientComponent.getHeight());
        }
        return new Dimension();
    }

    @Override
    public void draw(PoseStack poseStack, CompoundTag data, ICommonAccessor accessor, int x, int y) {
        if (ClientConfig.isHWYLAActive()) {

            AttackData attackData;
            DefenseData defenseData;
            // check entity
            Entity entity = accessor.getEntity();
            ItemStack stack = accessor.getStack();
            if (entity != null && entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                attackData = AttackDataHelper.get(livingEntity);
                defenseData = DefenseDataHelper.get(livingEntity);
            } else if (stack != null) {
                attackData = AttackDataHelper.get(stack);
                defenseData = DefenseDataHelper.get(stack);
            } else {
                attackData = null;
                defenseData = null;
            }
            CombatDataLayerClientComponent clientComponent = new CombatDataLayerClientComponent(
                new CombatDataLayerComponent(
                    attackData.toLayer(),
                    defenseData.toLayer(),
                    true,
                    true,
                    ClientConfig.isDoubleRowDefenseHWYLA()));

            // rendering starts here
            Minecraft mc = Minecraft.getInstance();
            clientComponent.renderText(mc.font,
                poseStack,
                x,
                y); // nullpointer here
            clientComponent.renderImage(mc.font, x, y, poseStack, null, 0, null);
        }
    }

}
