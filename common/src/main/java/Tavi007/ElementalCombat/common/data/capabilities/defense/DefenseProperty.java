package Tavi007.ElementalCombat.common.data.capabilities.defense;

import Tavi007.ElementalCombat.common.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

public record DefenseProperty(DefenseMap style, DefenseMap element) {

    public DefenseProperty() {
        this(new DefenseMap(), new DefenseMap());
    }

    public static DefenseProperty read(FriendlyByteBuf buf) {
        DefenseMap style = DefenseMap.read(buf);
        DefenseMap element = DefenseMap.read(buf);
        return new DefenseProperty(style, element);
    }

    public static void write(DefenseProperty property, FriendlyByteBuf buf) {
        DefenseMap.write(property.style, buf);
        DefenseMap.write(property.element, buf);
    }

    public static CompoundTag writeTag(DefenseProperty property) {
        CompoundTag tag = new CompoundTag();
        tag.put(Constants.STYLE, DefenseMap.writeTag(property.style));
        tag.put(Constants.ELEMENT, DefenseMap.writeTag(property.element));
        return tag;
    }

    public static DefenseProperty readTag(CompoundTag tag) {
        DefenseMap style = DefenseMap.readTag(tag.getCompound(Constants.STYLE));
        DefenseMap element = DefenseMap.readTag(tag.getCompound(Constants.ELEMENT));
        return new DefenseProperty(style, element);
    }

    public boolean hasStyle() {
        return style.isEmpty();
    }

    public boolean hasElement() {
        return element.isEmpty();
    }

    public boolean isValid() {
        return style.isValid() && element.isValid();
    }
}
