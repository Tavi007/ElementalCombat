package Tavi007.ElementalCombat.client.gui;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.Condition;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import net.minecraft.client.gui.Font;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CombatDataComponent implements TooltipComponent {

    public static final int iconSize = 8;
    private final LinkedHashMap<Condition, AttackLayer> groupedAttackLayers;
    private final LinkedHashMap<Condition, DefenseLayer> groupedDefenseLayers;
    private final int numberOfRows;
    private final int imagePosX;

    public CombatDataComponent(AttackData attackData, DefenseData defenseData) {
        groupedAttackLayers = attackData.getLayerSortedByCondition();
        Constants.LOG.info(groupedAttackLayers.toString());
        groupedDefenseLayers = new LinkedHashMap<>();  // TODO: adjust later
        numberOfRows = groupedAttackLayers.size() + groupedDefenseLayers.size();
        imagePosX = 10; // TODO: adjust later?
    }

    public int getWidth(Font font) {
        return 20;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getLineHeight(Font font) {
        return font.lineHeight * getNumberOfRows();
    }


    public List<TextureData> getTextureData(Font font, int posX, int posY) {
        int maxLineHeight = getLineHeight(font);
        List<TextureData> textureData = new ArrayList<>();

        // attack
        if (shouldAttackDataBeDisplayed()) {
            textureData.add(new TextureData(
                    "attack",
                    posX,
                    posY));
            for (Map.Entry<Condition, AttackLayer> entry : groupedAttackLayers.entrySet()) {
                posY += maxLineHeight; // place for description
                AttackLayer layer = entry.getValue();
                textureData.add(new TextureData(
                        layer.getElement(),
                        posX + imagePosX,
                        posY));
                textureData.add(new TextureData(
                        layer.getStyle(),
                        posX + imagePosX + iconSize + 2,
                        posY));
                posY += maxLineHeight;

            }
        }


        // defense
        if (shouldDefenseDataBeDisplayed()) {
            textureData.add(new TextureData(
                    "defense",
                    posX,
                    posY));
//            textureData.add(new TextureData(
//                    getCurrentName(defenseData.getStyles()),
//                    posX + widthDefense,
//                    posY));
//            posY += maxLineHeight;
//            textureData.add(new TextureData(
//                    getCurrentName(defenseData.getElements()),
//                    posX + widthDefense,
//                    posY));
        }
        return textureData;
    }

    private boolean shouldAttackDataBeDisplayed() {
        //  TODO: only true, if noteworthy data exist
        return true;
    }

    private boolean shouldDefenseDataBeDisplayed() {
        //  TODO: only true, if noteworthy data exist
        return true;
    }
}
