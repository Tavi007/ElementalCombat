package Tavi007.ElementalCombat.init;

import static Tavi007.ElementalCombat.init.CreativeTabList.addToTab;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemList {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ElementalCombat.MOD_ID);

    private static Properties fullStack = new Item.Properties().stacksTo(64);

    // essence
    public static final RegistryObject<Item> ESSENCE_FIRE = addToTab(ITEMS.register("essence_fire", () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_ICE = addToTab(ITEMS.register("essence_ice", () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_WATER = addToTab(ITEMS.register("essence_water", () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_THUNDER = addToTab(ITEMS.register("essence_thunder", () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_DARKNESS = addToTab(ITEMS.register("essence_darkness", () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_LIGHT = addToTab(ITEMS.register("essence_light", () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_EARTH = addToTab(ITEMS.register("essence_earth", () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_WIND = addToTab(ITEMS.register("essence_wind", () -> new Item(fullStack)));
    public static final RegistryObject<Item> ESSENCE_FLORA = addToTab(ITEMS.register("essence_flora", () -> new Item(fullStack)));

}
