package Tavi007.ElementalCombat.client;

import Tavi007.ElementalCombat.client.gui.CombatDataHudOverlay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ForgeCombatDataHudOverlay implements IGuiOverlay {


    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        CombatDataHudOverlay.render(guiGraphics, partialTick, screenWidth, screenHeight);
    }
}
