package Tavi007.ElementalCombat.common.registry;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.items.LensItem;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class ModItems {
    public static final Map<String, Supplier<Item>> ITEMS = new HashMap<>();
    
    private static final Item.Properties fullStack = new Item.Properties().stacksTo(64);

    public static final Supplier<Item> LENS = register(Constants.LENS, () -> new LensItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ESSENCE_FIRE = register(Constants.ESSENCE_FIRE, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_ICE = register(Constants.ESSENCE_ICE, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_WATER = register(Constants.ESSENCE_WATER, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_THUNDER = register(Constants.ESSENCE_THUNDER, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_DARKNESS = register(Constants.ESSENCE_DARKNESS, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_LIGHT = register(Constants.ESSENCE_LIGHT, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_EARTH = register(Constants.ESSENCE_EARTH, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_WIND = register(Constants.ESSENCE_WIND, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_FLORA = register(Constants.ESSENCE_FLORA, () -> new Item(fullStack));

    public static final Supplier<Item> ESSENCE_BLOCK_FIRE = register(Constants.ESSENCE_BLOCK_FIRE, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_BLOCK_ICE = register(Constants.ESSENCE_BLOCK_ICE, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_BLOCK_WATER = register(Constants.ESSENCE_BLOCK_WATER, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_BLOCK_THUNDER = register(Constants.ESSENCE_BLOCK_THUNDER, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_BLOCK_DARKNESS = register(Constants.ESSENCE_BLOCK_DARKNESS, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_BLOCK_LIGHT = register(Constants.ESSENCE_BLOCK_LIGHT, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_BLOCK_EARTH = register(Constants.ESSENCE_BLOCK_EARTH, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_BLOCK_WIND = register(Constants.ESSENCE_BLOCK_WIND, () -> new Item(fullStack));
    public static final Supplier<Item> ESSENCE_BLOCK_FLORA = register(Constants.ESSENCE_BLOCK_FLORA, () -> new Item(fullStack));

    private static Supplier<Item> register(String name, Supplier<Item> supplier) {
        Supplier<Item> memoize = Suppliers.memoize(supplier);
        ITEMS.put(name, memoize);
        return memoize;
    }
}
