package Tavi007.ElementalCombat.common.api.data;

import Tavi007.ElementalCombat.common.util.PacketBufferHelper;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Map;

public class DefenseLayer {
//    public static final Codec<DefenseLayer> CODEC = RecordCodecBuilder.create(
//            instance -> instance.group(
//                    DefenseMap.CODEC.fieldOf("styles").forGetter((DefenseLayer layer) -> layer.styles),
//                    DefenseMap.CODEC.fieldOf("elements").forGetter((DefenseLayer layer) -> layer.elements)
//            ).apply(instance, new BiFunction<DefenseMap, DefenseMap, DefenseLayer>() {
//                @Override
//                public DefenseLayer apply(DefenseMap styles, DefenseMap elements) {
//                    return new DefenseLayer(styles, elements);
//                }
//            })
//    );

    private DefenseMap styles = new DefenseMap();
    private DefenseMap elements = new DefenseMap();
    private Condition condition;

    public DefenseLayer() {
    }

    public DefenseLayer(DefenseMap styles, DefenseMap elements) {
        this.styles = styles;
        this.elements = elements;
    }

    public DefenseLayer(Map<String, Integer> style, Map<String, Integer> element) {
        addStyles(style);
        addElements(element);
    }

    public DefenseLayer(DefenseLayer layer) {
        addStyles(layer.getStyles());
        addElements(layer.getElements());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DefenseLayer other) {
            return this.elements.equals(other.elements)
                    && this.styles.equals(other.styles);
        }
        return false;
    }

    public Map<String, Integer> getStyles() {
        return styles.getMap();
    }

    public Map<String, Integer> getElements() {
        return elements.getMap();
    }

    // Adders
    public void addLayer(DefenseLayer layer) {
        addStyles(layer.getStyles());
        addElements(layer.getElements());
    }

    public void addStyles(Map<String, Integer> map) {
        styles.add(map);
    }

    public void addElements(Map<String, Integer> map) {
        elements.add(map);
    }

    // Subtractor
    public void subtractLayer(DefenseLayer layer) {
        subtractStyle(layer.getStyles());
        subtractElement(layer.getElements());
    }

    public void subtractStyle(Map<String, Integer> map) {
        styles.subtract(map);
    }

    public void subtractElement(Map<String, Integer> map) {
        elements.subtract(map);
    }

    // Merger
    public void mergeLayer(DefenseLayer layer) {
        mergeStyle(layer.getStyles());
        mergeElement(layer.getElements());
    }

    public void mergeStyle(Map<String, Integer> map) {
        styles.merge(map);
    }

    public void mergeElement(Map<String, Integer> map) {
        elements.merge(map);
    }

    // Other
    public boolean isEmpty() {
        return isStyleEmpty() && isElementEmpty();
    }

    public boolean isElementEmpty() {
        return elements.isEmpty();
    }

    public boolean isStyleEmpty() {
        return styles.isEmpty();
    }

    public String toString() {
        return "[element: " + elements + "; stlye:" + styles + "]";
    }

    public void writeToBuffer(FriendlyByteBuf buf) {
        PacketBufferHelper.writeMap(buf, styles.getMap());
        PacketBufferHelper.writeMap(buf, elements.getMap());
    }

    public void readFromBuffer(FriendlyByteBuf buf) {
        styles = new DefenseMap(PacketBufferHelper.readMap(buf));
        elements = new DefenseMap(PacketBufferHelper.readMap(buf));
    }
}
