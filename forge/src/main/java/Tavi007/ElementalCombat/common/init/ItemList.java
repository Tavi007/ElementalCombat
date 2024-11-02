package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.items.LensItem;
import Tavi007.ElementalCombat.common.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemList {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    static {
        ModItems.LENS = register(Constants.LENS, () -> new LensItem(new Item.Properties().stacksTo(1)));
        ModItems.ESSENCE_FIRE = register(Constants.ESSENCE_FIRE, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_ICE = register(Constants.ESSENCE_ICE, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_WATER = register(Constants.ESSENCE_WATER, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_THUNDER = register(Constants.ESSENCE_THUNDER, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_DARKNESS = register(Constants.ESSENCE_DARKNESS, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_LIGHT = register(Constants.ESSENCE_LIGHT, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_EARTH = register(Constants.ESSENCE_EARTH, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_WIND = register(Constants.ESSENCE_WIND, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_FLORA = register(Constants.ESSENCE_FLORA, () -> new Item(new Item.Properties()));

        ModItems.ESSENCE_BLOCK_FIRE = register(Constants.ESSENCE_BLOCK_FIRE, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_BLOCK_ICE = register(Constants.ESSENCE_BLOCK_ICE, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_BLOCK_WATER = register(Constants.ESSENCE_BLOCK_WATER, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_BLOCK_THUNDER = register(Constants.ESSENCE_BLOCK_THUNDER, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_BLOCK_DARKNESS = register(Constants.ESSENCE_BLOCK_DARKNESS, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_BLOCK_LIGHT = register(Constants.ESSENCE_BLOCK_LIGHT, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_BLOCK_EARTH = register(Constants.ESSENCE_BLOCK_EARTH, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_BLOCK_WIND = register(Constants.ESSENCE_BLOCK_WIND, () -> new Item(new Item.Properties()));
        ModItems.ESSENCE_BLOCK_FLORA = register(Constants.ESSENCE_BLOCK_FLORA, () -> new Item(new Item.Properties()));
    }

    private static RegistryObject<Item> register(String name, Supplier<Item> supplier) {
        return CreativeTabList.addToTab(ITEMS.register(name, supplier));
    }
}
