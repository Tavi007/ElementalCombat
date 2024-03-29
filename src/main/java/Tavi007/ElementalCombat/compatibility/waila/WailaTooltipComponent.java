package Tavi007.ElementalCombat.compatibility.waila;

import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import Tavi007.ElementalCombat.client.CombatDataLayerClientComponent;
import Tavi007.ElementalCombat.client.CombatDataLayerComponent;
import Tavi007.ElementalCombat.config.ClientConfig;
import mcp.mobius.waila.api.ITooltipComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class WailaTooltipComponent implements ITooltipComponent {

    private CombatDataLayerClientComponent clientComponent;

    public WailaTooltipComponent(AttackLayer attackLayer, DefenseLayer defenseLayer) {
        clientComponent = new CombatDataLayerClientComponent(
            new CombatDataLayerComponent(
                attackLayer,
                defenseLayer,
                true,
                false,
                ClientConfig.isDoubleRowDefenseHWYLA()));
    }

    @Override
    public int getWidth() {
        return clientComponent.getWidth(Minecraft.getInstance().font);
    }

    @Override
    public int getHeight() {
        return clientComponent.getHeight();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int posX, int posY, float delta) {
        Font font = Minecraft.getInstance().font;
        clientComponent.renderImage(font, posX, posY, guiGraphics);
        clientComponent.renderText(font, guiGraphics, posX, posY);
    }

}
