package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.items.LensItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemList {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    private static final Properties fullStack = new Item.Properties().stacksTo(64);

    // essence
    public static final RegistryObject<Item> ESSENCE_FIRE = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_FIRE, () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_ICE = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_ICE, () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_WATER = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_WATER, () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_THUNDER = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_THUNDER, () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_DARKNESS = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_DARKNESS, () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_LIGHT = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_LIGHT, () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_EARTH = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_EARTH, () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_WIND = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_WIND, () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_FLORA = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_FLORA, () -> new Item(fullStack)));

    public static final RegistryObject<Item> LENS = CreativeTabList.addToTab(ITEMS.register(Constants.LENS, () -> new LensItem(fullStack)));

}
