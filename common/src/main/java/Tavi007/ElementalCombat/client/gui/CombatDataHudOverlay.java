package Tavi007.ElementalCombat.client.gui;

import Tavi007.ElementalCombat.client.ClientConfig;
import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;

public class CombatDataHudOverlay {

    // might have to split this method
    public static void render(GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {

        if (ClientConfig.isHUDEnabled()) {
            // see Screen#renderToolTips in client.gui.screen
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                float scale = (float) ClientConfig.getScale();
                AttackData attackData = CapabilitiesAccessors.getAttackData(mc.player);
                DefenseData defenseData = CapabilitiesAccessors.getDefenseData(mc.player);

                CombatDataLayerComponent component = new CombatDataLayerComponent(
                        attackData.toLayer(),
                        defenseData.toLayer(),
                        true,
                        false,
                        ClientConfig.isDoubleRowDefenseHUD());

                CombatDataLayerClientComponent clientComponent = new CombatDataLayerClientComponent(component);

                // the width of the box.
                int listWidth = clientComponent.getWidth(mc.font);
                int listHeight = clientComponent.getHeight();

                // moves the coords so the text and box appear correct
                int posX = 0;
                int maxPosX = (int) (screenWidth / scale) - listWidth - 12;
                if (ClientConfig.isLeft()) {
                    posX = Math.min(12 + ClientConfig.getXOffset(), maxPosX);
                } else {
                    posX = Math.max(12, maxPosX - ClientConfig.getXOffset());
                }
                int posY = 0;
                int maxPosY = (int) (screenHeight / scale) - listHeight - 12;
                if (ClientConfig.isTop()) {
                    posY = Math.min(12 + ClientConfig.getYOffset(), maxPosY);
                } else {
                    posY = Math.max(12, maxPosY - ClientConfig.getYOffset());
                }

                // rendering starts here
                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();
                poseStack.scale(scale, scale, scale);

                // render component
                TooltipRenderUtil.renderTooltipBackground(guiGraphics, posX, posY, listWidth, listHeight, 0);
                clientComponent.renderImage(mc.font, posX, posY, guiGraphics);
                clientComponent.renderText(mc.font, guiGraphics, posX, posY);

                poseStack.popPose();
            }
        }
    }
}
