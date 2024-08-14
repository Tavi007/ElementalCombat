package Tavi007.ElementalCombat.common.registry;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.items.LensItem;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ModItems {

    private static final Item.Properties fullStack = new Item.Properties().stacksTo(64);
    private static final Map<String, Item> registry = new HashMap<>();
    public static final Item LENS = register(Constants.LENS, new LensItem(new Item.Properties().stacksTo(1)));
    public static final Item ESSENCE_FIRE = register(Constants.ESSENCE_FIRE, new Item(fullStack));
    public static final Item ESSENCE_ICE = register(Constants.ESSENCE_ICE, new Item(fullStack));
    public static final Item ESSENCE_WATER = register(Constants.ESSENCE_WATER, new Item(fullStack));
    public static final Item ESSENCE_THUNDER = register(Constants.ESSENCE_THUNDER, new Item(fullStack));
    public static final Item ESSENCE_DARKNESS = register(Constants.ESSENCE_DARKNESS, new Item(fullStack));
    public static final Item ESSENCE_LIGHT = register(Constants.ESSENCE_LIGHT, new Item(fullStack));
    public static final Item ESSENCE_EARTH = register(Constants.ESSENCE_EARTH, new Item(fullStack));
    public static final Item ESSENCE_WIND = register(Constants.ESSENCE_WIND, new Item(fullStack));
    public static final Item ESSENCE_FLORA = register(Constants.ESSENCE_FLORA, new Item(fullStack));

    public static final Item ESSENCE_BLOCK_FIRE = register(Constants.ESSENCE_BLOCK_FIRE, new Item(fullStack));
    public static final Item ESSENCE_BLOCK_ICE = register(Constants.ESSENCE_BLOCK_ICE, new Item(fullStack));
    public static final Item ESSENCE_BLOCK_WATER = register(Constants.ESSENCE_BLOCK_WATER, new Item(fullStack));
    public static final Item ESSENCE_BLOCK_THUNDER = register(Constants.ESSENCE_BLOCK_THUNDER, new Item(fullStack));
    public static final Item ESSENCE_BLOCK_DARKNESS = register(Constants.ESSENCE_BLOCK_DARKNESS, new Item(fullStack));
    public static final Item ESSENCE_BLOCK_LIGHT = register(Constants.ESSENCE_BLOCK_LIGHT, new Item(fullStack));
    public static final Item ESSENCE_BLOCK_EARTH = register(Constants.ESSENCE_BLOCK_EARTH, new Item(fullStack));
    public static final Item ESSENCE_BLOCK_WIND = register(Constants.ESSENCE_BLOCK_WIND, new Item(fullStack));
    public static final Item ESSENCE_BLOCK_FLORA = register(Constants.ESSENCE_BLOCK_FLORA, new Item(fullStack));

    private static Item register(String name, Item item) {
        registry.put(name, item);
        return item;
    }

    public static void register(BiConsumer<String, Item> registerConsumer) {
        registry.forEach((name, item) -> registerConsumer.accept(name, item));
    }
}
