package Tavi007.ElementalCombat.client.gui;

import Tavi007.ElementalCombat.client.ClientConfigAccessors;
import Tavi007.ElementalCombat.client.data.HudData;
import Tavi007.ElementalCombat.client.data.TextureData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.util.Tuple;

import java.util.List;

import static Tavi007.ElementalCombat.client.data.ClientDataHandler.getHudData;
import static Tavi007.ElementalCombat.client.gui.GuiHelper.renderTextureData;

public class ElementalCombatHud {

    public static void render(GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {

//        increaseTickCounter();

        if (ClientConfigAccessors.isHUDEnabled()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                float scale = (float) ClientConfigAccessors.getScale();
//                AttackData attackData = CapabilitiesAccessors.getAttackData(mc.player);
//                DefenseData defenseData = CapabilitiesAccessors.getDefenseData(mc.player);

                // TODO: call with correct data. For now uses test values
                HudData data = getHudData(null, null);

                // the width of the box. Compute once for efficiency
                int listWidth = data.getWidth();
                int listHeight = data.getHeight();

                Tuple<Integer, Integer> pos = getPosition(screenWidth, screenHeight, listWidth, listHeight);
                int posX = pos.getA();
                int posY = pos.getB();


                // rendering
                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();
                poseStack.scale(scale, scale, scale);
                TooltipRenderUtil.renderTooltipBackground(guiGraphics, posX, posY, listWidth, listHeight, 0);
                List<TextureData> textures = data.getTextureData(posX, posY);
                textures.forEach(texture -> {
                    renderTextureData(mc.font, guiGraphics, texture);
                });
                poseStack.popPose();
            }
        }
    }

    private static Tuple<Integer, Integer> getPosition(int screenWidth, int screenHeight, int listWidth, int listHeight) {
        float scale = (float) ClientConfigAccessors.getScale();

        int posX = 0;
        int maxPosX = (int) (screenWidth / scale) - listWidth - 12;
        if (ClientConfigAccessors.isLeft()) {
            posX = Math.min(12 + ClientConfigAccessors.getXOffset(), maxPosX);
        } else {
            posX = Math.max(12, maxPosX - ClientConfigAccessors.getXOffset());
        }

        int posY = 0;
        int maxPosY = (int) (screenHeight / scale) - listHeight - 12;
        if (ClientConfigAccessors.isTop()) {
            posY = Math.min(12 + ClientConfigAccessors.getYOffset(), maxPosY);
        } else {
            posY = Math.max(12, maxPosY - ClientConfigAccessors.getYOffset());
        }
        return new Tuple<>(posX, posY);
    }
}
