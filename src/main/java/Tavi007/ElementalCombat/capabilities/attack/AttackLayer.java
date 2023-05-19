package Tavi007.ElementalCombat.capabilities.attack;

import Tavi007.ElementalCombat.api.BasePropertiesAPI;

public class AttackLayer {

    private String style = BasePropertiesAPI.getDefaultAttackStyle();
    private String element = BasePropertiesAPI.getDefaultAttackElement();

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
        if (object instanceof AttackLayer) {
            AttackLayer other = (AttackLayer) object;
            return this.element.equals(other.element)
                && this.style.equals(other.style);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + element + " " + style + "]";
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

    public void setElement(String element) {
        if (element == null || element.trim().isEmpty()) {
            this.element = BasePropertiesAPI.getDefaultAttackElement();
        } else {
            this.element = element;
        }
    }

    public void setStyle(String style) {
        if (style == null || style.trim().isEmpty()) {
            this.style = BasePropertiesAPI.getDefaultAttackStyle();
        } else {
            this.style = style;
        }
    }

    public String getElement() {
        return this.element;
    }

    public String getStyle() {
        return this.style;
    }

    public boolean isDefault() {
        return (element.isEmpty() || element.equals(BasePropertiesAPI.getDefaultAttackElement()))
            && (style.isEmpty() || style.equals(BasePropertiesAPI.getDefaultAttackStyle()));
    }
}
