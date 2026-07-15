package Tavi007.ElementalCombat.client.data;

import Tavi007.ElementalCombat.client.gui.GuiHelper;
import Tavi007.ElementalCombat.common.api.data.Condition;
import Tavi007.ElementalCombat.common.data.DatapackDataAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Tavi007.ElementalCombat.client.gui.GuiHelper.TEXTURE_DELTA;


public record TooltipAttackData(String baseStyle, String baseElement,
                                Map<Condition, Tuple<String, String>> conditionals) {

    public int getHeight() {
        int noLines = conditionals.size() * 2 + 1;
        return noLines * TEXTURE_DELTA;
    }

    public int getWidth() {
        return 3 * TEXTURE_DELTA - 2;
    }

    public boolean isDefault() {
        boolean isDefaultStyle = DatapackDataAccessor.getDefaultStyle().equals(baseStyle);
        boolean isDefaultElement = DatapackDataAccessor.getDefaultElement().equals(baseElement);
        return conditionals.isEmpty() && isDefaultStyle && isDefaultElement;
    }

    public List<TextData> getTextData(int posX, int posY) {
        posY += TEXTURE_DELTA;
        ArrayList<TextData> textData = new ArrayList<>();
        for (Condition condition : conditionals.keySet()) {
            textData.add(new TextData(condition.getDescriptionId(), posX, posY));
            posY += TEXTURE_DELTA * 2;
        }
        return textData;
    }

    public List<TextureData> getTextureData(Font font, int posX, int posY) {
        List<TextureData> textureData = new ArrayList<>();

        // handle base
        textureData.add(new TextureData(
                "attack",
                posX,
                posY,
                null));
        textureData.addAll(GuiHelper.getAttackTextures(baseStyle, baseElement, posX + TEXTURE_DELTA, posY));

        // handle conditionals
        for (Map.Entry<Condition, Tuple<String, String>> entry : conditionals.entrySet()) {
            posY += TEXTURE_DELTA * 2; // this row contains the text of the conditional
            String style = entry.getValue().getA();
            String element = entry.getValue().getB();
            textureData.addAll(GuiHelper.getAttackTextures(style, element, posX + TEXTURE_DELTA, posY));
        }
        return textureData;
    }
}
