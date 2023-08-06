package Tavi007.ElementalCombat.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.util.AttackDataHelper;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CombatDataHudOverlay implements IGuiOverlay {

    // copied from Screen
    private void renderHUDBox(PoseStack poseStack, int posX, int posY, int width, int height) {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        TooltipRenderUtil.renderTooltipBackground((p_262872_, p_262873_, p_262874_, p_262875_, p_262876_, p_262877_, p_262878_, p_262879_, p_262880_) -> {
            fillGradient(p_262872_, p_262873_, p_262874_, p_262875_, p_262876_, p_262877_, p_262878_, p_262879_, p_262880_);
        }, poseStack.last().pose(), bufferbuilder, posX, posY, width, height, 400);
        BufferUploader.drawWithShader(bufferbuilder.end());
    }

    // copied from GuiComponent
    protected void fillGradient(Matrix4f p_254526_, BufferBuilder p_93125_, int p_93126_, int p_93127_, int p_93128_, int p_93129_, int p_93130_,
            int p_93131_, int p_93132_) {
        float f = (p_93131_ >> 24 & 255) / 255.0F;
        float f1 = (p_93131_ >> 16 & 255) / 255.0F;
        float f2 = (p_93131_ >> 8 & 255) / 255.0F;
        float f3 = (p_93131_ & 255) / 255.0F;
        float f4 = (p_93132_ >> 24 & 255) / 255.0F;
        float f5 = (p_93132_ >> 16 & 255) / 255.0F;
        float f6 = (p_93132_ >> 8 & 255) / 255.0F;
        float f7 = (p_93132_ & 255) / 255.0F;
        p_93125_.vertex(p_254526_, p_93128_, p_93127_, p_93130_).color(f1, f2, f3, f).endVertex();
        p_93125_.vertex(p_254526_, p_93126_, p_93127_, p_93130_).color(f1, f2, f3, f).endVertex();
        p_93125_.vertex(p_254526_, p_93126_, p_93129_, p_93130_).color(f5, f6, f7, f4).endVertex();
        p_93125_.vertex(p_254526_, p_93128_, p_93129_, p_93130_).color(f5, f6, f7, f4).endVertex();
    }

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {

        if (ClientConfig.isHUDEnabled()) {
            // see Screen#renderToolTips in client.gui.screen
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                float scale = (float) ClientConfig.scale();
                AttackData attackData = AttackDataHelper.get(mc.player);
                DefenseData defenseData = DefenseDataHelper.get(mc.player);

                CombatDataLayerClientComponent clientComponent = new CombatDataLayerClientComponent(
                    new CombatDataLayerComponent(
                        attackData.toLayer(),
                        defenseData.toLayer(),
                        true,
                        false,
                        ClientConfig.isDoubleRowDefenseHUD()));

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
                poseStack.pushPose();
                poseStack.scale(scale, scale, scale);

                renderHUDBox(poseStack, posX, posY, listWidth, listHeight);

                // render component
                clientComponent.renderText(mc.font, poseStack, posX, posY);
                clientComponent.renderImage(mc.font, posX, posY, poseStack, null, 0);

                poseStack.popPose();
            }
        }
    }
}
