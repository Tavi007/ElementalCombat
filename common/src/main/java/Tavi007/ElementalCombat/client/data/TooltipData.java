package Tavi007.ElementalCombat.client.data;

import net.minecraft.client.gui.Font;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.ArrayList;
import java.util.List;

public record TooltipData(TooltipAttackData attackData, TooltipDefenseData defenseData) implements TooltipComponent {

    public int getWidth(Font font) {
        int attackWidth = attackData.isDefault() ? 0 : attackData.getWidth();
        int defenseWidth = defenseData.isDefault() ? 0 : defenseData.getWidth(font);
        return attackWidth + defenseWidth;
    }

    public int getHeight() {
        int attackHeight = attackData.isDefault() ? 0 : attackData.getHeight();
        int defenseHeight = defenseData.isDefault() ? 0 : defenseData.getHeight();
        return attackHeight + defenseHeight;
    }

    public List<TextData> getTextData(int posX, int posY) {
        ArrayList<TextData> textData = new ArrayList<>();

        // attack
        if (!attackData.isDefault()) {
            textData.addAll(attackData.getTextData(posX, posY));
            posY += attackData.getHeight();
        }

        // defense
        if (!defenseData.isDefault()) {
            textData.addAll(defenseData.getTextData(posX, posY));
        }

        return textData;
    }

    public List<TextureData> getTextureData(Font font, int posX, int posY) {
        List<TextureData> textureData = new ArrayList<>();

        // attack
        if (!attackData.isDefault()) {
            textureData.addAll(attackData.getTextureData(font, posX, posY));
            posY += attackData.getHeight();
        }

        // defense
        if (!defenseData.isDefault()) {
            textureData.addAll(defenseData.getTextureData(font, posX, posY));
        }
        
        return textureData;
    }
}
