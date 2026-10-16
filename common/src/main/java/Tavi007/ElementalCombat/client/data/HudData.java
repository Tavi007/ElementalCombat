package Tavi007.ElementalCombat.client.data;

import Tavi007.ElementalCombat.client.gui.GuiHelper;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Tavi007.ElementalCombat.client.gui.GuiHelper.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public record HudData(String attackStyle,
                      String attackElement,
                      Map<String, Integer> defenseStyle,
                      Map<String, Integer> defenseElement) {


    public int getHeight() {
        int noLinesStyleDefense = getNumberOfRows(defenseStyle);
        int noLinesElementDefense = getNumberOfRows(defenseElement);
        return (1 + noLinesStyleDefense + noLinesElementDefense) * TEXTURE_DELTA;
    }

    public int getWidth() {
        int noIcons = max(defenseStyle.size(), defenseElement.size());
        noIcons = 1 + min(noIcons, MAX_ICONS_PRE_ROW);
        return noIcons * TEXTURE_DELTA - 2;
    }

    public List<TextureData> getTextureData(int posX, int posY) {
        List<TextureData> textureData = new ArrayList<>();

        // attack
        textureData.add(new TextureData(
                "attack",
                posX,
                posY,
                null));
        textureData.addAll(GuiHelper.getAttackTextures(attackStyle, attackElement, posX + TEXTURE_DELTA, posY));

        posY = posY + TEXTURE_DELTA;
        // defense
        textureData.add(new TextureData(
                "defense",
                posX,
                posY,
                null));

        Tuple<List<TextureData>, Integer> dataTuple = getDefenseTextures(defenseStyle, defenseElement, posX + TEXTURE_DELTA, posY);
        textureData.addAll(dataTuple.getA());

        return textureData;
    }
}
