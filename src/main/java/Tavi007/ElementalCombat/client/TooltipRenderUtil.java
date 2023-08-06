package Tavi007.ElementalCombat.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.math.Matrix4f;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TooltipRenderUtil {

    public static final int MOUSE_OFFSET = 12;
    private static final int PADDING = 3;
    public static final int PADDING_LEFT = 3;
    public static final int PADDING_RIGHT = 3;
    public static final int PADDING_TOP = 3;
    public static final int PADDING_BOTTOM = 3;
    private static final int BACKGROUND_COLOR = -267386864;
    private static final int BORDER_COLOR_TOP = 1347420415;
    private static final int BORDER_COLOR_BOTTOM = 1344798847;

    public static void renderTooltipBackground(TooltipRenderUtil.BlitPainter p_262929_, Matrix4f p_263052_, BufferBuilder p_263082_, int p_263046_,
            int p_263101_, int p_263024_, int p_262926_, int p_263038_) {
        int i = p_263046_ - 3;
        int j = p_263101_ - 3;
        int k = p_263024_ + 3 + 3;
        int l = p_262926_ + 3 + 3;
        renderHorizontalLine(p_262929_, p_263052_, p_263082_, i, j - 1, k, p_263038_, -267386864);
        renderHorizontalLine(p_262929_, p_263052_, p_263082_, i, j + l, k, p_263038_, -267386864);
        renderRectangle(p_262929_, p_263052_, p_263082_, i, j, k, l, p_263038_, -267386864);
        renderVerticalLine(p_262929_, p_263052_, p_263082_, i - 1, j, l, p_263038_, -267386864);
        renderVerticalLine(p_262929_, p_263052_, p_263082_, i + k, j, l, p_263038_, -267386864);
        renderFrameGradient(p_262929_, p_263052_, p_263082_, i, j + 1, k, l, p_263038_, 1347420415, 1344798847);
    }

    private static void renderFrameGradient(TooltipRenderUtil.BlitPainter p_262974_, Matrix4f p_263030_, BufferBuilder p_263061_, int p_262982_, int p_262912_,
            int p_262946_, int p_263049_, int p_263118_, int p_263104_, int p_262979_) {
        renderVerticalLineGradient(p_262974_, p_263030_, p_263061_, p_262982_, p_262912_, p_263049_ - 2, p_263118_, p_263104_, p_262979_);
        renderVerticalLineGradient(p_262974_, p_263030_, p_263061_, p_262982_ + p_262946_ - 1, p_262912_, p_263049_ - 2, p_263118_, p_263104_, p_262979_);
        renderHorizontalLine(p_262974_, p_263030_, p_263061_, p_262982_, p_262912_ - 1, p_262946_, p_263118_, p_263104_);
        renderHorizontalLine(p_262974_, p_263030_, p_263061_, p_262982_, p_262912_ - 1 + p_263049_ - 1, p_262946_, p_263118_, p_262979_);
    }

    private static void renderVerticalLine(TooltipRenderUtil.BlitPainter p_262921_, Matrix4f p_263044_, BufferBuilder p_262936_, int p_262967_, int p_262922_,
            int p_262938_, int p_263008_, int p_263088_) {
        p_262921_.blit(p_263044_, p_262936_, p_262967_, p_262922_, p_262967_ + 1, p_262922_ + p_262938_, p_263008_, p_263088_, p_263088_);
    }

    private static void renderVerticalLineGradient(TooltipRenderUtil.BlitPainter p_263090_, Matrix4f p_263096_, BufferBuilder p_263015_, int p_262919_,
            int p_262995_, int p_263067_, int p_263023_, int p_262942_, int p_263114_) {
        p_263090_.blit(p_263096_, p_263015_, p_262919_, p_262995_, p_262919_ + 1, p_262995_ + p_263067_, p_263023_, p_262942_, p_263114_);
    }

    private static void renderHorizontalLine(TooltipRenderUtil.BlitPainter p_262966_, Matrix4f p_263094_, BufferBuilder p_262943_, int p_262949_, int p_263068_,
            int p_263111_, int p_263095_, int p_262918_) {
        p_262966_.blit(p_263094_, p_262943_, p_262949_, p_263068_, p_262949_ + p_263111_, p_263068_ + 1, p_263095_, p_262918_, p_262918_);
    }

    private static void renderRectangle(TooltipRenderUtil.BlitPainter p_262933_, Matrix4f p_263035_, BufferBuilder p_263084_, int p_262945_, int p_262955_,
            int p_263017_, int p_262978_, int p_262950_, int p_262931_) {
        p_262933_.blit(p_263035_, p_263084_, p_262945_, p_262955_, p_262945_ + p_263017_, p_262955_ + p_262978_, p_262950_, p_262931_, p_262931_);
    }

    @FunctionalInterface
    @OnlyIn(Dist.CLIENT)
    public interface BlitPainter {

        void blit(Matrix4f p_262958_, BufferBuilder p_263117_, int p_262987_, int p_263036_, int p_263001_, int p_263071_, int p_263011_, int p_262954_,
                int p_262961_);
    }
}
