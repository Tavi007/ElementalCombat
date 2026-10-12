package Tavi007.ElementalCombat.common.registry;

import Tavi007.ElementalCombat.common.Constants;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import java.util.HashMap;

public class ModCreativeTabs {
    public static final HashMap<String, Supplier<CreativeModeTab>> TAB_MAP = new HashMap<>();

    public static final Supplier<CreativeModeTab> TAB = register(
            Constants.MOD_ID,
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .title(Component.translatable("itemGroup.elementalcombat"))
                    .icon(ModItems.ESSENCE_FIRE.get()::getDefaultInstance)
                    .displayItems((displayParams, output) ->
                            ModItems.ITEMS.forEach((name, supplier) ->
                                    output.accept(supplier.get())))
                    .build());

    private static Supplier<CreativeModeTab> register(String name, Supplier<CreativeModeTab> supplier) {
        Supplier<CreativeModeTab> memoized = Suppliers.memoize(supplier);
        TAB_MAP.put(name, memoized);
        return memoized;
    }
}
