package Tavi007.ElementalCombat.client;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.util.AttackDataHelper;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Collections;
import java.util.Optional;

public class CombatDataHudOverlay implements IGuiOverlay {

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {

        if (ClientConfig.isHUDEnabled()) {
            // see Screen#renderToolTips in client.gui.screen
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                float scale = (float) ClientConfig.scale();
                AttackData attackData = AttackDataHelper.get(mc.player);
                DefenseData defenseData = DefenseDataHelper.get(mc.player);

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
