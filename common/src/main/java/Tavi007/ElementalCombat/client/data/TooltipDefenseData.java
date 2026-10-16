package Tavi007.ElementalCombat.client.data;

import Tavi007.ElementalCombat.common.api.data.Condition;
import net.minecraft.client.gui.Font;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Tavi007.ElementalCombat.client.gui.GuiHelper.*;
import static java.lang.Math.max;
import static java.lang.Math.min;


public record TooltipDefenseData(Map<String, Integer> baseStyle,
                                 Map<String, Integer> baseElement,
                                 Map<Condition, Tuple<Map<String, Integer>, Map<String, Integer>>> conditionals) {


    public boolean isDefault() {
        return baseStyle.isEmpty() && baseElement.isEmpty() && conditionals.isEmpty();
    }

    public int getWidth(Font font) {
        int baseWidth = max(getWidth(baseStyle), getWidth(baseElement));
        int maxConditionWidth = conditionals.entrySet().stream()
                .mapToInt(e -> getWidth(e.getKey(), e.getValue(), font))
                .max()
                .orElse(0);
        return max(baseWidth, maxConditionWidth);
    }

    private int getWidth(Condition condition, Tuple<Map<String, Integer>, Map<String, Integer>> values, Font font) {
        return max(getWidth(condition, font), max(getWidth(values.getA()), getWidth(values.getB())));
    }

    private int getWidth(Map<String, Integer> map) {
        int noIcons = 1 + min(map.size(), MAX_ICONS_PRE_ROW);
        return noIcons * TEXTURE_DELTA - 2;
    }

    private int getWidth(Condition condition, Font font) {
        return 0;
    }

    public int getHeight() {
        int conditionHeight = conditionals.entrySet().stream()
                .mapToInt(e -> getHeight(e.getKey(), e.getValue()))
                .sum();
        return getHeight(baseStyle) + getHeight(baseElement) + conditionHeight;
    }

    private int getHeight(Condition condition, Tuple<Map<String, Integer>, Map<String, Integer>> values) {
        return getHeight(condition) + getHeight(values.getA()) + getHeight(values.getB());
    }

    private int getHeight(Condition condition) {
        return TEXTURE_DELTA;
    }

    private int getHeight(Map<String, Integer> map) {
        return getNumberOfRows(map) * TEXTURE_DELTA;
    }

    public List<TextData> getTextData(int posX, int posY) {
        posY += TEXTURE_DELTA * (getNumberOfRows(baseStyle) + getNumberOfRows(baseElement));
        ArrayList<TextData> textData = new ArrayList<>();
        for (Map.Entry<Condition, Tuple<Map<String, Integer>, Map<String, Integer>>> entry : conditionals.entrySet()) {
            textData.add(new TextData(entry.getKey().getDescriptionId(), posX, posY));
            Map<String, Integer> style = entry.getValue().getA();
            Map<String, Integer> element = entry.getValue().getB();
            posY += TEXTURE_DELTA * (getNumberOfRows(style) + getNumberOfRows(element) + 1);
        }
        return textData;
    }

    public List<TextureData> getTextureData(Font font, int posX, int posY) {
        List<TextureData> textureData = new ArrayList<>();

        // handle base
        textureData.add(new TextureData(
                "defense",
                posX,
                posY,
                null));
        Tuple<List<TextureData>, Integer> dataTuple = getDefenseTextures(baseStyle, baseElement, posX + TEXTURE_DELTA, posY);
        textureData.addAll(dataTuple.getA());
        posY = posY + (dataTuple.getB() + 1) * TEXTURE_DELTA;

        // handle conditionals
        for (Map.Entry<Condition, Tuple<Map<String, Integer>, Map<String, Integer>>> entry : conditionals.entrySet()) {
            Map<String, Integer> style = entry.getValue().getA();
            Map<String, Integer> element = entry.getValue().getB();

            dataTuple = getDefenseTextures(style, element, posX + TEXTURE_DELTA, posY);
            textureData.addAll(dataTuple.getA());
            posY = posY + (dataTuple.getB() + 1) * TEXTURE_DELTA;
        }
        return textureData;
    }
}
