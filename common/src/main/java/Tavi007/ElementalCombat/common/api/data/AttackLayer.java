package Tavi007.ElementalCombat.common.api.data;

import Tavi007.ElementalCombat.common.data.DatapackDataAccessor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class AttackLayer {
    //    public static final Codec<AttackLayer> CODEC = RecordCodecBuilder.create(
//            instance -> instance.group(
//                    Codec.STRING.fieldOf("style").forGetter(AttackLayer::getStyle),
//                    Codec.STRING.fieldOf("element").forGetter(AttackLayer::getElement),
//                    Codec.of(Condtion.).fieldOf("condition").forGetter(AttackLayer::getCondition)
//            ).apply(instance, new Function3<String, String, String, AttackLayer>() {
//                @Override
//                public AttackLayer apply(String style, String element, Condition condition) {
//                    return new AttackLayer(style, element, condition);
//                }
//            })
//    );
    private String style;
    private String element;
    private Condition condition;

    public AttackLayer() {
    }

    public AttackLayer(String style, String element, Condition condition) {
        set(style, element, condition);
    }


    public AttackLayer(AttackLayer data) {
        set(data);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof AttackLayer other) {
            return this.getElement().equals(other.getElement())
                    && this.getStyle().equals(other.getStyle());
        }
        return false;
    }

    @Override
    public String toString() {
        return "[element:" + element + "; style:" + style + "]";
    }

    public void set(AttackLayer data) {
        if (data != null) {
            set(data.getStyle(), data.getElement(), data.getCondition());
        }
    }

    public void set(String style, String element, Condition condition) {
        setElement(element);
        setStyle(style);
        setCondition(condition);
    }

    public Condition getCondition() {
        if (condition == null) {
            condition = Condition.BASE;
        }
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public boolean isActive(@Nullable Entity entity, @Nullable ItemStack stack, @Nullable Level level) {
        return getCondition().isActive(entity, stack, level);
    }

    public String getElement() {
        if (element == null || element.trim().isEmpty()) {
            element = DatapackDataAccessor.getDefaultElement();
        }
        return element;
    }

    public void setElement(String element) {
        if (element == null || element.trim().isEmpty()) {
            this.element = DatapackDataAccessor.getDefaultElement();
        } else {
            this.element = element;
        }
    }

    public String getStyle() {
        if (style == null || style.trim().isEmpty()) {
            style = DatapackDataAccessor.getDefaultStyle();
        }
        return style;
    }

    public void setStyle(String style) {
        if (style == null || style.trim().isEmpty()) {
            this.style = DatapackDataAccessor.getDefaultStyle();
        } else {
            this.style = style;
        }
    }

    public void writeToBuffer(FriendlyByteBuf buf) {
        buf.writeUtf(getStyle());
        buf.writeUtf(getElement());
    }

    public void readFromBuffer(FriendlyByteBuf buf) {
        style = buf.readUtf();
        element = buf.readUtf();
    }

    public boolean isDefault() {
        String element = getElement();
        String style = getStyle();
        Condition condition = getCondition();
        return element.isEmpty() || element.equals(DatapackDataAccessor.getDefaultElement())
                && (style.isEmpty() || style.equals(DatapackDataAccessor.getDefaultStyle()))
                && condition == null;
    }
}
