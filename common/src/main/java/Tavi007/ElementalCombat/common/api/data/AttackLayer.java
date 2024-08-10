package Tavi007.ElementalCombat.common.api.data;

import Tavi007.ElementalCombat.common.data.DatapackDataAccessor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.BiFunction;

public class AttackLayer {
    public static final Codec<AttackLayer> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.fieldOf("style").forGetter((AttackLayer layer) -> layer.style),
                    Codec.STRING.fieldOf("element").forGetter((AttackLayer layer) -> layer.element)
            ).apply(instance, new BiFunction<String, String, AttackLayer>() {
                @Override
                public AttackLayer apply(String style, String element) {
                    return new AttackLayer(style, element);
                }
            })
    );
    private String style;
    private String element;

    public AttackLayer() {
    }

    public AttackLayer(String style, String element) {
        set(style, element);
    }


    public AttackLayer(AttackLayer data) {
        this(data.getStyle(), data.getElement());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof AttackLayer other) {
            return this.element.equals(other.element)
                    && this.style.equals(other.style);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[element:" + element + "; style:" + style + "]";
    }

    public void set(AttackLayer data) {
        if (data != null) {
            setElement(data.getElement());
            setStyle(data.getStyle());
        }
    }

    public void set(String style, String element) {
        setElement(element);
        setStyle(style);
    }

    public String getElement() {
        return this.element;
    }

    public void setElement(String element) {
        if (element == null || element.trim().isEmpty()) {
            this.element = DatapackDataAccessor.getDefaultElement();
        } else {
            this.element = element;
        }
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        if (style == null || style.trim().isEmpty()) {
            this.style = DatapackDataAccessor.getDefaultStyle();
        } else {
            this.style = style;
        }
    }

    public void writeToBuffer(FriendlyByteBuf buf) {
        buf.writeUtf(style);
        buf.writeUtf(element);
    }

    public void readFromBuffer(FriendlyByteBuf buf) {
        style = buf.readUtf();
        element = buf.readUtf();
    }

    public boolean isDefault() {
        return element.isEmpty() || element.equals(DatapackDataAccessor.getDefaultElement())
                && (style.isEmpty() || style.equals(DatapackDataAccessor.getDefaultStyle()));
    }
}
