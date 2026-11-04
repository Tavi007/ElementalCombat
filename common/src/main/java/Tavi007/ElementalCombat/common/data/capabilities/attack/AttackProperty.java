package Tavi007.ElementalCombat.common.data.capabilities.attack;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.data.datapack.CombatPropertiesManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;

public record AttackProperty(String style, String element) {

    public static void write(FriendlyByteBuf buf, AttackProperty property) {
        buf.writeUtf(property.style);
        buf.writeUtf(property.element);
    }

    public static AttackProperty read(FriendlyByteBuf buf) {
        return new AttackProperty(buf.readUtf(), buf.readUtf());
    }

    public static CompoundTag writeTag(AttackProperty property) {
        CompoundTag tag = new CompoundTag();
        tag.put(Constants.STYLE, StringTag.valueOf(property.style));
        tag.put(Constants.ELEMENT, StringTag.valueOf(property.element));
        return tag;
    }


    public static AttackProperty readTag(CompoundTag tag) {
        String style = tag.getString(Constants.STYLE);
        String element = tag.getString(Constants.ELEMENT);
        return new AttackProperty(style, element);
    }

    public boolean hasDefaultStyle() {
        return CombatPropertiesManager.getDefaultStyle().equals(style);
    }

    public boolean hasDefaultElement() {
        return CombatPropertiesManager.getDefaultElement().equals(element);
    }

    public boolean isValid() {
        return style != null && element != null;
    }
}
